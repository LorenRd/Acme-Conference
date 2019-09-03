
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.TutorialRepository;
import domain.Conference;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TutorialRepository	tutorialRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private SectionService		sectionService;
	
	@Autowired
	private ConferenceService		conferenceService;
	
	// Additional functions

	// Simple CRUD Methods

	public Tutorial create() {
		Tutorial result;

		result = new Tutorial();

		return result;
	}

	public Tutorial save(final Tutorial tutorial, final int conferenceId) {
		Tutorial saved;
		Conference conference;
		
		conference = this.conferenceService.findOne(conferenceId);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tutorial.getStartMoment());
		calendar.add(Calendar.HOUR_OF_DAY, tutorial.getDuration());
		
		tutorial.setSchedule(calendar.getTime());
		tutorial.setConference(conference);
		
		saved = this.tutorialRepository.save(tutorial);
		return saved;
	}

	public Tutorial findOne(final int tutorialId) {
		Tutorial result;

		result = this.tutorialRepository.findOne(tutorialId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Tutorial> findAll() {
		Collection<Tutorial> result;

		result = this.tutorialRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Tutorial> findAllByConferenceId(final int conferenceId) {
		Collection<Tutorial> result;

		result = this.tutorialRepository.findAllByConferenceId(conferenceId);
		Assert.notNull(result);
		return result;
	}
	
	public void delete(Tutorial tutorial) {
		Collection<Section> sections;
		
		sections = this.sectionService.findByTutorialId(tutorial.getId());
		
		for (Section section : sections) {
			this.sectionService.delete(section);
		}
		
		this.tutorialRepository.delete(tutorial);
	}

}
