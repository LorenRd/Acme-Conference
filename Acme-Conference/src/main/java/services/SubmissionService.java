package services;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PaperRepository;
import repositories.SubmissionRepository;

import domain.Author;
import domain.Paper;
import domain.Submission;
import forms.SubmissionForm;

@Service
@Transactional
public class SubmissionService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SubmissionRepository submissionRepository;

	@Autowired
	private PaperRepository paperRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AuthorService authorService;

	@Autowired
	private PaperService paperService;

	@Autowired
	private Validator validator;

	// Simple CRUD Methods

	public Submission findOne(final int submissionId) {
		Submission result;

		result = this.submissionRepository.findOne(submissionId);
		Assert.notNull(result);
		return result;
	}

	public Submission create() {
		Submission result;
		final Author principal;

		principal = this.authorService.findByPrincipal();
		Assert.notNull(principal);

		Paper paper = new Paper();
		paper.setTitle("");
		paper.setAuthor("");
		paper.setDocument("");
		paper.setSummary("");

		result = new Submission();
		result.setAuthor(principal);
		result.setTicker(this.generateTicker(principal));
		result.setStatus("UNDER-REVIEW");
		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setPaper(paper);

		return result;
	}

	public Submission save(final Submission submission) {
		Author principal;
		Submission result;
		Paper paper;

		principal = this.authorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(submission);
		Assert.isTrue(submission.getAuthor() == principal);

		paper = this.paperRepository.save(submission.getPaper());

		submission.setPaper(paper);

		result = this.submissionRepository.save(submission);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Submission submission) {
		final Paper paper;

		Assert.notNull(submission);
		Assert.isTrue(submission.getId() != 0);

		paper = submission.getPaper();

		if (paper != null)
			this.paperService.delete(paper);

		this.submissionRepository.delete(submission);
	}

	// Business Methods

	public Collection<Submission> findAllByAuthor(final int authorId) {
		Collection<Submission> result;

		result = this.submissionRepository.findAllByAuthorId(authorId);
		return result;
	}

	private String generateTicker(final Author author) {
		String result;
		String name;
		String middleName;
		String surname;
		String text;
		String randomAlphanumeric;

		name = author.getName().toUpperCase();
		name = Character.toString(name.charAt(0));

		if (author.getMiddleName().equals(null)
				|| author.getMiddleName().equals("")) {
			middleName = "X";
		} else {
			middleName = author.getMiddleName().toUpperCase();
			middleName = Character.toString(middleName.charAt(0));
		}

		surname = author.getSurname().toUpperCase();
		surname = Character.toString(surname.charAt(0));

		text = name + middleName + surname;

		randomAlphanumeric = this.getAlphaNumeric();

		result = text + "-" + randomAlphanumeric;
		if (this.repeatedTicker(author, result))
			this.generateTicker(author);

		return result;
	}

	public String getAlphaNumeric() {
		char[] ch = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		char[] c = new char[4];
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			c[i] = ch[random.nextInt(ch.length)];
		}

		return new String(c);
	}

	public boolean repeatedTicker(final Author author, final String ticker) {
		Boolean isRepeated = false;
		int repeats;

		repeats = this.submissionRepository.findRepeatedTickers(author.getId(),
				ticker);

		if (repeats > 0)
			isRepeated = true;

		return isRepeated;
	}

	public Submission reconstruct(final SubmissionForm submissionForm,
			final BindingResult binding) {
		Submission result;
		if (submissionForm.getId() == 0) {
			result = this.create();
			result.setTicker(this.generateTicker(this.authorService
					.findByPrincipal()));
			result.setMoment(new Date(System.currentTimeMillis() - 1));
			result.setStatus("UNDER-REVIEW");
			result.setAuthor(this.authorService.findByPrincipal());
			result.setConference(submissionForm.getConference());
			result.getPaper().setTitle(submissionForm.getTitle());
			result.getPaper().setAuthor(submissionForm.getAuthorPaper());
			result.getPaper().setSummary(submissionForm.getSummary());
			result.getPaper().setDocument(submissionForm.getDocument());

		} else {
			result = this.submissionRepository.findOne(submissionForm.getId());
			result.getPaper().setTitle(submissionForm.getTitle());
			result.getPaper().setAuthor(submissionForm.getAuthorPaper());
			result.getPaper().setSummary(submissionForm.getSummary());
			result.getPaper().setDocument(submissionForm.getDocument());
		}

		this.validator.validate(result, binding);
		if (binding.hasErrors()) {
			throw new ValidationException();
		}

		return result;
	}

	public SubmissionForm construct(final Submission submission) {
		final SubmissionForm submissionForm = new SubmissionForm();
		submissionForm.setId(submission.getId());
		submissionForm.setConference(submission.getConference());
		submissionForm.setTitle(submission.getPaper().getTitle());
		submissionForm.setAuthorPaper(submission.getPaper().getAuthor());
		submissionForm.setSummary(submission.getPaper().getSummary());
		submissionForm.setDocument(submission.getPaper().getDocument());
		return submissionForm;
	}

}
