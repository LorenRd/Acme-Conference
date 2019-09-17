
package controllers.administrator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
import services.KolemService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Conference;
import domain.Kolem;

@Controller
@RequestMapping("/kolem/administrator")
public class KolemAdministratorController extends AbstractController {

	// Services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private KolemService			kolemService;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Kolem> kolems;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		kolems = this.kolemService.findAllByAdministratorId(principal.getId());

		result = new ModelAndView("kolem/list");
		result.addObject("kolems", kolems);
		result.addObject("requestURI", "kolem/administrator/list.do");

		//Dates
		final Calendar cal = Calendar.getInstance();
		//1 month old 
		cal.add(Calendar.MONTH, -1);
		final Date dateOneMonth = cal.getTime();
		//2 months old 
		cal.add(Calendar.MONTH, -1);
		final Date dateTwoMonths = cal.getTime();
		result.addObject("dateOneMonth", dateOneMonth);
		result.addObject("dateTwoMonths", dateTwoMonths);

		return result;

	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int kolemId) {
		// Inicializa resultado
		ModelAndView result;
		Kolem kolem;
		Conference conference;

		kolem = this.kolemService.findOne(kolemId);
		conference = kolem.getConference();

		result = new ModelAndView("kolem/display");
		result.addObject("requestURI", "kolem/display.do");
		result.addObject("conference", conference);
		result.addObject("kolem", kolem);

		return result;
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;
		Kolem kolem;

		kolem = this.kolemService.create(conferenceId);
		result = this.createModelAndView(kolem);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int kolemId) {
		ModelAndView result;
		Kolem kolem;

		kolem = this.kolemService.findOne(kolemId);
		Assert.notNull(kolem);
		result = this.createEditModelAndView(kolem);

		return result;
	}
	// --- CREATION ---

	//Save Draft

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView createDraft(@ModelAttribute("kolem") Kolem kolem, final BindingResult binding) {
		ModelAndView result;

		try {
			kolem = this.kolemService.reconstruct(kolem, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(kolem);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				kolem = this.kolemService.save(kolem, true);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(kolem, "kolem.commit.error");
		}
		return result;
	}

	//Save Final

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView createFinal(@ModelAttribute("kolem") Kolem kolem, final BindingResult binding) {
		ModelAndView result;

		try {
			kolem = this.kolemService.reconstruct(kolem, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(kolem);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				kolem = this.kolemService.save(kolem, false);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(kolem, "kolem.commit.error");
		}
		return result;
	}

	// --- EDIT ---
	//Save Draft
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(@ModelAttribute("kolem") Kolem kolem, final BindingResult binding) {
		ModelAndView result;

		try {
			kolem = this.kolemService.reconstruct(kolem, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(kolem);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				kolem = this.kolemService.save(kolem, true);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(kolem, "kolem.commit.error");
		}

		return result;
	}

	//Save Final

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(@ModelAttribute("kolem") Kolem kolem, final BindingResult binding) {
		ModelAndView result;

		try {
			kolem = this.kolemService.reconstruct(kolem, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(kolem);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				kolem = this.kolemService.save(kolem, false);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(kolem, "kolem.commit.error");
		}

		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int kolemId) {
		ModelAndView result;
		Kolem kolem;

		kolem = this.kolemService.findOne(kolemId);

		try {
			this.kolemService.delete(kolem);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(kolem, "kolem.commit.error");
		}
		return result;
	}

	// -------------------

	protected ModelAndView createEditModelAndView(final Kolem kolem) {
		ModelAndView result;

		result = this.createEditModelAndView(kolem, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Kolem kolem, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("kolem/edit");
		result.addObject("kolem", kolem);
		result.addObject("message", messageCode);

		return result;
	}

	private ModelAndView createModelAndView(final Kolem kolem) {
		ModelAndView result;

		result = this.createModelAndView(kolem, null);
		return result;
	}

	private ModelAndView createModelAndView(final Kolem kolem, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("kolem/create");
		result.addObject("kolem", kolem);
		result.addObject("message", messageCode);
		return result;
	}

}
