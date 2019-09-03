package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Section;
import domain.Tutorial;

import repositories.SectionRepository;
import repositories.TutorialRepository;

@Service
@Transactional
public class TutorialService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private TutorialRepository tutorialRepository;

	@Autowired
	private SectionRepository sectionRepository;

	public Tutorial findOne(final int tutorialid) {
		Tutorial result;

		result = this.tutorialRepository.findOne(tutorialid);
		Assert.notNull(result);
		return result;
	}

	public Collection<Section> findSections(final int tutorialId) {
		return this.sectionRepository.findSections(tutorialId);
	}

	public Collection<Tutorial> findByConferenceId(final int conferenceId) {
		return this.tutorialRepository.findByConferenceId(conferenceId);
	}

}
