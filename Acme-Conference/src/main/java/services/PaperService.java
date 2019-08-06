package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Paper;
import repositories.PaperRepository;

@Service
@Transactional
public class PaperService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private PaperRepository paperRepository;

	// Simple CRUD Methods
	public void delete(Paper paper) {
		this.paperRepository.delete(paper);
	}

	// Business Methods

	public Paper findBySubmission(final int submissionId) {
		Paper result;

		result = this.paperRepository.findBySubmissionId(submissionId);
		return result;
	}

}
