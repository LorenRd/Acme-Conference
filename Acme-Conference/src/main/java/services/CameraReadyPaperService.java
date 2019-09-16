
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CameraReadyPaperRepository;
import repositories.SubmissionRepository;
import domain.CameraReadyPaper;
import domain.Submission;

@Service
@Transactional
public class CameraReadyPaperService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CameraReadyPaperRepository	cameraReadyPaperRepository;

	@Autowired
	private SubmissionRepository		submissionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private Validator					validator;


	// Simple CRUD Methods

	public CameraReadyPaper findOne(final int cameraReadyPaperId) {
		CameraReadyPaper result;

		result = this.cameraReadyPaperRepository.findOne(cameraReadyPaperId);
		Assert.notNull(result);
		return result;
	}

	public CameraReadyPaper create() {
		final CameraReadyPaper result = new CameraReadyPaper();

		final CameraReadyPaper cameraReadyPaper = new CameraReadyPaper();
		cameraReadyPaper.setTitle("");
		cameraReadyPaper.setAuthor("");
		cameraReadyPaper.setDocument("");
		cameraReadyPaper.setSummary("");

		return result;
	}

	public CameraReadyPaper save(final CameraReadyPaper cameraReadyPaper) {
		CameraReadyPaper result;

		Assert.notNull(cameraReadyPaper);

		result = this.cameraReadyPaperRepository.save(cameraReadyPaper);
		Assert.notNull(result);

		return result;
	}

	public void delete(final CameraReadyPaper cameraReadyPaper) {
		this.cameraReadyPaperRepository.delete(cameraReadyPaper);
	}

	public CameraReadyPaper reconstruct(final CameraReadyPaper cameraReadyPaper, final BindingResult binding) {
		CameraReadyPaper result;

		if (cameraReadyPaper.getId() == 0) {
			result = cameraReadyPaper;
			result.setSubmission(cameraReadyPaper.getSubmission());
			
			
		} else
			result = this.cameraReadyPaperRepository.findOne(cameraReadyPaper.getId());

		result.setTitle(cameraReadyPaper.getTitle());
		result.setAuthor(cameraReadyPaper.getAuthor());
		result.setSummary(cameraReadyPaper.getSummary());
		result.setDocument(cameraReadyPaper.getDocument());

		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public Collection<CameraReadyPaper> findAllByAuthor(final int authorId) {
		final Collection<CameraReadyPaper> result = new ArrayList<CameraReadyPaper>();
		Collection<Submission> submissions;
		Collection<CameraReadyPaper> cameraReadyPapers = new ArrayList<CameraReadyPaper>();

		submissions = this.submissionRepository.findAllByAuthorId(authorId);

		for (final Submission s : submissions) {
			cameraReadyPapers = this.cameraReadyPaperRepository.findBySubmissionId(s.getId());
			if (!cameraReadyPapers.isEmpty())
				result.addAll(cameraReadyPapers);
		}
		return result;
	}

	public Collection<CameraReadyPaper> findBySubmissionId(final int id) {

		Collection<CameraReadyPaper> result;
		result = this.cameraReadyPaperRepository.findBySubmissionId(id);
		return result;
	}
}
