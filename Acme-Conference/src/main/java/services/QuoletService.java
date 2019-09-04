
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

import repositories.QuoletRepository;
import domain.Administrator;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private QuoletRepository	quoletRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private Validator				validator;


	// Simple CRUD Methods

	public Quolet create() {
		Quolet result;

		result = new Quolet();
		result.setIsDraft(true);
		result.setTicker(this.generateTicker());
		return result;
	}
	private String generateTicker() {
		String result;

		final Date today = new Date();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(today);

		final String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		String month = String.valueOf(cal.get(Calendar.MONTH));
		String year = String.valueOf(cal.get(Calendar.YEAR));

		year = year.substring(2, 4);

		if (Integer.parseInt(month) < 10)
			month = "0" + month;

		final Random r = new Random();
		final char a = (char) (r.nextInt(26) + 'a');
		final char b = (char) (r.nextInt(26) + 'a');
		final char c = (char) (r.nextInt(26) + 'a');
		final char d = (char) (r.nextInt(26) + 'a');
		String code = String.valueOf(a) + String.valueOf(b) + String.valueOf(c) + String.valueOf(d);
		code = code.toUpperCase();
		result = year + month + day + "-" + code;

		if (this.repeatedTicker(result))
			this.generateTicker();

		return result;
	}

	public boolean repeatedTicker(final String ticker) {
		Boolean isRepeated = false;
		int repeats;

		repeats = this.quoletRepository.findRepeatedTickers(ticker);

		if (repeats > 0)
			isRepeated = true;

		return isRepeated;
	}


	public Quolet save(final Quolet quolet, final boolean isDraft) {
		Quolet saved;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(quolet);
		Assert.isTrue(quolet.getConference().getAdministrator().getId() == principal.getId());
		quolet.setIsDraft(isDraft);
		if (!isDraft)
			quolet.setPublicationMoment(new Date(System.currentTimeMillis() - 1));
		saved = this.quoletRepository.save(quolet);
		return saved;
	}

	public Quolet findOne(final int quoletId) {
		Quolet result;

		result = this.quoletRepository.findOne(quoletId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Quolet> findAll() {
		Collection<Quolet> result;

		result = this.quoletRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Quolet> findAllByConferenceId(final int conferenceId) {
		Collection<Quolet> result;

		result = this.quoletRepository.findAllByConferenceId(conferenceId);
		Assert.notNull(result);
		return result;
	}
	
	public void delete(Quolet quolet) {
		this.quoletRepository.delete(quolet);
	}
	
	public Quolet reconstruct(final Quolet quolet, final BindingResult binding) {
		Quolet original;
		if (quolet.getId() == 0) {
			original = quolet;
			original.setTicker(this.generateTicker());
			original.setBody(quolet.getBody());
			original.setPicture(quolet.getPicture());
			original.setPublicationMoment(new Date(System.currentTimeMillis() - 1));
			original.setIsDraft(true);
		} else {
			original = this.quoletRepository.findOne(quolet.getId());
			quolet.setIsDraft(false);
		}
		this.validator.validate(quolet, binding);

		return quolet;
	}

}
