
package controllers.administrator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

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

import services.AdministratorService;
import services.CategoryService;
import services.ConferenceService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Category;
import domain.Conference;

@Controller
@RequestMapping("/conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	// Services

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SubmissionService	submissionService;
	
	@Autowired
	private CategoryService		categoryService;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Conference> conferences;
		final int administratorId;
		Collection<Category> categories;

		categories = this.categoryService.findAll();
		administratorId = this.administratorService.findByPrincipal().getId();

		conferences = this.conferenceService.findByAdministratorId(administratorId);

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("categories", categories);
		result.addObject("requestURI", "conference/administrator/list.do");

		return result;

	}
	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"conferenceStatus"
	})
	public ModelAndView listByStatus(@RequestParam final int conferenceStatus) {
		final ModelAndView result;
		Collection<Conference> conferences;
		Administrator principal;
		Collection<Category> categories;

		categories = this.categoryService.findAll();
		principal = this.administratorService.findByPrincipal();

		//Submission deadline pas� hace 5 dias
		if (conferenceStatus == 1)
			conferences = this.conferenceService.submissionDeadline5daysOverByAdministratorId(principal.getId());
		//Notification deadline va a pasar en 5 dias o menos
		else if (conferenceStatus == 2)
			conferences = this.conferenceService.notificationDeadline5daysOrLessByAdministratorId(principal.getId());
		//Camera deadline va a pasar en 5 dias o menos
		else if (conferenceStatus == 3)
			conferences = this.conferenceService.cameraReadyDeadline5daysOrLessByAdministratorId(principal.getId());
		//Conferencias que van a ser organizadas en menos de 5 dias
		else if (conferenceStatus == 4)
			conferences = this.conferenceService.conferences5daysOrLessByAdministratorId(principal.getId());
		else
			//En cualquier otro caso devolvemos todas
			conferences = this.conferenceService.findByAdministratorId(principal.getId());

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("categories", categories);
		result.addObject("requestURI", "conference/administrator/list.do");

		return result;

	}
	
	//List grouped by categories
	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
			"conferenceCategory"
		})
	public ModelAndView listByStatus(@RequestParam final String conferenceCategory) {
		final ModelAndView result;
		Collection<Conference> conferences;
		Collection<Category> categories;

		categories = this.categoryService.findAll();
		
		conferences = this.conferenceService.searchByCategory(conferenceCategory);
				
		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("categories", categories);
		result.addObject("requestURI", "conference/list.do");

		return result;

	}
	
	
	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int conferenceId) {
		// Inicializa resultado
		ModelAndView result;
		Conference conference;
		boolean submissionDeadlineOver = false;
		final Date date = new Date(System.currentTimeMillis());

		// Busca en el repositorio
		conference = this.conferenceService.findOne(conferenceId);
		Assert.notNull(conference);

		if (conference.getSubmissionDeadline().before(date))
			submissionDeadlineOver = true;

		// Crea y a�ade objetos a la vista
		result = new ModelAndView("conference/display");
		result.addObject("requestURI", "conference/display.do");
		result.addObject("conference", conference);
		result.addObject("submissionDeadlineOver", submissionDeadlineOver);

		// Env�a la vista
		return result;
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Conference conference;

		conference = this.conferenceService.create();
		result = this.createModelAndView(conference);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId) {
		ModelAndView result;
		Conference conference;

		conference = this.conferenceService.findOne(conferenceId);
		Assert.notNull(conference);
		result = this.createEditModelAndView(conference);

		return result;
	}
	// --- CREATION ---

	//Save Draft

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView createDraft(@Valid @ModelAttribute("conference") Conference conference, final BindingResult binding) {
		ModelAndView result;

		try {
			conference = this.conferenceService.reconstruct(conference, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(conference);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				conference = this.conferenceService.save(conference, false);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(conference, "conference.commit.error");
		}
		return result;
	}

	//Save Final

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView createFinal(@Valid @ModelAttribute("conference") Conference conference, final BindingResult binding) {
		ModelAndView result;

		try {
			conference = this.conferenceService.reconstruct(conference, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(conference);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				conference = this.conferenceService.save(conference, true);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(conference, "conference.commit.error");
		}
		return result;
	}

	// --- EDIT ---
	//Save Draft
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(@ModelAttribute("conference") Conference conference, final BindingResult binding) {
		ModelAndView result;

		try {
			conference = this.conferenceService.reconstruct(conference, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(conference);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				conference = this.conferenceService.save(conference, false);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(conference, "conference.commit.error");
		}

		return result;
	}

	//Save Final

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(@ModelAttribute("conference") Conference conference, final BindingResult binding) {
		ModelAndView result;

		try {
			conference = this.conferenceService.reconstruct(conference, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(conference);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				conference = this.conferenceService.save(conference, true);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(conference, "conference.commit.error");
		}

		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int conferenceId) {
		ModelAndView result;
		Conference conference;

		conference = this.conferenceService.findOne(conferenceId);

		try {
			this.conferenceService.delete(conference);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(conference, "conference.commit.error");
		}
		return result;
	}

	//Analiza todas las submissions de una conference y les cambia el estado a Accepted o Rejected

	@RequestMapping(value = "/analyseSubmissions", method = RequestMethod.GET)
	public ModelAndView computeScore(@RequestParam final int conferenceId) {
		final ModelAndView result;
		Conference conference;

		conference = this.conferenceService.findOne(conferenceId);

		this.conferenceService.analyseSubmissions(conference);

		result = new ModelAndView("redirect:/conference/administrator/list.do");

		return result;
	}

	//Decision notification procedure
	@RequestMapping(value = "/decisionNotification", method = RequestMethod.GET)
	public ModelAndView decisionNotificationProcedure(@RequestParam final int conferenceId) {
		final ModelAndView result;

		this.submissionService.decisionNotificationProcedure(conferenceId);

		result = new ModelAndView("redirect:list.do");

		return result;
	}

	// -------------------

	protected ModelAndView createEditModelAndView(final Conference conference) {
		ModelAndView result;

		result = this.createEditModelAndView(conference, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Conference conference, final String messageCode) {
		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findAll();

		result = new ModelAndView("conference/edit");
		result.addObject("conference", conference);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);

		return result;
	}

	private ModelAndView createModelAndView(final Conference conference) {
		ModelAndView result;

		result = this.createModelAndView(conference, null);
		return result;
	}

	private ModelAndView createModelAndView(final Conference conference, final String messageCode) {
		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findAll();

		result = new ModelAndView("conference/create");
		result.addObject("conference", conference);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);
		return result;
	}

}
