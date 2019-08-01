
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReviewerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Reviewer;

@Service
@Transactional
public class ReviewerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ReviewerRepository	reviewerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;


	// Additional functions

	// Simple CRUD Methods

	public Reviewer create() {
		Reviewer result;

		result = new Reviewer();

		// Nuevo userAccount con Reviewer en la lista de Reviewerities
		final UserAccount userAccount = this.actorService.createUserAccount(Authority.REVIEWER);

		result.setUserAccount(userAccount);

		return result;
	}

	public Reviewer save(final Reviewer reviewer) {
		Reviewer saved;
		UserAccount logedUserAccount;

		final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		logedUserAccount = this.actorService.createUserAccount(Authority.REVIEWER);
		Assert.notNull(reviewer, "reviewer.not.null");

		if (reviewer.getId() == 0) {
			reviewer.getUserAccount().setPassword(passwordEncoder.encodePassword(reviewer.getUserAccount().getPassword(), null));
			saved = this.reviewerRepository.saveAndFlush(reviewer);

		} else {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "Reviewer.notLogged");
			Assert.isTrue(logedUserAccount.equals(reviewer.getUserAccount()), "Reviewer.notEqual.userAccount");
			saved = this.reviewerRepository.findOne(reviewer.getId());
			Assert.notNull(saved, "Reviewer.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername().equals(reviewer.getUserAccount().getUsername()));
			Assert.isTrue(saved.getUserAccount().getPassword().equals(reviewer.getUserAccount().getPassword()));
			saved = this.reviewerRepository.saveAndFlush(reviewer);
		}
		return saved;
	}

	public Reviewer findOne(final int reviewerId) {
		Reviewer result;

		result = this.reviewerRepository.findOne(reviewerId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Reviewer> findAll() {
		Collection<Reviewer> result;

		result = this.reviewerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Reviewer findByPrincipal() {
		Reviewer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.reviewerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Reviewer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Reviewer result;
		result = this.reviewerRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public boolean exists(final Integer arg0) {
		return this.reviewerRepository.exists(arg0);
	}
}
