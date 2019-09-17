
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

import repositories.KolemRepository;
import domain.Administrator;
import domain.Conference;
import domain.Kolem;

@Service
@Transactional
public class KolemService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private KolemRepository			kolemRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private Validator				validator;


	// Simple CRUD Methods

	public Kolem create(final int conferenceId) {
		Kolem result;
		Conference conference;

		conference = this.conferenceService.findOne(conferenceId);
		result = new Kolem();
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
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String year = String.valueOf(cal.get(Calendar.YEAR));

		year = year.substring(2, 4);

		if (Integer.parseInt(month) < 10)
			month = "0" + month;

		if (Integer.parseInt(day) < 10)
			day = "0" + day;

		final Random r = new Random();
		final char a = (char) (r.nextInt(26) + 'a');
		final char b = (char) (r.nextInt(26) + 'a');
		String code = String.valueOf(a) + String.valueOf(b);
		code = code.toUpperCase();

		final Random r2 = new Random();
		final int code2 = r2.nextInt(9);
		final int code3 = r2.nextInt(9);

		result = day + "-" + code + "-" + month + "-" + code2 + code3 + "-" + year;

		if (this.repeatedTicker(result))
			this.generateTicker();

		return result;
	}

	public boolean repeatedTicker(final String ticker) {
		Boolean isRepeated = false;
		int repeats;

		repeats = this.kolemRepository.findRepeatedTickers(ticker);

		if (repeats > 0)
			isRepeated = true;

		return isRepeated;
	}

	public Kolem save(final Kolem kolem, final boolean isDraft) {
		Kolem saved;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(kolem);
		Assert.isTrue(kolem.getConference().getAdministrator().getId() == principal.getId());
		kolem.setIsDraft(isDraft);
		if (!isDraft)
			kolem.setPublicationMoment(new Date(System.currentTimeMillis() - 1));
		saved = this.kolemRepository.save(kolem);
		return saved;
	}

	public Kolem findOne(final int kolemId) {
		Kolem result;

		result = this.kolemRepository.findOne(kolemId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Kolem> findAll() {
		Collection<Kolem> result;

		result = this.kolemRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Kolem> findAllByConferenceId(final int conferenceId) {
		Collection<Kolem> result;

		result = this.kolemRepository.findAllByConferenceId(conferenceId);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Kolem kolem) {
		this.kolemRepository.delete(kolem);
	}

	public Kolem reconstruct(final Kolem kolem, final BindingResult binding) {
		Kolem original;
		if (kolem.getId() == 0) {
			original = kolem;
			original.setTicker(this.generateTicker());
			original.setBody(kolem.getBody());
			original.setPicture(kolem.getPicture());
			original.setIsDraft(true);
		} else {
			original = this.kolemRepository.findOne(kolem.getId());
			kolem.setConference(original.getConference());
		}
		this.validator.validate(kolem, binding);

		return kolem;
	}

	public Collection<Kolem> findAllByAdministratorId(final int administratorId) {
		Collection<Kolem> result;

		result = this.kolemRepository.findAllByAdministratorId(administratorId);
		Assert.notNull(result);
		return result;
	}

	public Double stddevKolemScoreConference() {
		Double result;

		if (this.findAll().size() == 0)
			result = 0.0;
		else
			result = this.kolemRepository.stddevKolemScoreConference();

		return result;
	}

	public Double ratioPublishedKolems() {
		Double result;

		if (this.findAll().size() == 0)
			result = 0.0;
		else
			result = this.kolemRepository.ratioPublishedKolems();

		return result;
	}

	public Double avgKolemScoreConference() {
		Double result;

		if (this.findAll().size() == 0)
			result = 0.0;
		else
			result = this.kolemRepository.avgKolemScoreConference();

		return result;
	}

}
