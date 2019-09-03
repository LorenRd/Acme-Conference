
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed Repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting services
	@Autowired
	private SponsorService			sponsorService;


	// Constructors

	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods

	public Collection<Sponsorship> findByPrincipal() {
		Collection<Sponsorship> result;
		Sponsor principal;

		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.sponsorshipRepository.findBySponsorId(principal.getId());

		Assert.notNull(result);

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {

		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);

		Assert.notNull(result);

		return result;
	}

	public Sponsorship create() {
		Sponsorship result;
		Sponsor principal;
		CreditCard creditCard;

		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		creditCard = new CreditCard();

		result = new Sponsorship();
		Assert.notNull(result);
		result.setSponsor(principal);
		result.setCreditCard(creditCard);

		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Sponsorship result;
		Sponsor principal;
		CreditCard creditCard;

		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		//Imagino que en este momento la crea y la mete

		creditCard = this.creditCardRepository.save(sponsorship.getCreditCard());
		sponsorship.setCreditCard(creditCard);
		Assert.notNull(creditCard);

		result = this.sponsorshipRepository.save(sponsorship);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Sponsorship sponsorship) {
		Sponsor principal;

		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);

		principal = this.sponsorService.findByPrincipal();
		Assert.notNull(principal);

		this.sponsorshipRepository.delete(sponsorship);
	}

	// Other business methods

}
