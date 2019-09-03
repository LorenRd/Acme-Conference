
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repository ------------------------

	@Autowired
	private SponsorRepository	sponsorRepository;


	// Supporting services ----------------------

	// Constructor
	public SponsorService() {
		super();
	}

	// Simple CRUDs methods

	public Sponsor create() {
		Sponsor result;

		result = new Sponsor();

		return result;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;
		result = this.sponsorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Sponsor findOne(final int sponsorId) {
		Sponsor result;
		result = this.sponsorRepository.findOne(sponsorId);
		return result;
	}

	public Sponsor findByPrincipal() {
		Sponsor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Sponsor result;
		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Sponsor saved;
		Assert.notNull(sponsor);

		if (sponsor.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			sponsor.getUserAccount().setPassword(passwordEncoder.encodePassword(sponsor.getUserAccount().getPassword(), null));
			saved = this.sponsorRepository.save(sponsor);
		} else {
			Sponsor principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);
			saved = this.sponsorRepository.save(sponsor);
		}

		return saved;
	}

	// Other business methods

}
