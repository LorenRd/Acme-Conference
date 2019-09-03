package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Panel;
import repositories.PanelRepository;

@Service
@Transactional
public class PanelService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PanelRepository panelRepository;

	public Panel findOne(final int panelid) {
		Panel result;

		result = this.panelRepository.findOne(panelid);
		Assert.notNull(result);
		return result;
	}

	public Collection<Panel> findByConferenceId(final int conferenceId) {
		return this.panelRepository.findByConferenceId(conferenceId);
	}

}
