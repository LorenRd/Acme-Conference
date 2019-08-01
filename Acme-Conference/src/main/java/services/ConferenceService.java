
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConferenceRepository;
import domain.Conference;

@Service
@Transactional
public class ConferenceService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ConferenceRepository	conferenceRepository;


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

	public Collection<Conference> findForthcoming() {
		Collection<Conference> result;

		result = this.conferenceRepository.findForthcoming();
		return result;
	}

	public Collection<Conference> findPast() {
		Collection<Conference> result;

		result = this.conferenceRepository.findPast();
		return result;
	}

	public Collection<Conference> findRunning() {
		Collection<Conference> result;

		result = this.conferenceRepository.findRunning();
		return result;
	}

	public Collection<Conference> findByKeyword(final String keyword) {
		Collection<Conference> result;

		result = this.conferenceRepository.findByKeyword(keyword);
		return result;
	}
}
