package controllers.any;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityCommentService;
import services.PresentationService;

import controllers.AbstractController;
import domain.ActivityComment;
import domain.Presentation;

@Controller
@RequestMapping("/presentation")
public class PresentationController extends AbstractController {

	@Autowired
	private PresentationService presentationService;

	@Autowired
	private ActivityCommentService activityCommentService;

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int presentationId) {
		final ModelAndView result;
		Presentation presentation;
		final Collection<ActivityComment> activityComments;

		presentation = this.presentationService.findOne(presentationId);

		activityComments = this.activityCommentService
				.findAllByActivity(presentationId);

		result = new ModelAndView("presentation/display");
		result.addObject("presentation", presentation);
		result.addObject("activityComments", activityComments);
		result.addObject("requestURI", "presentation/display.do");
		return result;
	}

}
