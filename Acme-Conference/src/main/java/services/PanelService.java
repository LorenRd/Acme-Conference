package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Panel;
import repositories.PanelRepository;

@Service
@Transactional
public class PanelService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PanelRepository panelRepository;

	public Collection<Panel> findByConferenceId(final int conferenceId) {
		return this.panelRepository.findByConferenceId(conferenceId);
	}

}
