package controllers.author;

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

import services.AuthorService;
import services.ConferenceService;
import services.PaperService;
import services.SubmissionService;

import controllers.AbstractController;
import domain.Author;
import domain.Conference;
import domain.Paper;
import domain.Submission;

@Controller
@RequestMapping("/submission/author")
public class SubmissionAuthorController extends AbstractController {

	@Autowired
	private SubmissionService submissionService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private PaperService paperService;

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
		// Inicializa resultado
		ModelAndView result;
		Submission submission;
		Paper paper = this.paperService.findBySubmission(submissionId);

		// Busca en el repositorio
		submission = this.submissionService.findOne(submissionId);
		Assert.notNull(submission);

		// Crea y añade objetos a la vista
		result = new ModelAndView("submission/display");
		result.addObject("requestURI", "submission/display.do");
		result.addObject("submission", submission);
		result.addObject("paper", paper);

		// Envía la vista
		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Submission submission;

		submission = this.submissionService.create();
		result = this.createModelAndView(submission);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(
			@ModelAttribute("submission") Submission submission,
			final BindingResult binding) {
		ModelAndView result;

		try {
			submission = this.submissionService
					.reconstruct(submission, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(submission);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				submission = this.submissionService.save(submission);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(submission,
					"submission.commit.error");
		}
		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int submissionId) {
		ModelAndView result;
		Submission submission;

		submission = this.submissionService.findOne(submissionId);
		Assert.notNull(submission);
		result = this.createEditModelAndView(submission);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(
			@ModelAttribute("submission") Submission submission,
			final BindingResult binding) {
		ModelAndView result;

		try {
			submission = this.submissionService
					.reconstruct(submission, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(submission);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				submission = this.submissionService.save(submission);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(submission,
					"submission.commit.error");
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
			result = this.createEditModelAndView(submission,
					"submission.commit.error");
		}
		return result;
	}

	// -------------------

	protected ModelAndView createEditModelAndView(final Submission submission) {
		ModelAndView result;

		result = this.createEditModelAndView(submission, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Submission submission,
			final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("submission/edit");
		result.addObject("submission", submission);
		result.addObject("message", messageCode);

		return result;
	}

	private ModelAndView createModelAndView(final Submission submission) {
		ModelAndView result;

		result = this.createModelAndView(submission, null);
		return result;
	}

	private ModelAndView createModelAndView(final Submission submission,
			final String messageCode) {
		ModelAndView result;
		Collection<Conference> conferences;

		conferences = this.conferenceService.findAvailableConferences();

		result = new ModelAndView("submission/create");
		result.addObject("submission", submission);
		result.addObject("conferences", conferences);
		result.addObject("message", messageCode);
		return result;
	}

}
