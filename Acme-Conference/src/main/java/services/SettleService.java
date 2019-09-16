
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SettleRepository;
import domain.Administrator;
import domain.Conference;
import domain.Settle;

@Service
@Transactional
public class SettleService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SettleRepository		settleRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private Validator				validator;


	// Simple CRUD Methods

	public Settle create(final int conferenceId) {
		Settle result;
		Conference conference;

		conference = this.conferenceService.findOne(conferenceId);
		result = new Settle();
		result.setConference(conference);
		result.setIsDraft(true);
		result.setTicker(this.generateTicker());
		return result;
	}
	private String generateTicker() {
		String result;

		final Date today = new Date();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(today);

		String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		String month = String.valueOf(cal.get(Calendar.MONTH)+1);
		String year = String.valueOf(cal.get(Calendar.YEAR));

		year = year.substring(2, 4);

		if (Integer.parseInt(month) < 10)
			month = "0" + month;

		if (Integer.parseInt(day) < 10)
			day = "0" + day;

		final Random r = new Random();

	    final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    final String lower = upper.toLowerCase();
	    final String digits = "0123456789";
	    final String alphanum = upper + lower + digits;
		String code = "";
		
		for(int i=0; i<2; i++){
			char c = alphanum.charAt(r.nextInt(alphanum.length()-1));
			code += String.valueOf(c);
		}
		
		int random99 = r.nextInt(99);
		String random0100 = String.valueOf(random99);
		if(random99<10){
			random0100 = "0"+random0100;
		}
		code += random0100;

		result = code + "-"+ month + day + year;

		if (this.repeatedTicker(result))
			this.generateTicker();

		return result;
	}

	public boolean repeatedTicker(final String ticker) {
		Boolean isRepeated = false;
		int repeats;

		repeats = this.settleRepository.findRepeatedTickers(ticker);

		if (repeats > 0)
			isRepeated = true;

		return isRepeated;
	}

	public Settle save(final Settle settle, final boolean isDraft) {
		Settle saved;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(settle);
		Assert.isTrue(settle.getConference().getAdministrator().getId() == principal.getId());
		settle.setIsDraft(isDraft);
		if (!isDraft)
			settle.setPublicationMoment(new Date(System.currentTimeMillis() - 1));
		saved = this.settleRepository.save(settle);
		return saved;
	}

	public Settle findOne(final int settleId) {
		Settle result;

		result = this.settleRepository.findOne(settleId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Settle> findAll() {
		Collection<Settle> result;

		result = this.settleRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Settle> findAllByConferenceId(final int conferenceId) {
		Collection<Settle> result;

		result = this.settleRepository.findAllByConferenceId(conferenceId);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Settle settle) {
		this.settleRepository.delete(settle);
	}

	public Settle reconstruct(final Settle settle, final BindingResult binding) {
		Settle original;
		if (settle.getId() == 0) {
			original = settle;
			original.setTicker(this.generateTicker());
			original.setBody(settle.getBody());
			original.setPicture(settle.getPicture());
			original.setPublicationMoment(new Date(System.currentTimeMillis() - 1));
			original.setIsDraft(true);
		} else {
			original = this.settleRepository.findOne(settle.getId());
			settle.setConference(original.getConference());
		}
		this.validator.validate(settle, binding);

		return settle;
	}

	public Collection<Settle> findAllByAdministratorId(final int administratorId) {
		Collection<Settle> result;

		result = this.settleRepository.findAllByAdministratorId(administratorId);
		Assert.notNull(result);
		return result;
	}
	
	public Double avgSettlePerConference(){
		Double result=0.0;
		
		result = this.settleRepository.avgSettlePerConference();
		
		return result;
	}
	public Double stddevSettlePerConference(){
		Double result=0.0;
		
		result = this.settleRepository.stddevSettlePerConference();
		
		return result;
	}
	
	public Double settlesPublishedVersusTotal(){
		Double result=0.0;
		
		result = this.settleRepository.settlesPublishedVersusTotal();
		
		return result;
	}
	public Double settlesUnpublishedVersusTotal(){
		Double result=0.0;
		
		result = this.settleRepository.settlesUnpublishedVersusTotal();
		
		return result;
	}
}
