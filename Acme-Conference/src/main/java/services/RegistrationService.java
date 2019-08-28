
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CreditCardRepository;
import repositories.RegistrationRepository;
import domain.Author;
import domain.CreditCard;
import domain.Registration;
import forms.RegistrationForm;

@Service
@Transactional
public class RegistrationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RegistrationRepository	registrationRepository;

	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private Validator				validator;


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
