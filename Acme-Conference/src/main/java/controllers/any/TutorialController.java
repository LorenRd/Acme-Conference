package controllers.any;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityCommentService;
import services.TutorialService;

import controllers.AbstractController;
import domain.ActivityComment;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	@Autowired
	private TutorialService tutorialService;

	@Autowired
	private ActivityCommentService activityCommentService;

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tutorialId) {
		final ModelAndView result;
		Tutorial tutorial;
		final Collection<ActivityComment> activityComments;
		final Collection<Section> sections;

		tutorial = this.tutorialService.findOne(tutorialId);

		activityComments = this.activityCommentService
				.findAllByActivity(tutorialId);
		sections = this.tutorialService.findSections(tutorialId);

		result = new ModelAndView("tutorial/display");
		result.addObject("tutorial", tutorial);
		result.addObject("activityComments", activityComments);
		result.addObject("sections", sections);
		result.addObject("requestURI", "tutorial/display.do");
		return result;
	}

}
