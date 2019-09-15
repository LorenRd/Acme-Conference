package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReportRepository;
import domain.Report;
import domain.Reviewer;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ReportRepository reportRepository;


	// Supporting services ----------------------------------------------------
	@Autowired
	private ReviewerService reviewerService;

	@Autowired
	private Validator				validator;

	// Simple CRUD Methods

	public Report create() {
		Report result;
		final Reviewer principal;

		principal = this.reviewerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Report();
		result.setReviewer(principal);

		return result;

	}

	public Report save(final Report report) {
		Reviewer principal;
		Report result;

		principal = this.reviewerService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(report);
		Assert.isTrue(report.getReviewer() == principal);


		result = this.reportRepository.save(report);
		Assert.notNull(result);

		return result;
	}
	
	
	public Report reconstruct(final Report report, final BindingResult binding) {
		Report original;
		
		original = report;
		original.setReviewer(this.reviewerService.findByPrincipal());

		this.validator.validate(original, binding);

		return original;
	}
	
	
	public Report findOne(final int reportId){
		Report result;
		result = this.reportRepository.findOne(reportId);
		return result;
	}
	
	// Business Methods

	public Collection<Report> findReportsBySubmissionId(final int submissionId) {
		Collection<Report> result;

		result = this.reportRepository.findReportsBySubmissionId(submissionId);
		return result;
	}
	
	public Collection<Report> findAllByReviewerId(final int reviewerId) {
		Collection<Report> result;

		result = this.reportRepository.findAllByReviewerId(reviewerId);
		return result;
	}


}
