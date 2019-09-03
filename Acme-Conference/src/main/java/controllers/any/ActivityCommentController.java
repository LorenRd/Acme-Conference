package controllers.any;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityCommentService;
import services.ActivityService;
import controllers.AbstractController;
import domain.ActivityComment;

@Controller
@RequestMapping("/activityComment")
public class ActivityCommentController extends AbstractController {

	// Services

	@Autowired
	private ActivityCommentService activityCommentService;

	@Autowired
	private ActivityService activityService;

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int activityId) {
		ModelAndView result;
		ActivityComment activityComment;

		activityComment = this.activityCommentService.create();
		activityComment.setActivity(this.activityService.findOne(activityId));
		result = this.createEditModelAndView(activityComment);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(
			@ModelAttribute("activityComment") ActivityComment activityComment,
			@RequestParam final int activityId, final BindingResult binding) {
		ModelAndView result;

		try {
			activityComment = this.activityCommentService.reconstruct(
					activityComment, activityId, binding);
			this.activityCommentService.save(activityComment);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (ValidationException oops) {
			result = this.createEditModelAndView(activityComment);
			oops.printStackTrace();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(activityComment,
					"activityComment.commit.error");
			oops.printStackTrace();
		}
		return result;
	}

	// -------------------

	protected ModelAndView createEditModelAndView(
			final ActivityComment activityComment) {
		ModelAndView result;
		result = this.createEditModelAndView(activityComment, null);
		return result;
	}

	private ModelAndView createEditModelAndView(
			final ActivityComment activityComment, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("activityComment/create");

		result.addObject("activityComment", activityComment);
		result.addObject("message", messageCode);
		return result;
	}

}
