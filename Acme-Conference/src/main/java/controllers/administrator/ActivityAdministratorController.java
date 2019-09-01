package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Activity;
import domain.Panel;
import domain.Presentation;
import domain.Submission;
import domain.Tutorial;

import services.AdministratorService;

@Controller
@RequestMapping("/activity/administrator")
public class ActivityAdministratorController {

	// Services

	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private Tutorial

	// List

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int conferenceId) {
		final ModelAndView result;

		Collection <Tutorial> tutorials;
		Collection <Panel> panels;
		Collection <Presentation> presentations;
		
		tutorials = this.
		
		
		
		result = new ModelAndView("submission/display");
		result.addObject("submission", submission);

		return result;
	}
	
	
	

}
