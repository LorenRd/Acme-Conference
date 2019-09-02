package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PresentationRepository;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PresentationRepository presentationRepository;

	public Collection<Presentation> findByConferenceId(final int conferenceId) {
		return this.presentationRepository.findByConferenceId(conferenceId);
	}

}
