package controllers.any;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Conference;
import domain.Panel;
import domain.Presentation;
import domain.Section;
import domain.Tutorial;

import services.ConferenceService;
import services.PanelService;
import services.PresentationService;
import services.TutorialService;

@Controller
@RequestMapping("/activity")
public class ActivityController {

	// Services

	@Autowired
	private TutorialService	tutorialService;

	@Autowired
	private PanelService	panelService;
	
	@Autowired
	private PresentationService	presentationService;
	
	@Autowired
	private ConferenceService	conferenceService;
	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int conferenceId) {
		final ModelAndView result;

		Collection <Tutorial> tutorials;
		Collection <Panel> panels;
		Collection <Presentation> presentations;
		Conference conference;
		
		conference = this.conferenceService.findOne(conferenceId);
		tutorials = this.tutorialService.findAllByConferenceId(conferenceId);
		panels = this.panelService.findAllByConferenceId(conferenceId);
		presentations = this.presentationService.findAllByConferenceId(conferenceId);
		
		if(conference.getIsFinal()){
			result = new ModelAndView("activity/list");
			result.addObject("tutorials", tutorials);
			result.addObject("panels", panels);
			result.addObject("presentations", presentations);
			result.addObject("conference", conference);
		}else{
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	//DISPLAY------------------------------
	//Tutorial
	
	@RequestMapping(value = "/tutorial/display", method = RequestMethod.GET)
	public ModelAndView displayTutorial(@RequestParam final int tutorialId) {
		// Inicializa resultado
		ModelAndView result;
		Tutorial tutorial;
		Collection<Section> sections;
		
		// Busca en el repositorio
		tutorial = this.tutorialService.findOne(tutorialId);
		Assert.notNull(tutorial);

		sections = tutorial.getSections();
		// Crea y añade objetos a la vista
		result = new ModelAndView("tutorial/display");
		result.addObject("requestURI", "tutorial/display.do");
		result.addObject("tutorial", tutorial);
		result.addObject("sections", sections);

		// Envía la vista
		return result;
	}
	//Panel
	@RequestMapping(value = "/panel/display", method = RequestMethod.GET)
	public ModelAndView displayPanel(@RequestParam final int panelId) {
		// Inicializa resultado
		ModelAndView result;
		Panel panel;

		// Busca en el repositorio
		panel = this.panelService.findOne(panelId);
		Assert.notNull(panel);

		// Crea y añade objetos a la vista
		result = new ModelAndView("panel/display");
		result.addObject("requestURI", "panel/display.do");
		result.addObject("panel", panel);

		// Envía la vista
		return result;
	}
	
	//Presentation
	@RequestMapping(value = "/presentation/display", method = RequestMethod.GET)
	public ModelAndView displayPresentation(@RequestParam final int presentationId) {
		// Inicializa resultado
		ModelAndView result;
		Presentation presentation;

		// Busca en el repositorio
		presentation = this.presentationService.findOne(presentationId);
		Assert.notNull(presentation);

		// Crea y añade objetos a la vista
		result = new ModelAndView("presentation/display");
		result.addObject("requestURI", "presentation/display.do");
		result.addObject("presentation", presentation);

		// Envía la vista
		return result;
	}
	
}
