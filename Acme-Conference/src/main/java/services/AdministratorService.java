
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Message;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services-------------------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			messageService;


	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.administratorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(res);
		return res;
	}

	public boolean exists(final Integer arg0) {
		return this.administratorRepository.exists(arg0);
	}

	public Administrator create() {
		Administrator principal;
		principal = this.findByPrincipal();
		Assert.notNull(principal);

		Administrator result;
		UserAccount userAccount;
		Authority authority;
		result = new Administrator();
		userAccount = new UserAccount();
		authority = new Authority();

		authority.setAuthority("ADMIN");
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);

		return result;

	}

	public Administrator save(final Administrator administrator) {
		Administrator saved;
		UserAccount logedUserAccount;

		final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		logedUserAccount = this.actorService.createUserAccount(Authority.ADMIN);
		Assert.notNull(administrator, "administrator.not.null");

		if (administrator.getId() == 0) {
			administrator.getUserAccount().setPassword(passwordEncoder.encodePassword(administrator.getUserAccount().getPassword(), null));
			saved = this.administratorRepository.saveAndFlush(administrator);

		} else {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "administrator.notLogged");
			Assert.isTrue(logedUserAccount.equals(administrator.getUserAccount()), "administrator.notEqual.userAccount");
			saved = this.administratorRepository.findOne(administrator.getId());
			Assert.notNull(saved, "administrator.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername().equals(administrator.getUserAccount().getUsername()));
			Assert.isTrue(saved.getUserAccount().getPassword().equals(administrator.getUserAccount().getPassword()));
			saved = this.administratorRepository.saveAndFlush(administrator);
		}

		return saved;
	}

	public void delete() {
		Administrator principal;
		Collection<Message> messages;

		principal = this.findByPrincipal();
		Assert.notNull(principal);
		// Debería quedar al menos un administrador
		Assert.isTrue(this.findAll().size() > 1);

		messages = this.messageService.findBySenderId(principal.getId());
		this.messageService.deleteInBach(messages);

		this.administratorRepository.delete(principal);
	}

	public Administrator findOne(final int adminId) {
		Administrator result;

		result = this.administratorRepository.findOne(adminId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.administratorRepository.flush();
	}
}
