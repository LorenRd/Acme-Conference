package controllers.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.ReportService;
import controllers.AbstractController;
import domain.Author;
import domain.Report;

@Controller
@RequestMapping("/report/author")
public class ReportAuthorController extends AbstractController {


	@Autowired
	private AuthorService authorService;

	@Autowired
	private ReportService reportService;


	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reportId) {
		final ModelAndView result;
		Report report;
		Author author;
		
		author = this.authorService.findByPrincipal();
		report = this.reportService.findOne(reportId);
		
		if((report.getSubmission().getAuthor().getId() == author.getId()) && (report.getSubmission().getDecisionNotification())){
			result = new ModelAndView("report/display");
			result.addObject("report", report);
			result.addObject("requestURI", "report/display.do");
			
		}else{
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}


}
