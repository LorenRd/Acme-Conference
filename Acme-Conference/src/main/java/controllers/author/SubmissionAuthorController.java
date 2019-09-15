
package controllers.author;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.ValidationException;

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

import services.AuthorService;
import services.ConferenceService;
import services.ReportService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Author;
import domain.Report;
import domain.Submission;
import forms.SubmissionForm;

@Controller
@RequestMapping("/submission/author")
public class SubmissionAuthorController extends AbstractController {

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private ReportService		reportService;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Submission> submissions;
		Author principal;

		principal = this.authorService.findByPrincipal();

		submissions = this.submissionService.findAllByAuthor(principal.getId());

		result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("requestURI", "submission/author/list.do");

		return result;

	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int submissionId) {
		final ModelAndView result;
		Submission submission;
		Author author;
		Collection<Report> reports;

		author = this.authorService.findByPrincipal();
		submission = this.submissionService.findOne(submissionId);
		reports = this.reportService.findReportsBySubmissionId(submissionId);

		if (submission.getAuthor().getId() == author.getId()) {
			result = new ModelAndView("submission/display");
			result.addObject("submission", submission);
			result.addObject("requestURI", "submission/display.do");
			result.addObject("reports", reports);

		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Submission submission;
		SubmissionForm submissionForm;

		submission = this.submissionService.create();
		submissionForm = this.submissionService.construct(submission);
		result = this.createEditModelAndView(submissionForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final SubmissionForm submissionForm, final BindingResult binding) {
		ModelAndView result;
		Submission submission = null;

		try {
			submission = this.submissionService.reconstruct(submissionForm, binding);

			if (submission.getConference() == null)
				result = this.createEditModelAndView(submissionForm, "registration.commit.noConference");
			else {
				this.submissionService.save(submission);
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		} catch (final ValidationException oops) {
			// submissionForm = this.submissionService.construct(submission);
			result = this.createEditModelAndView(submissionForm);
			oops.printStackTrace();
		} catch (final Throwable oops) {
			// submissionForm = this.submissionService.construct(submission);
			result = this.createEditModelAndView(submissionForm, "submission.commit.error");
			oops.printStackTrace();
		}
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
	public ModelAndView edit(@Valid @ModelAttribute("submissionForm") final SubmissionForm submissionForm, final BindingResult binding) {
		ModelAndView result;
		Submission submission;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(submissionForm);
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
		} else
			try {
				submission = this.submissionService.reconstruct(submissionForm, binding);
				this.submissionService.save(submission);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final ValidationException oops) {
				result = this.createEditModelAndView(submissionForm);
				oops.printStackTrace();
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(submissionForm, "submission.commit.error");
				oops.printStackTrace();
			}
		return result;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int submissionId) {
		ModelAndView result;
		Submission submission;

		submission = this.submissionService.findOne(submissionId);

		try {
			this.submissionService.delete(submission);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.deleteModelAndView(submission, "submission.commit.error");
		}
		return result;
	}

	// -------------------

	protected ModelAndView createEditModelAndView(final SubmissionForm submissionForm) {
		ModelAndView result;
		result = this.createEditModelAndView(submissionForm, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final SubmissionForm submissionForm, final String messageCode) {
		ModelAndView result;

		if (submissionForm.getId() != 0)
			result = new ModelAndView("submission/edit");
		else
			result = new ModelAndView("submission/create");

		result.addObject("submissionForm", submissionForm);
		result.addObject("conferences", this.conferenceService.findAvailableConferences());
		result.addObject("message", messageCode);
		return result;
	}

	protected ModelAndView deleteModelAndView(final Submission submission) {
		ModelAndView result;

		result = this.deleteModelAndView(submission, null);

		return result;
	}

	protected ModelAndView deleteModelAndView(final Submission submission, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("submission/edit");
		result.addObject("submission", submission);
		result.addObject("message", messageCode);

		return result;
	}

}
