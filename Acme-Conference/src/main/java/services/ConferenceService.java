package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConferenceRepository;
import repositories.SubmissionRepository;
import domain.Conference;
import domain.Submission;

@Service
@Transactional
public class ConferenceService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ConferenceRepository conferenceRepository;

	@Autowired
	private SubmissionRepository submissionRepository;

	// Supporting services ----------------------------------------------------

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

	public Collection<Conference> findFinalForthcomingByKeyword(
			final String keyword) {
		Collection<Conference> result;

		result = this.conferenceRepository
				.findFinalForthcomingByKeyword(keyword);
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
		Collection<Conference> result = this.findFinals();
		Collection<Conference> finals = this.findFinals();
		Collection<Submission> allSubmissions = this.submissionRepository
				.findAll();

		for (Submission s : allSubmissions) {
			for (Conference c : finals) {
				if (s.getConference().equals(c)) {
					result.remove(c);
				}
			}
		}

		return result;
	}

	public Collection<Conference> findByAdministratorId(int administratorId) {

		Collection<Conference> result;

		result = this.conferenceRepository.findByAdministratorId(administratorId);
		return result;
	}



}
