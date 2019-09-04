
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QuoletRepository;
import domain.Conference;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private QuoletRepository	quoletRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ConferenceService		conferenceService;
	
	// Additional functions

	// Simple CRUD Methods

	public Quolet create() {
		Quolet result;

		result = new Quolet();

		return result;
	}

	public Quolet save(final Quolet quolet, final int conferenceId) {
		Quolet saved;
		Conference conference;
		
		conference = this.conferenceService.findOne(conferenceId);

		quolet.setConference(conference);
		
		saved = this.quoletRepository.save(quolet);
		return saved;
	}

	public Quolet findOne(final int quoletId) {
		Quolet result;

		result = this.quoletRepository.findOne(quoletId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Quolet> findAll() {
		Collection<Quolet> result;

		result = this.quoletRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Quolet> findAllByConferenceId(final int conferenceId) {
		Collection<Quolet> result;

		result = this.quoletRepository.findAllByConferenceId(conferenceId);
		Assert.notNull(result);
		return result;
	}
	
	public void delete(Quolet quolet) {
		this.quoletRepository.delete(quolet);
	}
}
