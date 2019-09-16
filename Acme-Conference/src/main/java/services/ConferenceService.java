
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.ConferenceRepository;
import repositories.ReportRepository;
import security.Authority;
import domain.Actor;
import domain.Administrator;
import domain.Conference;
import domain.Report;
import domain.Submission;

@Service
@Transactional
public class ConferenceService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ConferenceRepository	conferenceRepository;

	@Autowired
	private ReportRepository		reportRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private AdministratorService	administratorService;


	//	@Autowired
	//	private Validator				validator;

	// Simple CRUD Methods

	public Conference findOne(final int conferenceid) {
		Conference result;

		result = this.conferenceRepository.findOne(conferenceid);
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findAll() {
		Collection<Conference> result;

		result = this.conferenceRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Conference create() {
		Conference result;
		final Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Conference();
		result.setAdministrator(principal);
		result.setIsFinal(false);

		return result;
	}

	public Conference reconstruct(final Conference conference, final BindingResult binding) {
		Conference original;
		if (conference.getId() == 0) {
			original = conference;
			original.setAdministrator(this.administratorService.findByPrincipal());
			original.setIsFinal(false);
		} else {
			original = this.conferenceRepository.findOne(conference.getId());
			conference.setAdministrator(this.administratorService.findByPrincipal());
			conference.setIsFinal(false);
		}
		if (conference.getSubmissionDeadline().after(conference.getNotificationDeadline()))
			binding.rejectValue("submissionDeadline", "conference.validation.submissionDeadline", "Submission deadline date must be before than Notification deadline");
		if (conference.getNotificationDeadline().after(conference.getCameraReadyDeadline()))
			binding.rejectValue("notificationDeadline", "conference.validation.notificationDeadline", "Notification deadline date must be before than Camera Ready deadline");
		if (conference.getCameraReadyDeadline().after(conference.getStartDate()))
			binding.rejectValue("cameraReadyDeadline", "conference.validation.cameraReadyDeadline", "Camera Ready deadline date must be before than Start Date");
		if (conference.getStartDate().after(conference.getEndDate()))
			binding.rejectValue("startDate", "conference.validation.startDate", "Start date date must be before than End Date");
		if (conference.getEndDate().before(Calendar.getInstance().getTime()))
			binding.rejectValue("endDate", "conference.validation.endDate", "End date must be future");
		if (conference.getSubmissionDeadline().before(Calendar.getInstance().getTime()))
			binding.rejectValue("submissionDeadline", "conference.validation.submissionDeadline", "Submission deadline must be future");
		if (conference.getNotificationDeadline().before(Calendar.getInstance().getTime()))
			binding.rejectValue("notificationDeadline", "conference.validation.notificationDeadline", "Notification deadline must be future");
		if (conference.getCameraReadyDeadline().before(Calendar.getInstance().getTime()))
			binding.rejectValue("cameraReadyDeadline", "conference.validation.cameraReadyDeadline", "Camera Ready deadline must be future");
		if (conference.getStartDate().before(Calendar.getInstance().getTime()))
			binding.rejectValue("startDate", "conference.validation.startDate", "Start date must be future");

		return conference;
	}

	public Conference save(final Conference conference, final boolean isFinal) {
		Administrator principal;
		Conference result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(conference);
		Assert.isTrue(conference.getAdministrator() == principal);

		conference.setIsFinal(isFinal);

		result = this.conferenceRepository.save(conference);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Conference conference) {
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(conference.getAdministrator() == principal);

		this.conferenceRepository.delete(conference);
	}

	// Business Methods

	public Collection<Conference> findFinalForthcoming() {
		Collection<Conference> result;

		result = this.conferenceRepository.findFinalForthcoming();
		return result;
	}

	public Collection<Conference> findFinalPast() {
		Collection<Conference> result;

		result = this.conferenceRepository.findFinalPast();
		return result;
	}

	public Collection<Conference> findFinalRunning() {
		Collection<Conference> result;

		result = this.conferenceRepository.findFinalRunning();
		return result;
	}

	public Collection<Conference> findFinalByKeyword(final String keyword) {
		Collection<Conference> result;

		result = this.conferenceRepository.findFinalByKeyword(keyword);
		return result;
	}

	public Collection<Conference> findFinalForthcomingByKeyword(final String keyword) {
		Collection<Conference> result;

		result = this.conferenceRepository.findFinalForthcomingByKeyword(keyword);
		return result;
	}

	public Collection<Conference> findFinalPastByKeyword(final String keyword) {
		Collection<Conference> result;

		result = this.conferenceRepository.findFinalPastByKeyword(keyword);
		return result;
	}

	public Collection<Conference> findFinalRunningByKeyword(final String keyword) {
		Collection<Conference> result;

		result = this.conferenceRepository.findFinalRunningByKeyword(keyword);
		return result;
	}

	public Collection<Conference> findFinals() {

		Collection<Conference> result;

		result = this.conferenceRepository.findFinals();
		return result;
	}

	public Collection<Conference> findAvailableConferences() {

		final Collection<Conference> result = new ArrayList<Conference>();
		Collection<Conference> finals = new ArrayList<Conference>();
		Collection<Submission> allSubmissions = new ArrayList<Submission>();

		finals = this.findConferencesBeforeSubmissionDeadline();
		result.addAll(finals);
		allSubmissions = this.submissionService.findAll();

		for (final Submission s : allSubmissions)
			for (final Conference c : finals)
				if (s.getConference().getId() == c.getId())
					result.remove(c);

		return result;
	}

	public Collection<Conference> findConferencesBeforeSubmissionDeadline() {
		final Collection<Conference> result = this.findConferencesBeforeSubmissionDeadline();
		final Collection<Conference> finals = this.findConferencesBeforeSubmissionDeadline();
		final Collection<Submission> allSubmissions = this.submissionService.findAll();

		for (final Submission s : allSubmissions)
			for (final Conference c : finals)
				if (s.getConference().equals(c))
					result.remove(c);

		return result;
	}

	public Collection<Conference> findByAdministratorId(final int administratorId) {

		Collection<Conference> result;

		result = this.conferenceRepository.findByAdministratorId(administratorId);
		return result;
	}

	public Collection<Conference> submissionDeadline5daysOverByAdministratorId(final int administratorId) {

		Collection<Conference> result;

		final Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -5);
		final Date fiveDaysAgo = cal.getTime();

		result = this.conferenceRepository.submissionDeadline5daysOverByAdministratorId(administratorId, fiveDaysAgo);
		return result;
	}

	public Collection<Conference> notificationDeadline5daysOrLessByAdministratorId(final int administratorId) {

		Collection<Conference> result;

		final Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -5);
		final Date fiveDaysAgo = cal.getTime();

		result = this.conferenceRepository.notificationDeadline5daysOrLessByAdministratorId(administratorId, fiveDaysAgo);
		return result;
	}

	public Collection<Conference> cameraReadyDeadline5daysOrLessByAdministratorId(final int administratorId) {

		Collection<Conference> result;

		final Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -5);
		final Date fiveDaysAgo = cal.getTime();

		result = this.conferenceRepository.cameraReadyDeadline5daysOrLessByAdministratorId(administratorId, fiveDaysAgo);
		return result;
	}

	public Collection<Conference> conferences5daysOrLessByAdministratorId(final int administratorId) {

		Collection<Conference> result;

		final Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -5);
		final Date fiveDaysAgo = cal.getTime();

		result = this.conferenceRepository.cameraReadyDeadline5daysOrLessByAdministratorId(administratorId, fiveDaysAgo);
		return result;
	}

	public void analyseSubmissions(final Conference conference) {
		Collection<Submission> submissions;

		submissions = this.submissionService.findAllByConferenceId(conference.getId());

		for (final Submission s : submissions)
			if (s.getStatus().equals("UNDER-REVIEW")) {
				Collection<Report> reports;
				int possitive = 0;
				int negative = 0;
				reports = this.reportRepository.findReportsBySubmissionId(s.getId());
				for (final Report r : reports)
					if (r.getDecision().equals("ACCEPT") || r.getDecision().equals("BORDER-LINE"))
						possitive++;
					else
						negative++;
				if (possitive >= negative)
					s.setStatus("ACCEPTED");
				else
					s.setStatus("REJECTED");
				this.submissionService.saveSubmissionAdmin(s);
			}
	}

	//----------------------------------------

	public Double avgConferenceFees() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.conferenceRepository.avgConferenceFees();

		return result;
	}

	public Double minConferenceFees() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.conferenceRepository.minConferenceFees();
		return result;
	}

	public Double maxConferenceFees() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.conferenceRepository.maxConferenceFees();

		return result;
	}

	public Double stddevConferenceFees() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.conferenceRepository.stddevConferenceFees();

		return result;
	}

	public Double avgDaysPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result = 0.0;
		Collection<Conference> conferences;
		conferences = this.conferenceRepository.findAll();
		final Collection<Long> conferenceDuration = new ArrayList<Long>();
		for (final Conference c : conferences) {
			final long duration = (c.getEndDate().getTime() - c.getStartDate().getTime()) / 86400000;
			conferenceDuration.add(duration);
		}

		for (final Long d : conferenceDuration)
			result += d;
		result = result / conferenceDuration.size();

		return result;
	}

	public Long minDaysPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Long result = (long) 0;
		Collection<Conference> conferences;
		conferences = this.conferenceRepository.findAll();
		final Collection<Long> conferenceDuration = new ArrayList<Long>();
		for (final Conference c : conferences) {
			final long duration = (c.getEndDate().getTime() - c.getStartDate().getTime()) / 86400000;
			conferenceDuration.add(duration);
		}
		if (conferenceDuration.size() > 0) {
			result = conferenceDuration.iterator().next();
			for (final Long d : conferenceDuration)
				if (d < result)
					result = d;
		} else
			result = (long) 0.0;
		return result;
	}
	public Long maxDaysPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Long result = (long) 0;
		Collection<Conference> conferences;
		conferences = this.conferenceRepository.findAll();
		final Collection<Long> conferenceDuration = new ArrayList<Long>();
		for (final Conference c : conferences) {
			final long duration = (c.getEndDate().getTime() - c.getStartDate().getTime()) / 86400000;
			conferenceDuration.add(duration);
		}

		if (conferenceDuration.size() > 0) {
			result = conferenceDuration.iterator().next();
			for (final Long d : conferenceDuration)
				if (d > result)
					result = d;
		} else
			result = (long) 0.0;
		return result;
	}

	public Double stddevDaysPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result = 0.0;
		Collection<Conference> conferences;
		conferences = this.conferenceRepository.findAll();
		final Collection<Long> conferenceDuration = new ArrayList<Long>();
		for (final Conference c : conferences) {
			final long duration = (c.getEndDate().getTime() - c.getStartDate().getTime()) / 86400000;
			conferenceDuration.add(duration);
		}

		for (final Long d : conferenceDuration)
			result += d;
		final double mean = result / conferenceDuration.size();

		for (final Long d1 : conferenceDuration)
			result += Math.pow(d1 - mean, 2);

		return Math.sqrt(result / conferenceDuration.size());
	}

	public Collection<Conference> findAvailableConferencesForRegistration() {

		return this.conferenceRepository.findAvailableConferencesForRegistration();
	}

	public Collection<Conference> searchByMaxFee(final Double fee) {
		Collection<Conference> result;
		result = this.conferenceRepository.searchByMaxFee(fee);
		return result;
	}

	public Collection<Conference> searchByMaxFeeAdminId(final Double fee) {
		Collection<Conference> result;
		result = this.conferenceRepository.searchByMaxFeeAdminId(fee);
		return result;
	}

	public Collection<Conference> findByKeyword(final String keyword) {
		Collection<Conference> result;
		result = this.conferenceRepository.findByKeyword(keyword);
		return result;
	}

	public Collection<Conference> findByKeywordAdminId(final String keyword) {
		Collection<Conference> result;
		result = this.conferenceRepository.findByKeywordAdminId(keyword);
		return result;
	}

	public Collection<Conference> searchByCategory(final String categoryId) {
		Collection<Conference> result;
		result = this.conferenceRepository.searchByCategory(categoryId);
		return result;
	}

	public Collection<Conference> searchByCategoryAdminId(final String categoryId) {
		Collection<Conference> result;
		result = this.conferenceRepository.searchByCategoryAdminId(categoryId);
		return result;
	}

	public Collection<Conference> searchByDatesAdminId(final Date minDate, final Date maxDate) {
		Collection<Conference> result;
		result = this.conferenceRepository.searchByDateRangeAdminId(minDate, maxDate);
		return result;
	}

	public Collection<Conference> searchByDates(final Date minDate, final Date maxDate) {
		Collection<Conference> result;
		result = this.conferenceRepository.searchByDateRange(minDate, maxDate);
		return result;
	}

	public Double avgConferencePerCategory() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.conferenceRepository.avgConferencePerCategory();

		return result;
	}
	public Double minConferencePerCategory() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.conferenceRepository.minConferencePerCategory();

		return result;
	}
	public Double maxConferencePerCategory() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.conferenceRepository.maxConferencePerCategory();

		return result;
	}

	public Double stddevConferencePerCategory() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.conferenceRepository.stddevConferencePerCategory();

		return result;
	}

}
