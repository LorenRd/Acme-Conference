
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;


	// Supporting services-------------------------------------------

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
