package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PresentationRepository presentationRepository;

	public Presentation findOne(final int presentationid) {
		Presentation result;

		result = this.presentationRepository.findOne(presentationid);
		Assert.notNull(result);
		return result;
	}

	public Collection<Presentation> findByConferenceId(final int conferenceId) {
		return this.presentationRepository.findByConferenceId(conferenceId);
	}

}
