package controllers.any;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.ActivityComment;
import domain.Conference;
import domain.Panel;
import domain.Presentation;
import domain.Section;
import domain.Tutorial;

import services.ActivityCommentService;
import services.ConferenceService;
import services.PanelService;
import services.PresentationService;
import services.SectionService;
import services.TutorialService;

@Controller
@RequestMapping("/activity")
public class ActivityController {

	// Services

	@Autowired
	private TutorialService tutorialService;

	@Autowired
	private SectionService sectionService;

	@Autowired
	private PanelService panelService;

	@Autowired
	private PresentationService presentationService;

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private ActivityCommentService activityCommentService;

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int conferenceId) {
		final ModelAndView result;

		Collection<Tutorial> tutorials;
		Collection<Panel> panels;
		Collection<Presentation> presentations;
		Conference conference;

		conference = this.conferenceService.findOne(conferenceId);
		tutorials = this.tutorialService.findAllByConferenceId(conferenceId);
		panels = this.panelService.findAllByConferenceId(conferenceId);
		presentations = this.presentationService
				.findAllByConferenceId(conferenceId);

		if (conference.getIsFinal()) {
			result = new ModelAndView("activity/list");
			result.addObject("tutorials", tutorials);
			result.addObject("panels", panels);
			result.addObject("presentations", presentations);
			result.addObject("conference", conference);
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	// DISPLAY------------------------------
	// Tutorial

	@RequestMapping(value = "/tutorial/display", method = RequestMethod.GET)
	public ModelAndView displayTutorial(@RequestParam final int tutorialId) {
		// Inicializa resultado
		ModelAndView result;
		Tutorial tutorial;
		Collection<Section> sections;
		final Collection<ActivityComment> activityComments;

		// Busca en el repositorio
		tutorial = this.tutorialService.findOne(tutorialId);
		Assert.notNull(tutorial);

		sections = this.sectionService.findByTutorialId(tutorialId);

		activityComments = this.activityCommentService
				.findAllByActivity(tutorialId);

		// Crea y a�ade objetos a la vista
		result = new ModelAndView("tutorial/display");
		result.addObject("requestURI", "tutorial/display.do");
		result.addObject("tutorial", tutorial);
		result.addObject("sections", sections);
		result.addObject("activityComments", activityComments);

		// Env�a la vista
		return result;
	}

	// Panel
	@RequestMapping(value = "/panel/display", method = RequestMethod.GET)
	public ModelAndView displayPanel(@RequestParam final int panelId) {
		// Inicializa resultado
		ModelAndView result;
		Panel panel;
		final Collection<ActivityComment> activityComments;

		// Busca en el repositorio
		panel = this.panelService.findOne(panelId);
		Assert.notNull(panel);

		activityComments = this.activityCommentService
				.findAllByActivity(panelId);

		// Crea y a�ade objetos a la vista
		result = new ModelAndView("panel/display");
		result.addObject("requestURI", "panel/display.do");
		result.addObject("panel", panel);
		result.addObject("activityComments", activityComments);

		// Env�a la vista
		return result;
	}

	// Presentation
	@RequestMapping(value = "/presentation/display", method = RequestMethod.GET)
	public ModelAndView displayPresentation(
			@RequestParam final int presentationId) {
		// Inicializa resultado
		ModelAndView result;
		Presentation presentation;
		final Collection<ActivityComment> activityComments;

		// Busca en el repositorio
		presentation = this.presentationService.findOne(presentationId);
		Assert.notNull(presentation);

		activityComments = this.activityCommentService
				.findAllByActivity(presentationId);

		// Crea y a�ade objetos a la vista
		result = new ModelAndView("presentation/display");
		result.addObject("requestURI", "presentation/display.do");
		result.addObject("presentation", presentation);
		result.addObject("activityComments", activityComments);

		// Env�a la vista
		return result;
	}

}
