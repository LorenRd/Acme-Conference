
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.SectionRepository;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class SectionService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SectionRepository	sectionRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private TutorialService tutorialService;
	// Additional functions

	// Simple CRUD Methods

	public Section create(final int tutorialId) {
		Section result;
		Tutorial tutorial;
		
		tutorial = this.tutorialService.findOne(tutorialId);
		
		result = new Section();
		result.setTutorial(tutorial);
		
		return result;
	}

	public Section save(final Section section, final int tutorialId) {
		Section saved;
		Tutorial tutorial;
		
		tutorial = this.tutorialService.findOne(tutorialId);
		
		section.setTutorial(tutorial);
		
		saved = this.sectionRepository.save(section);
		return saved;
	}

	public Section findOne(final int sectionId) {
		Section result;

		result = this.sectionRepository.findOne(sectionId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Section> findAll() {
		Collection<Section> result;

		result = this.sectionRepository.findAll();
		Assert.notNull(result);
		return result;
	}
	
	public Collection<Section> findByTutorialId(final int tutorialId) {
		Collection<Section> result;

		result = this.sectionRepository.findAllByTutorialId(tutorialId);
		Assert.notNull(result);
		return result;
	}
	
	public void delete(Section section) {
		this.sectionRepository.delete(section);
	}
	
}
