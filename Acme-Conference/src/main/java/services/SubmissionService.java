
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import security.Authority;
import domain.Actor;
import domain.Administrator;
import domain.Author;
import domain.Message;
import domain.Paper;
import domain.Reviewer;
import domain.Submission;
import forms.SubmissionForm;

@Service
@Transactional
public class SubmissionService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SubmissionRepository	submissionRepository;

	@Autowired
	private PaperRepository			paperRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ReviewerService			reviewerService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private PaperService			paperService;

	@Autowired
	private Validator				validator;


	// Simple CRUD Methods

	public Submission findOne(final int submissionId) {
		Submission result;

		result = this.submissionRepository.findOne(submissionId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Submission> findAll() {
		Collection<Submission> result;

		result = this.submissionRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Submission> findAllByConferenceId(final int conferenceId) {
		Collection<Submission> result;

		result = this.submissionRepository.findAllByConferenceId(conferenceId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Submission> findAllByConferenceIdDecision(final int conferenceId) {
		Collection<Submission> result;

		result = this.submissionRepository.findAllByConferenceIdDecision(conferenceId);
		Assert.notNull(result);
		return result;
	}

	public Submission create() {
		Submission result;
		final Author principal;

		principal = this.authorService.findByPrincipal();
		Assert.notNull(principal);

		final Paper paper = new Paper();
		paper.setTitle("");
		paper.setAuthor("");
		paper.setDocument("");
		paper.setSummary("");

		result = new Submission();
		result.setAuthor(principal);
		result.setTicker(this.generateTicker(principal));
		result.setStatus("UNDER-REVIEW");
		result.setDecisionNotification(false);
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
	public void saveSubmissionAdmin(final Submission submission) {
		Submission result;
		result = this.submissionRepository.save(submission);
		Assert.notNull(result);
	}

	public void delete(final Submission submission) {
		Paper paper;

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

		if (author.getMiddleName().equals(null) || author.getMiddleName().equals(""))
			middleName = "X";
		else {
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
		final char[] ch = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
		};

		final char[] c = new char[4];
		final Random random = new Random();
		for (int i = 0; i < 4; i++)
			c[i] = ch[random.nextInt(ch.length)];

		return new String(c);
	}

	public boolean repeatedTicker(final Author author, final String ticker) {
		Boolean isRepeated = false;
		int repeats;

		repeats = this.submissionRepository.findRepeatedTickers(author.getId(), ticker);

		if (repeats > 0)
			isRepeated = true;

		return isRepeated;
	}

	public Submission reconstruct(final SubmissionForm submissionForm, final BindingResult binding) {
		Submission result;
		if (submissionForm.getId() == 0) {
			result = this.create();
			final Paper paper = new Paper();
			result.setTicker(this.generateTicker(this.authorService.findByPrincipal()));
			result.setMoment(new Date(System.currentTimeMillis() - 1));
			result.setStatus("UNDER-REVIEW");
			result.setAuthor(this.authorService.findByPrincipal());
			result.setConference(submissionForm.getConference());
			paper.setTitle(submissionForm.getTitle());
			paper.setAuthor(submissionForm.getAuthor());
			paper.setSummary(submissionForm.getSummary());
			paper.setDocument(submissionForm.getDocument());
			this.paperService.save(paper);
			result.setPaper(paper);

		} else {
			result = this.submissionRepository.findOne(submissionForm.getId());
			if (submissionForm.getReviewers() != null)
				result.setReviewers(submissionForm.getReviewers());
			else {
				result.getPaper().setTitle(submissionForm.getTitle());
				result.getPaper().setAuthor(submissionForm.getAuthor());
				result.getPaper().setSummary(submissionForm.getSummary());
				result.getPaper().setDocument(submissionForm.getDocument());
			}
		}

		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public SubmissionForm construct(final Submission submission) {
		final SubmissionForm submissionForm = new SubmissionForm();
		submissionForm.setId(submission.getId());
		submissionForm.setConference(submission.getConference());
		submissionForm.setTitle(submission.getPaper().getTitle());
		submissionForm.setAuthor(submission.getPaper().getAuthor());
		submissionForm.setSummary(submission.getPaper().getSummary());
		submissionForm.setDocument(submission.getPaper().getDocument());
		return submissionForm;
	}

	public Collection<Submission> findAllByAdministratorId(final int administratorId) {
		Collection<Submission> result;
		result = new ArrayList<Submission>();
		result = this.submissionRepository.findAllByAdministrator(administratorId);

		return result;
	}

	public Map<String, List<Submission>> groupByStatus(final Collection<Submission> submissions) {
		final Map<String, List<Submission>> result = new HashMap<String, List<Submission>>();

		Assert.notNull(submissions);
		for (final Submission r : submissions)
			if (result.containsKey(r.getStatus()))
				result.get(r.getStatus()).add(r);
			else {
				final List<Submission> l = new ArrayList<Submission>();
				l.add(r);
				result.put(r.getStatus(), l);
			}

		if (!result.containsKey("ACCEPTED"))
			result.put("ACCEPTED", new ArrayList<Submission>());
		if (!result.containsKey("UNDER-REVIEW"))
			result.put("UNDER-REVIEW", new ArrayList<Submission>());
		if (!result.containsKey("REJECTED"))
			result.put("REJECTED", new ArrayList<Submission>());

		return result;
	}

	public void reviewerAssignation() {
		Collection<Submission> submissions;
		Collection<Reviewer> reviewers;

		submissions = this.submissionRepository.findSubmissionUnderReview();
		reviewers = this.reviewerService.findAll();

		final Iterator<Reviewer> revIterator = reviewers.iterator();

		for (final Submission s : submissions) {
			final Collection<Reviewer> submissionReviewers = new ArrayList<Reviewer>();

			if (s.getReviewers().size() < 3)
				for (final Reviewer r : reviewers) {
					revIterator.next();
					for (final String e : r.getExpertises())
						if (s.getConference().getTitle().toLowerCase().contains(e.toLowerCase()) || s.getConference().getSummary().toLowerCase().contains(e.toLowerCase()))
							submissionReviewers.add(r);
					if (!revIterator.hasNext() && submissionReviewers.isEmpty())
						submissionReviewers.add(r);
					s.setReviewers(submissionReviewers);
				}
			this.saveSubmissionAdmin(s);
		}

	}

	public void decisionNotificationProcedure(final int conferenceId) {
		Administrator principal;
		final Collection<Actor> recipients = new ArrayList<Actor>();
		Collection<Submission> submissions = new ArrayList<Submission>();
		principal = this.administratorService.findByPrincipal();

		submissions = this.findAllByConferenceIdDecision(conferenceId);

		for (final Submission s : submissions) {
			s.setDecisionNotification(true);
			this.saveSubmissionAdmin(s);

			final Actor actor = this.actorService.findOne(s.getAuthor().getId());
			recipients.add(actor);
		}

		Message message;
		message = this.messageService.create();
		message.setSender(principal);
		message.setRecipients(recipients);
		message.setTopic("NOTIFICATION");
		message.setSubject("Submission notification");
		message.setBody("One of your submission has any update.");
		this.messageService.save(message);
	}

	//-------------------------------------

	public Double avgSubmissionPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.submissionRepository.avgSubmissionPerConference();

		return result;
	}

	public Double minSubmissionPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.submissionRepository.minSubmissionPerConference();
		return result;
	}

	public Double maxSubmissionPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.submissionRepository.maxSubmissionPerConference();

		return result;
	}

	public Double stddevSubmissionPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.submissionRepository.stddevSubmissionPerConference();

		return result;
	}

	public Collection<Submission> findAvailableSubmissions(final int authorId) {
		final Collection<Submission> result = new ArrayList<Submission>();
		final Collection<Submission> accepted = this.submissionRepository.findAcceptedSubmissions();

		final Date date = new Date();

		for (final Submission s : accepted)
			if (s.getConference().getCameraReadyDeadline().after(date) && s.getAuthor().getId() == authorId)
				result.add(s);

		return result;
	}
}
