
package controllers.reviewer;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@Controller
@RequestMapping("/report/reviewer")
public class ReportReviewerController extends AbstractController {

	// Services
	@Autowired
	private ReportService	reportService;
	
	@Autowired
	private ReviewerService	reviewerService;

	@Autowired
	private SubmissionService submissionService;
	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Report> reports;
		Reviewer principal;
		
		principal = this.reviewerService.findByPrincipal();

		reports = this.reportService.findAllByReviewerId(principal.getId());

		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		result.addObject("requestURI", "report/reviewer/list.do");

		return result;

	}
	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reportId) {
		// Inicializa resultado
		ModelAndView result;
		Report report;
		Reviewer principal;
		
		principal = this.reviewerService.findByPrincipal();

		// Busca en el repositorio
		report = this.reportService.findOne(reportId);
		Assert.notNull(report);

		if (report.getReviewer().getId() == principal.getId()){
			result = new ModelAndView("report/display");
			result.addObject("requestURI", "report/display.do");
			result.addObject("report", report);

		}else{
			result = new ModelAndView("redirect:/welcome/index.do");
		}
			
		return result;
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Report report;

		report = this.reportService.create();
		result = this.createModelAndView(report);

		return result;
	}

	// --- CREATION ---

	//Save Final

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView createFinal(@ModelAttribute("report") Report report, final BindingResult binding) {
		ModelAndView result;

		try {
			report = this.reportService.reconstruct(report, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(report);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				report = this.reportService.save(report);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(report, "report.commit.error");
		}
		return result;
	}




	// -------------------

	private ModelAndView createModelAndView(final Report report) {
		ModelAndView result;

		result = this.createModelAndView(report, null);
		return result;
	}

	private ModelAndView createModelAndView(final Report report, final String messageCode) {
		ModelAndView result;
		Collection<Submission> submissions;
		Reviewer principal;
		
		principal = this.reviewerService.findByPrincipal();
		
		submissions = this.submissionService.findAllByReviewerIdUnderReview(principal.getId());
		
		result = new ModelAndView("report/create");
		result.addObject("report", report);
		result.addObject("submissions", submissions);
		result.addObject("message", messageCode);
		return result;
	}

}
