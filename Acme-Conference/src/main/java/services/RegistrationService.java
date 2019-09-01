package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.RegistrationRepository;
import security.Authority;

import domain.Actor;

@Service
@Transactional
public class RegistrationService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private RegistrationRepository registrationRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;
	

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
}
