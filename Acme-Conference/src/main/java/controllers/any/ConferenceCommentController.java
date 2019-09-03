package controllers.any;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceCommentService;
import services.ConferenceService;
import controllers.AbstractController;
import domain.Conference;
import domain.ConferenceComment;

@Controller
@RequestMapping("/conferenceComment")
public class ConferenceCommentController extends AbstractController {

	// Services

	@Autowired
	private ConferenceCommentService conferenceCommentService;

	@Autowired
	private ConferenceService conferenceService;

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ConferenceComment conferenceComment;

		conferenceComment = this.conferenceCommentService.create();
		result = this.createEditModelAndView(conferenceComment);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(
			@ModelAttribute("conferenceComment") ConferenceComment conferenceComment,
			final BindingResult binding) {
		ModelAndView result;

		try {
			conferenceComment = this.conferenceCommentService.reconstruct(
					conferenceComment, binding);
			this.conferenceCommentService.save(conferenceComment);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (ValidationException oops) {
			result = this.createEditModelAndView(conferenceComment);
			oops.printStackTrace();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(conferenceComment,
					"conferenceComment.commit.error");
			oops.printStackTrace();
		}
		return result;
	}

	// -------------------

	protected ModelAndView createEditModelAndView(
			final ConferenceComment conferenceComment) {
		ModelAndView result;
		result = this.createEditModelAndView(conferenceComment, null);
		return result;
	}

	private ModelAndView createEditModelAndView(
			final ConferenceComment conferenceComment, final String messageCode) {
		ModelAndView result;
		final Collection<Conference> conferences;

		conferences = this.conferenceService.findFinals();

		result = new ModelAndView("conferenceComment/create");

		result.addObject("conferenceComment", conferenceComment);
		result.addObject("conferences", conferences);
		result.addObject("message", messageCode);
		return result;
	}

}
