
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuthorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Author;

@Service
@Transactional
public class AuthorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AuthorRepository	authorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;


	// Additional functions

	// Simple CRUD Methods

	public Author create() {
		Author result;

		result = new Author();

		// Nuevo userAccount con Author en la lista de authorities
		final UserAccount userAccount = this.actorService.createUserAccount(Authority.AUTHOR);

		result.setUserAccount(userAccount);

		return result;
	}

	public Author save(final Author author) {
		Author saved;
		UserAccount logedUserAccount;

		final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		logedUserAccount = this.actorService.createUserAccount(Authority.AUTHOR);
		Assert.notNull(author, "author.not.null");

		if (author.getId() == 0) {
			author.getUserAccount().setPassword(passwordEncoder.encodePassword(author.getUserAccount().getPassword(), null));
			saved = this.authorRepository.saveAndFlush(author);

		} else {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "author.notLogged");
			Assert.isTrue(logedUserAccount.equals(author.getUserAccount()), "author.notEqual.userAccount");
			saved = this.authorRepository.findOne(author.getId());
			Assert.notNull(saved, "author.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername().equals(author.getUserAccount().getUsername()));
			Assert.isTrue(saved.getUserAccount().getPassword().equals(author.getUserAccount().getPassword()));
			saved = this.authorRepository.saveAndFlush(author);
		}
		return saved;
	}

	public Author findOne(final int authorId) {
		Author result;

		result = this.authorRepository.findOne(authorId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Author> findAll() {
		Collection<Author> result;

		result = this.authorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Author findByPrincipal() {
		Author result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.authorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Author findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Author result;
		result = this.authorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public boolean exists(final Integer arg0) {
		return this.authorRepository.exists(arg0);
	}
}
