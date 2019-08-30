
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConferenceRepository;
import repositories.SubmissionRepository;
import domain.Administrator;
import domain.Conference;
import domain.Submission;

@Service
@Transactional
public class ConferenceService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ConferenceRepository	conferenceRepository;

	@Autowired
	private SubmissionRepository	submissionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


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
			original.setAdministrator(this.administratorService.findByPrincipal());
			original.setIsFinal(false);
		}

		this.validator.validate(conference, binding);

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
		final Collection<Conference> result = this.findFinals();
		final Collection<Conference> finals = this.findFinals();
		final Collection<Submission> allSubmissions = this.submissionRepository.findAll();

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

		result = this.conferenceRepository.submissionDeadline5daysOverByAdministratorId(administratorId, fiveDaysAgo.toString());
		return result;
	}

	public Collection<Conference> notificationDeadline5daysOrLessByAdministratorId(final int administratorId) {

		Collection<Conference> result;

		final Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -5);
		final Date fiveDaysAgo = cal.getTime();

		result = this.conferenceRepository.notificationDeadline5daysOrLessByAdministratorId(administratorId, fiveDaysAgo.toString());
		return result;
	}

	public Collection<Conference> cameraReadyDeadline5daysOrLessByAdministratorId(final int administratorId) {

		Collection<Conference> result;

		final Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -5);
		final Date fiveDaysAgo = cal.getTime();

		result = this.conferenceRepository.cameraReadyDeadline5daysOrLessByAdministratorId(administratorId, fiveDaysAgo.toString());
		return result;
	}

	public Collection<Conference> conferences5daysOrLessByAdministratorId(final int administratorId) {

		Collection<Conference> result;

		final Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -5);
		final Date fiveDaysAgo = cal.getTime();

		result = this.conferenceRepository.cameraReadyDeadline5daysOrLessByAdministratorId(administratorId, fiveDaysAgo.toString());
		return result;
	}

	//	public Collection<Conference> searchConference(final Date dateMin, final Date dateMax, final Double maxFee, final String keyword, final int categoryId) {
	//
	//		Collection<Conference> result;
	//
	//		result = this.conferenceRepository.searchConference(dateMin, dateMax, maxFee, keyword, categoryId);
	//		return result;
	//	}
}
