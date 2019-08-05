package services;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SubmissionRepository;

import domain.Author;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SubmissionRepository submissionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AuthorService authorService;

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

		result = new Submission();
		result.setAuthor(principal);
		result.setTicker(this.generateTicker(principal));
		result.setStatus("UNDER-REVIEW");
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	public Submission save(final Submission submission) {
		Author principal;
		Submission result;

		principal = this.authorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(submission);
		Assert.isTrue(submission.getAuthor() == principal);

		result = this.submissionRepository.save(submission);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Submission submission) {
		Author principal;

		Assert.notNull(submission);

		principal = this.authorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(submission.getAuthor().getId() == principal.getId());

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

	public Submission reconstruct(final Submission submission,
			final BindingResult binding) {
		Submission result;
		if (submission.getId() == 0) {
			result = submission;
			result.setAuthor(this.authorService.findByPrincipal());
			result.setTicker(this.generateTicker(result.getAuthor()));
			result.setConference(submission.getConference());
			result.setMoment(new Date(System.currentTimeMillis() - 1));
			result.setStatus("UNDER-REVIEW");
		} else {
			result = this.submissionRepository.findOne(submission.getId());

			result.setStatus(submission.getStatus());
		}

		if (submission.getConference().getSubmissionDeadline()
				.before(submission.getMoment())) {
			binding.rejectValue("submissionDeadline",
					"conference.validation.submissionDeadline",
					"Submission deadline has elapsed");
		}

		this.validator.validate(result, binding);

		return result;
	}

}
