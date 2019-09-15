
package controllers.administrator;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

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

import services.ConferenceService;
import services.PanelService;
import services.PresentationService;
import services.SectionService;
import services.TutorialService;
import domain.Conference;
import domain.Panel;
import domain.Presentation;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/activity/administrator")
public class ActivityAdministratorController {

	// Services

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SectionService		sectionService;

	@Autowired
	private PanelService		panelService;

	@Autowired
	private PresentationService	presentationService;

	@Autowired
	private ConferenceService	conferenceService;


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
		presentations = this.presentationService.findAllByConferenceId(conferenceId);

		result = new ModelAndView("activity/list");
		result.addObject("tutorials", tutorials);
		result.addObject("panels", panels);
		result.addObject("presentations", presentations);
		result.addObject("conference", conference);

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

		sections = this.sectionService.findByTutorialId(tutorialId);
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

	//CREATION---------------------------------------------------------------
	//Tutorial
	@RequestMapping(value = "/tutorial/create", method = RequestMethod.GET)
	public ModelAndView createTutorial() {
		ModelAndView result;
		Tutorial tutorial;

		tutorial = this.tutorialService.create();
		result = this.createModelAndViewTutorial(tutorial);

		return result;
	}

	@RequestMapping(value = "/tutorial/create", method = RequestMethod.POST, params = "save")
	public ModelAndView createFinalTutorial(@RequestParam final int conferenceId, @ModelAttribute("tutorial") @Valid Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			result = this.createModelAndViewTutorial(tutorial);
		} else

