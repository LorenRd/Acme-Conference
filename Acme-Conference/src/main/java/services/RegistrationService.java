
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RegistrationRepository;
import security.Authority;
import domain.Actor;
import domain.Registration;

@Service
@Transactional
public class RegistrationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RegistrationRepository	registrationRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService			actorService;


	//-------------------------------------

	public Double avgRegistrationPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.registrationRepository.avgRegistrationPerConference();

		return result;
	}

	public Double minRegistrationPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.registrationRepository.minRegistrationPerConference();
		return result;
	}

	public Double maxRegistrationPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.registrationRepository.maxRegistrationPerConference();

		return result;
	}

	public Double stddevRegistrationPerConference() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		Double result;

		result = this.registrationRepository.stddevRegistrationPerConference();

		return result;
	}

	public Collection<Registration> findAllByConferenceId(final int id) {
		Collection<Registration> result;

		result = this.registrationRepository.findAllByConferenceId(id);
		return result;
	}


	// Simple CRUD Methods

	public Registration findOne(final int registrationId) {
		Registration result;

		result = this.registrationRepository.findOne(registrationId);
		Assert.notNull(result);
		return result;
	}

	public Registration create() {
		Registration result;
		final Author principal;

		principal = this.authorService.findByPrincipal();
		Assert.notNull(principal);

		final CreditCard creditCard = new CreditCard();

		result = new Registration();
		result.setCreditCard(creditCard);
		result.setAuthor(principal);

		return result;
	}

	public Registration save(final Registration registration) {
		Author principal;
		Registration result;
		CreditCard creditCard;

		principal = this.authorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(registration);
		Assert.isTrue(registration.getAuthor().getId() == principal.getId());

		creditCard = this.creditCardRepository.save(registration.getCreditCard());

		registration.setCreditCard(creditCard);

		result = this.registrationRepository.save(registration);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Registration registration) {
		CreditCard creditCard;

		Assert.notNull(registration);
		Assert.isTrue(registration.getId() != 0);

		creditCard = registration.getCreditCard();

		if (creditCard != null)
			this.creditCardService.delete(creditCard);

		this.registrationRepository.delete(registration);
	}

	// Business Methods

	public Collection<Registration> findAllByAuthor(final int authorId) {
		Collection<Registration> result;

		result = this.registrationRepository.findAllByAuthorId(authorId);
		return result;
	}

	public Registration reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		Registration result;
		if (registrationForm.getId() == 0)
			result = this.create();
		else
			result = this.registrationRepository.findOne(registrationForm.getId());

		result.getCreditCard().setHolderName(registrationForm.getHolderName());
		result.getCreditCard().setBrandName(registrationForm.getBrandName());
		result.getCreditCard().setNumber(registrationForm.getNumber());
		result.getCreditCard().setExpirationMonth(registrationForm.getExpirationMonth());
		result.getCreditCard().setExpirationYear(registrationForm.getExpirationYear());
		result.getCreditCard().setCVV(registrationForm.getCVV());
		result.setConference(registrationForm.getConference());

		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public RegistrationForm construct(final Registration registration) {
		final RegistrationForm registrationForm = new RegistrationForm();
		registrationForm.setId(registration.getId());
		registrationForm.setConference(registration.getConference());
		registrationForm.setHolderName(registration.getCreditCard().getHolderName());
		registrationForm.setBrandName(registration.getCreditCard().getBrandName());
		registrationForm.setNumber(registration.getCreditCard().getNumber());
		registrationForm.setExpirationMonth(registration.getCreditCard().getExpirationMonth());
		registrationForm.setExpirationYear(registration.getCreditCard().getExpirationYear());
		registrationForm.setCVV(registration.getCreditCard().getCVV());
		return registrationForm;
	}

}
