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

import repositories.HusitRepository;
import domain.Administrator;
import domain.Conference;
import domain.Husit;

@Service
@Transactional
public class HusitService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private HusitRepository husitRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private Validator validator;

	// Simple CRUD Methods

	public Husit create(final int conferenceId) {
		Husit result;
		Conference conference;

		conference = this.conferenceService.findOne(conferenceId);
		result = new Husit();
		result.setConference(conference);
		result.setIsDraft(true);
		result.setTicker(this.generateTicker());
		return result;
	}

	public String getAlphaNumeric() {
		char[] ch = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z' };

		char[] c = new char[5];
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			c[i] = ch[random.nextInt(ch.length)];
		}

		return new String(c);
	}

	private String generateTicker() {
		String result;
		String alphaNumeric = this.getAlphaNumeric();

		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);

		String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		String month = String.valueOf(cal.get(Calendar.MONTH));
		String year = String.valueOf(cal.get(Calendar.YEAR));

		year = year.substring(2, 4);

		if (Integer.parseInt(month) < 12) {
			month = Integer.toString(1 + Integer.parseInt(month));
		}

		if (Integer.parseInt(month) < 10)
			month = "0" + month;
		
		if (Integer.parseInt(day) < 10)
			day = "0" + day;

		result = alphaNumeric + "-" + day + "/" + month + "/" + year;
		if (this.repeatedTicker(result))
			this.generateTicker();

		return result;
	}

	public boolean repeatedTicker(final String ticker) {
		Boolean isRepeated = false;
		int repeats;

		repeats = this.husitRepository.findRepeatedTickers(ticker);

		if (repeats > 0)
			isRepeated = true;

		return isRepeated;
	}

	public Husit save(final Husit husit, final boolean isDraft) {
		Husit saved;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(husit);
		Assert.isTrue(husit.getConference().getAdministrator().getId() == principal
				.getId());
		husit.setIsDraft(isDraft);
		if (!isDraft)
			husit.setPublicationMoment(new Date(System.currentTimeMillis() - 1));
		saved = this.husitRepository.save(husit);
		return saved;
	}

	public Husit findOne(final int husitId) {
		Husit result;

		result = this.husitRepository.findOne(husitId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Husit> findAll() {
		Collection<Husit> result;

		result = this.husitRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Husit> findAllByConferenceId(final int conferenceId) {
		Collection<Husit> result;

		result = this.husitRepository.findAllByConferenceId(conferenceId);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Husit husit) {
		this.husitRepository.delete(husit);
	}

	public Husit reconstruct(final Husit husit, final BindingResult binding) {
		Husit original;
		if (husit.getId() == 0) {
			original = husit;
			original.setTicker(this.generateTicker());
			original.setBody(husit.getBody());
			original.setPicture(husit.getPicture());
			original.setPublicationMoment(new Date(
					System.currentTimeMillis() - 1));
			original.setIsDraft(true);
		} else {
			original = this.husitRepository.findOne(husit.getId());
			husit.setConference(original.getConference());
		}
		this.validator.validate(husit, binding);

		return husit;
	}

	public Collection<Husit> findAllByAdministratorId(final int administratorId) {
		Collection<Husit> result;

		result = this.husitRepository.findAllByAdministratorId(administratorId);
		Assert.notNull(result);
		return result;
	}

	public Double avgHusitPerConference() {
		final Double result;
		result = this.husitRepository.avgHusitPerConference();
		return result;
	}

	public Double stddevHusitPerConference() {
		final Double result;
		result = this.husitRepository.stddevHusitPerConference();
		return result;
	}

	public Double ratioPublishedHusits() {
		final Double result;
		result = this.husitRepository.ratioPublishedHusits();
		return result;
	}

	public Double ratioUnpublishedHusits() {
		final Double result;
		result = this.husitRepository.ratioUnpublishedHusits();
		return result;
	}

}