			try {
				tutorial = this.tutorialService.save(tutorial, conferenceId);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createModelAndViewTutorial(tutorial, "tutorial.commit.error");
			}
		return result;
	}
	//Panel
	@RequestMapping(value = "/panel/create", method = RequestMethod.GET)
	public ModelAndView createPanel() {
		ModelAndView result;
		Panel panel;

		panel = this.panelService.create();
		result = this.createModelAndViewPanel(panel);

		return result;
	}

	@RequestMapping(value = "/panel/create", method = RequestMethod.POST, params = "save")
	public ModelAndView createFinalPanel(@RequestParam final int conferenceId, @ModelAttribute("panel") @Valid Panel panel, final BindingResult binding) {
		ModelAndView result;

		try {
			panel = this.panelService.save(panel, conferenceId);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createModelAndViewPanel(panel, "panel.commit.error");
		}
		return result;
	}

	//Presentation
	@RequestMapping(value = "/presentation/create", method = RequestMethod.GET)
	public ModelAndView createPresentation() {
		ModelAndView result;
		Presentation presentation;

		presentation = this.presentationService.create();
		result = this.createModelAndViewPresentation(presentation);

		return result;
	}

	@RequestMapping(value = "/presentation/create", method = RequestMethod.POST, params = "save")
	public ModelAndView createFinalPanel(@RequestParam final int conferenceId, @ModelAttribute("presentation") @Valid Presentation presentation, final BindingResult binding) {
		ModelAndView result;

		try {
			presentation = this.presentationService.save(presentation, conferenceId);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createModelAndViewPresentation(presentation, "presentation.commit.error");
		}
		return result;
	}
	//EDIT---------------------------------------------------------------
	//Tutorial
	@RequestMapping(value = "/tutorial/edit", method = RequestMethod.GET)
	public ModelAndView editTutorial(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(tutorialId);
		Assert.notNull(tutorial);
		result = this.createEditModelAndViewTutorial(tutorial);

		return result;
	}

	@RequestMapping(value = "/tutorial/edit", method = RequestMethod.POST)
	public ModelAndView saveFinalTutorial(@ModelAttribute("tutorial") @Valid Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;
		Tutorial original;

		try {
			original = this.tutorialService.findOne(tutorial.getId());
			tutorial = this.tutorialService.save(tutorial, original.getConference().getId());
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createModelAndViewTutorial(tutorial, "tutorial.commit.error");
		}
		return result;
	}
	//Panel
	@RequestMapping(value = "/panel/edit", method = RequestMethod.GET)
	public ModelAndView editPanel(@RequestParam final int panelId) {
		ModelAndView result;
		Panel panel;

		panel = this.panelService.findOne(panelId);
		Assert.notNull(panel);
		result = this.createEditModelAndViewPanel(panel);

		return result;
	}

	@RequestMapping(value = "/panel/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveFinalPanel(@ModelAttribute("panel") @Valid Panel panel, final BindingResult binding) {
		ModelAndView result;
		Panel original;
		try {
			original = this.panelService.findOne(panel.getId());
			panel = this.panelService.save(panel, original.getConference().getId());
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createModelAndViewPanel(panel, "panel.commit.error");
		}
		return result;
	}

	//Presentation
	@RequestMapping(value = "/presentation/edit", method = RequestMethod.GET)
	public ModelAndView editPresentation(@RequestParam final int presentationId) {
		ModelAndView result;
		Presentation presentation;

		presentation = this.presentationService.findOne(presentationId);
		Assert.notNull(presentation);
		result = this.createEditModelAndViewPresentation(presentation);

		return result;
	}

	@RequestMapping(value = "/presentation/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveFinalPresentation(@ModelAttribute("presentation") @Valid Presentation presentation, final BindingResult binding) {
		ModelAndView result;
		Presentation original;
		try {
			original = this.presentationService.findOne(presentation.getId());
			presentation = this.presentationService.save(presentation, original.getConference().getId());
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createModelAndViewPresentation(presentation, "presentation.commit.error");
		}
		return result;
	}

	//DELETE---------------------------------------------------------------
	//Tutorial
	@RequestMapping(value = "/tutorial/delete", method = RequestMethod.GET)
	public ModelAndView deleteTutorial(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(tutorialId);

		try {
			this.tutorialService.delete(tutorial);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.displayTutorial(tutorialId);
		}
		return result;
	}
	//Panel
	@RequestMapping(value = "/panel/delete", method = RequestMethod.GET)
	public ModelAndView deletePanel(@RequestParam final int panelId) {
		ModelAndView result;
		Panel panel;

		panel = this.panelService.findOne(panelId);

		try {
			this.panelService.delete(panel);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.displayPanel(panelId);
		}
		return result;
	}

	//Presentation
	@RequestMapping(value = "/presentation/delete", method = RequestMethod.GET)
	public ModelAndView deletePresentation(@RequestParam final int presentationId) {
		ModelAndView result;
		Presentation presentation;

		presentation = this.presentationService.findOne(presentationId);

		try {
			this.presentationService.delete(presentation);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.displayPanel(presentationId);
		}
		return result;
	}
	// -------------------

	protected ModelAndView createEditModelAndViewTutorial(final Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndViewTutorial(tutorial, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewTutorial(final Tutorial tutorial, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);
		result.addObject("message", messageCode);

		return result;
	}
	protected ModelAndView createEditModelAndViewPanel(final Panel panel) {
		ModelAndView result;

		result = this.createEditModelAndViewPanel(panel, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewPanel(final Panel panel, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("panel/edit");
		result.addObject("panel", panel);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndViewPresentation(final Presentation presentation) {
		ModelAndView result;

		result = this.createEditModelAndViewPresentation(presentation, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewPresentation(final Presentation presentation, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("presentation/edit");
		result.addObject("presentation", presentation);
		result.addObject("message", messageCode);

		return result;
	}

	//------------------

	private ModelAndView createModelAndViewPanel(final Panel panel) {
		ModelAndView result;

		result = this.createModelAndViewPanel(panel, null);
		return result;
	}

	private ModelAndView createModelAndViewPanel(final Panel panel, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("panel/create");
		result.addObject("panel", panel);
		result.addObject("message", messageCode);
		return result;
	}

	private ModelAndView createModelAndViewTutorial(final Tutorial tutorial) {
		ModelAndView result;

		result = this.createModelAndViewTutorial(tutorial, null);
		return result;
	}

	private ModelAndView createModelAndViewTutorial(final Tutorial tutorial, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("tutorial/create");
		result.addObject("tutorial", tutorial);
		result.addObject("message", messageCode);
		return result;
	}

	private ModelAndView createModelAndViewPresentation(final Presentation presentation) {
		ModelAndView result;

		result = this.createModelAndViewPresentation(presentation, null);
		return result;
	}

	private ModelAndView createModelAndViewPresentation(final Presentation presentation, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("presentation/create");
		result.addObject("presentation", presentation);
		result.addObject("message", messageCode);
		return result;
	}

}
