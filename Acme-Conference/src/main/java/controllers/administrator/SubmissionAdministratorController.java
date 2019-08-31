package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.SubmissionService;

import controllers.AbstractController;
import domain.Administrator;
import domain.Submission;


@Controller
@RequestMapping("/submission/administrator")
public class SubmissionAdministratorController extends AbstractController {

	// Services

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private AdministratorService	administratorService;

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Submission> submissions;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();

		submissions = this.submissionService.findAllByAdministratorId(principal.getId());

		result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("requestURI", "submission/administrator/list.do");

		return result;

	}
	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"submissionStatus"
	})
	public ModelAndView listByStatus(@RequestParam final int submissionStatus) {
		final ModelAndView result;
		Map<String, List<Submission>> groupedSubmission;
		Collection<Submission> submissions;
		Administrator principal;
		principal = this.administratorService.findByPrincipal();

		submissions = this.submissionService.findAllByAdministratorId(principal.getId());
		
		groupedSubmission = this.submissionService.groupByStatus(submissions);

		if (submissionStatus == 0)
			submissions = this.submissionService.findAllByAdministratorId(principal.getId());
		else if (submissionStatus == 1)
			submissions = new ArrayList<Submission>(groupedSubmission.get("ACCEPTED"));
		else if (submissionStatus == 2)
			submissions = new ArrayList<Submission>(groupedSubmission.get("UNDER-REVIEW"));
		else if (submissionStatus == 3)
			submissions = new ArrayList<Submission>(groupedSubmission.get("REJECTED"));
		else
			submissions = null;

		result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("submissionURI", "submission/administrator/list.do");

		return result;

	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int submissionId) {
		final ModelAndView result;
		Submission submission;

		submission = this.submissionService.findOne(submissionId);

		result = new ModelAndView("submission/display");
		result.addObject("submission", submission);

		return result;
	}
	
	//Assing reviewers procedure
	@RequestMapping(value = "/list", method = RequestMethod.GET, params ="assignReviewers")
	public ModelAndView assignReviewers(){
		final ModelAndView result;
		
		this.submissionService.reviewerAssignation();
		
		result = new ModelAndView("redirect:list.do");
		
		return result;
	}
	
	//Decision notification procedure
	@RequestMapping(value = "/display", method = RequestMethod.GET, params ="decisionNotification")
	public ModelAndView decisionNotificationProcedure(@RequestParam final int submissionId){
		final ModelAndView result;
		
		this.submissionService.decisionNotificationProcedure(submissionId);
		
		result = new ModelAndView("redirect:list.do");
		
		return result;
	}
	
	
}
