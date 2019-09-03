package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.ReportRepository;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ReportRepository reportRepository;


	// Supporting services ----------------------------------------------------


	// Simple CRUD Methods

	// Business Methods

	public Collection<Report> findReportsBySubmissionId(final int submissionId) {
		Collection<Report> result;

		result = this.reportRepository.findReportsBySubmissionId(submissionId);
		return result;
	}

}
