package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SectionService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Section;
import domain.Tutorial;
@Controller
@RequestMapping("/section/administrator")
public class SectionAdministratorController extends AbstractController {
	// Services
	@Autowired
	private TutorialService tutorialService;

	@Autowired
	private SectionService	sectionService;


		//Create
		
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Section section;
			
			section = this.sectionService.create();
			result = this.createModelAndView(section);

			return result;
		}

		// --- CREATION ---
		
		
		//Save Final
		
		@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
		public ModelAndView createFinal(@RequestParam final int tutorialId, @ModelAttribute("section") Section section, final BindingResult binding) {
			ModelAndView result;
			Tutorial tutorial;
			Collection <Section> sections;
			
			tutorial = this.tutorialService.findOne(tutorialId);
			sections = tutorial.getSections();
			
			
			try {
				section = this.sectionService.save(section);
				sections.add(section);

				result = new ModelAndView("redirect:/welcome/index.do");
				
			} catch (final Throwable oops) {
				result = this.createModelAndView(section, "section.commit.error");
			}
			return result;
		} 

		
		// -------------------
		
		private ModelAndView createModelAndView(final Section section) {
			ModelAndView result;

			result = this.createModelAndView(section, null);
			return result;
		}

		private ModelAndView createModelAndView(final Section section, final String messageCode) {
			ModelAndView result;
			
			result = new ModelAndView("section/create");
			result.addObject("section", section);
			result.addObject("message", messageCode);
			return result;
		}

}
