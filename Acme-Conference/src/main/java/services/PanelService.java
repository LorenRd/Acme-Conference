
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PanelRepository;
import domain.Conference;
import domain.Panel;

@Service
@Transactional
public class PanelService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PanelRepository	panelRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ConferenceService		conferenceService;
	
	// Additional functions

	// Simple CRUD Methods

	public Panel create() {
		Panel result;

		result = new Panel();

		return result;
	}

	public Panel save(final Panel panel, final int conferenceId) {
		Panel saved;
		Conference conference;
		
		conference = this.conferenceService.findOne(conferenceId);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(panel.getStartMoment());
		calendar.add(Calendar.HOUR_OF_DAY, panel.getDuration());
		
		panel.setSchedule(calendar.getTime());
		panel.setConference(conference);
		
		saved = this.panelRepository.save(panel);
		return saved;
	}

	public Panel findOne(final int panelId) {
		Panel result;

		result = this.panelRepository.findOne(panelId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Panel> findAll() {
		Collection<Panel> result;

		result = this.panelRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Panel> findAllByConferenceId(final int conferenceId) {
		Collection<Panel> result;

		result = this.panelRepository.findAllByConferenceId(conferenceId);
		Assert.notNull(result);
		return result;
	}
	
	public void delete(Panel panel) {
		this.panelRepository.delete(panel);
	}
}
