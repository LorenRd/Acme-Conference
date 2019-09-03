
package services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import domain.Author;
import domain.CameraReadyPaper;
import domain.Conference;
import domain.Message;
import domain.Submission;

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

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private CameraReadyPaperService	cameraReadyPaperService;


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

	public void score() {
		final DecimalFormat df = new DecimalFormat("0.00");

		//Conferences we want
		final Collection<Conference> allConferences = this.conferenceService.findAll();
		final Collection<Conference> conferences = new ArrayList<>();
		final Date today = new Date();
		for (final Conference c : allConferences) {
			final int difference = this.difference(c.getStartDate(), today);
			if (difference <= 12 || c.getStartDate().after(today))
				conferences.add(c);
		}

		//Get buzz words
		final Collection<String> buzzWords = new ArrayList<>();
		final Collection<String> voidWords = this.customisationService.find().getVoidWords();
		final Map<String, Integer> mapBuzz = new HashMap<>();
		for (final Conference c : conferences) {
			final List<String> ls = this.toLowerCase(this.join(Arrays.asList(c.getTitle(), c.getSummary())));
			for (final String s : ls)
				if (!voidWords.contains(s))
					if (!mapBuzz.containsKey(s))
						mapBuzz.put(s, 1);
					else
						mapBuzz.put(s, mapBuzz.get(s) + 1);
		}
		final double maxRatio = this.maxFrequency(mapBuzz) - 0.20 * this.maxFrequency(mapBuzz);
		for (final String s : mapBuzz.keySet())
			if (mapBuzz.get(s) >= maxRatio)
				buzzWords.add(s);

		//Compute the points
		final Map<Author, Integer> points = new HashMap<>();
		final Collection<Author> allAuthors = this.authorService.findAll();
		for (final Author a : allAuthors) {
			points.put(a, 0);

			final Collection<Submission> submissions = this.submissionService.findAllByAuthor(a.getId());

			for (final Submission s : submissions) {
				final List<CameraReadyPaper> cRP = new ArrayList<CameraReadyPaper>(this.cameraReadyPaperService.findBySubmissionId(s.getId()));

				CameraReadyPaper c;
				if (cRP.isEmpty())
					c = null;
				else
					c = cRP.get(0);

				if (c != null) {
					final List<String> ls = this.toLowerCase(this.join(Arrays.asList(c.getTitle(), c.getSummary(), c.getDocument())));
					for (final String word : ls)
						if (buzzWords.contains(word))
							points.put(a, points.get(a) + 1);
				}
			}
		}

		//Compute the scores
		final int maxPoints = this.maxPoints(points);

		for (final Author a : points.keySet())
			if (maxPoints != 0) {
				final Double n = (double) (points.get(a) / maxPoints);
				final Double score = Double.parseDouble(df.format(n));
				a.setScore(score);
				this.authorService.save(a);
			}
	}

	private int difference(final Date date1, final Date date2) {
		return (int) Math.abs(date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24 * 60);
	}

	private List<String> join(final List<String> ls) {
		final List<String> res = new ArrayList<>();
		for (final String s : ls) {
			final String[] ss = s.split(" ");
			for (int i = 0; i < ss.length; i++)
				res.add(ss[i]);
		}
		return res;
	}

	private List<String> toLowerCase(final List<String> ls) {
		final List<String> res = new ArrayList<>();
		for (final String s : ls)
			res.add(s.toLowerCase());
		return res;
	}

	private int maxFrequency(final Map<String, Integer> map) {
		int res = 0;
		for (final String s : map.keySet())
			if (map.get(s) > res)
				res = map.get(s);
		return res;
	}

	private int maxPoints(final Map<Author, Integer> map) {
		int res = 0;
		for (final Author a : map.keySet())
			if (map.get(a) > res)
				res = map.get(a);
		return res;
	}
}
