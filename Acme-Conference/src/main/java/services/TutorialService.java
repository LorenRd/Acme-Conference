
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.TutorialRepository;
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
	
	// Additional functions

	// Simple CRUD Methods

	public Tutorial create() {
		Tutorial result;

		result = new Tutorial();
		final Section initialSection = this.sectionService.create();
		Collection<Section> sections = new ArrayList<Section>();
		sections.add(initialSection);
		
		result.setSections(sections);

		return result;
	}

	public Tutorial save(final Tutorial tutorial) {
		Tutorial saved;
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
		Collection<Section> sections = tutorial.getSections();
		for (Section section : sections) {
			this.sectionService.delete(section);
		}
		
		this.tutorialRepository.delete(tutorial);
	}

}
