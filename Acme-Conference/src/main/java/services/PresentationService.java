
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import domain.Conference;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PresentationRepository	presentationRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ConferenceService		conferenceService;
	
	// Additional functions

	// Simple CRUD Methods

	public Presentation create() {
		Presentation result;

		result = new Presentation();

		return result;
	}

	public Presentation save(final Presentation presentation, final int conferenceId) {
		Presentation saved;
		Conference conference;
		
		conference = this.conferenceService.findOne(conferenceId);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(presentation.getStartMoment());
		calendar.add(Calendar.HOUR_OF_DAY, presentation.getDuration());
		
		presentation.setSchedule(calendar.getTime());
		presentation.setConference(conference);
		
		saved = this.presentationRepository.save(presentation);
		return saved;
	}

	public Presentation findOne(final int presentationId) {
		Presentation result;

		result = this.presentationRepository.findOne(presentationId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Presentation> findAll() {
		Collection<Presentation> result;

		result = this.presentationRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Presentation> findAllByConferenceId(final int conferenceId) {
		Collection<Presentation> result;

		result = this.presentationRepository.findAllByConferenceId(conferenceId);
		Assert.notNull(result);
		return result;
	}
	public void delete(Presentation presentation) {
		this.presentationRepository.delete(presentation);
	}
}
