package controllers.any;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityCommentService;
import services.PanelService;

import controllers.AbstractController;
import domain.ActivityComment;
import domain.Panel;

@Controller
@RequestMapping("/panel")
public class PanelController extends AbstractController {

	@Autowired
	private PanelService panelService;

	@Autowired
	private ActivityCommentService activityCommentService;

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int panelId) {
		final ModelAndView result;
		Panel panel;
		final Collection<ActivityComment> activityComments;

		panel = this.panelService.findOne(panelId);

		activityComments = this.activityCommentService
				.findAllByActivity(panelId);

		result = new ModelAndView("panel/display");
		result.addObject("panel", panel);
		result.addObject("activityComments", activityComments);
		result.addObject("requestURI", "panel/display.do");
		return result;
	}

}
