package controllers.author;

import java.util.Collection;

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

import services.AuthorService;
import services.CameraReadyPaperService;
import services.SubmissionService;

import controllers.AbstractController;
import domain.Author;
import domain.CameraReadyPaper;

@Controller
@RequestMapping("/cameraReadyPaper/author")
public class CameraReadyPaperAuthorController extends AbstractController {

	@Autowired
	private CameraReadyPaperService cameraReadyPaperService;

	@Autowired
	private SubmissionService submissionService;

	@Autowired
	private AuthorService authorService;

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<CameraReadyPaper> cameraReadyPapers;
		Author principal;

		principal = this.authorService.findByPrincipal();

		cameraReadyPapers = this.cameraReadyPaperService
				.findAllByAuthor(principal.getId());

		result = new ModelAndView("cameraReadyPaper/list");
		result.addObject("cameraReadyPapers", cameraReadyPapers);
		result.addObject("requestURI", "cameraReadyPaper/author/list.do");

		return result;

	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int cameraReadyPaperId) {
		final ModelAndView result;
		CameraReadyPaper cameraReadyPaper;

		cameraReadyPaper = this.cameraReadyPaperService
				.findOne(cameraReadyPaperId);

		result = new ModelAndView("cameraReadyPaper/display");
		result.addObject("cameraReadyPaper", cameraReadyPaper);
		result.addObject("requestURI", "cameraReadyPaper/display.do");
		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CameraReadyPaper cameraReadyPaper;

		cameraReadyPaper = this.cameraReadyPaperService.create();
		result = this.createEditModelAndView(cameraReadyPaper);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(
			@ModelAttribute("cameraReadyPaper") CameraReadyPaper cameraReadyPaper,
			final BindingResult binding) {
		ModelAndView result;

		try {
			cameraReadyPaper = this.cameraReadyPaperService.reconstruct(
					cameraReadyPaper, binding);
			this.cameraReadyPaperService.save(cameraReadyPaper);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (ValidationException oops) {
			result = this.createEditModelAndView(cameraReadyPaper);
			oops.printStackTrace();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(cameraReadyPaper,
					"cameraReadyPaper.commit.error");
			oops.printStackTrace();
		}
		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int cameraReadyPaperId) {
		ModelAndView result;
		CameraReadyPaper cameraReadyPaper;

		cameraReadyPaper = this.cameraReadyPaperService
				.findOne(cameraReadyPaperId);
		Assert.notNull(cameraReadyPaper);
		result = this.createEditModelAndView(cameraReadyPaper);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(
			@ModelAttribute("cameraReadyPaper") CameraReadyPaper cameraReadyPaper,
			final BindingResult binding) {

		ModelAndView result;

		try {
			cameraReadyPaper = this.cameraReadyPaperService.reconstruct(
					cameraReadyPaper, binding);
			this.cameraReadyPaperService.save(cameraReadyPaper);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (ValidationException oops) {
			result = this.createEditModelAndView(cameraReadyPaper);
			oops.printStackTrace();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(cameraReadyPaper,
					"cameraReadyPaper.commit.error");
			oops.printStackTrace();
		}
		return result;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int cameraReadyPaperId) {
		ModelAndView result;
		CameraReadyPaper cameraReadyPaper;

		cameraReadyPaper = this.cameraReadyPaperService
				.findOne(cameraReadyPaperId);

		try {
			this.cameraReadyPaperService.delete(cameraReadyPaper);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.deleteModelAndView(cameraReadyPaper,
					"cameraReadyPaper.commit.error");
		}
		return result;
	}

	// -------------------

	protected ModelAndView createEditModelAndView(
			final CameraReadyPaper cameraReadyPaper) {
		ModelAndView result;
		result = this.createEditModelAndView(cameraReadyPaper, null);
		return result;
	}

	private ModelAndView createEditModelAndView(
			final CameraReadyPaper cameraReadyPaper, final String messageCode) {
		ModelAndView result;

		if (cameraReadyPaper.getId() != 0)
			result = new ModelAndView("cameraReadyPaper/edit");
		else
			result = new ModelAndView("cameraReadyPaper/create");

		result.addObject("cameraReadyPaper", cameraReadyPaper);
		result.addObject("submissions",
				this.submissionService.findAvailableSubmissions());
		result.addObject("message", messageCode);
		return result;
	}

	protected ModelAndView deleteModelAndView(
			final CameraReadyPaper cameraReadyPaper) {
		ModelAndView result;

		result = this.deleteModelAndView(cameraReadyPaper, null);

		return result;
	}

	protected ModelAndView deleteModelAndView(
			final CameraReadyPaper cameraReadyPaper, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("cameraReadyPaper/edit");
		result.addObject("cameraReadyPaper", cameraReadyPaper);
		result.addObject("message", messageCode);

		return result;
	}

}
