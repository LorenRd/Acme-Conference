package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Paper;
import repositories.PaperRepository;

@Service
@Transactional
public class PaperService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PaperRepository paperRepository;

	// Supporting services ----------------------------------------------------

	// Simple CRUD Methods

	public Paper save(final Paper Paper) {
		Paper result;

		Assert.notNull(Paper);

		result = this.paperRepository.save(Paper);
		Assert.notNull(result);

		return result;
	}

	public void delete(Paper paper) {
		this.paperRepository.delete(paper);
	}

}
