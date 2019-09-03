package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ReviewerService;
import services.SubmissionService;

import controllers.AbstractController;
import domain.Administrator;
import domain.Submission;
import forms.SubmissionForm;


@Controller
@RequestMapping("/submission/administrator")
public class SubmissionAdministratorController extends AbstractController {

	// Services

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private ReviewerService reviewerService;

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
	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int submissionId) {
		ModelAndView result;
		Submission submission;
		SubmissionForm submissionForm;

		submission = this.submissionService.findOne(submissionId);
		submissionForm = this.submissionService.construct(submission);
		Assert.notNull(submission);
		result = this.createEditModelAndView(submissionForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(
			@ModelAttribute("submissionForm") SubmissionForm submissionForm,
			final BindingResult binding) {
		ModelAndView result;
		Submission submission;

		try {
			submission = this.submissionService.reconstruct(submissionForm,binding);
			this.submissionService.saveSubmissionAdmin(submission);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (ValidationException oops) {
			result = this.createEditModelAndView(submissionForm);
			oops.printStackTrace();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(submissionForm,
					"submission.commit.error");
			oops.printStackTrace();
		}
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
	
	
	//--------------------
	protected ModelAndView createEditModelAndView(final SubmissionForm submissionForm) {
		ModelAndView result;
		result = this.createEditModelAndView(submissionForm, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final SubmissionForm submissionForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("submission/edit");


		result.addObject("submissionForm", submissionForm);
		result.addObject("reviewers", this.reviewerService.findAll());
		result.addObject("message", messageCode);
		return result;
	}
}
