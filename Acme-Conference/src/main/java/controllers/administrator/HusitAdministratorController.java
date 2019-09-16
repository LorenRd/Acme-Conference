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
import services.HusitService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Conference;
import domain.Husit;

@Controller
@RequestMapping("/husit/administrator")
public class HusitAdministratorController extends AbstractController {

	// Services

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private HusitService husitService;

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Husit> husits;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		husits = this.husitService.findAllByAdministratorId(principal.getId());

		result = new ModelAndView("husit/list");
		result.addObject("husits", husits);
		result.addObject("requestURI", "husit/administrator/list.do");

		// Dates
		final Calendar cal = Calendar.getInstance();
		// 1 month old
		cal.add(Calendar.MONTH, -1);
		final Date dateOneMonth = cal.getTime();
		// 2 months old
		cal.add(Calendar.MONTH, -1);
		final Date dateTwoMonths = cal.getTime();
		result.addObject("dateOneMonth", dateOneMonth);
		result.addObject("dateTwoMonths", dateTwoMonths);

		return result;

	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int husitId) {
		// Inicializa resultado
		ModelAndView result;
		Husit husit;
		Conference conference;

		husit = this.husitService.findOne(husitId);
		conference = husit.getConference();

		result = new ModelAndView("husit/display");
		result.addObject("requestURI", "husit/display.do");
		result.addObject("conference", conference);
		result.addObject("husit", husit);

		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;
		Husit husit;

		husit = this.husitService.create(conferenceId);
		result = this.createModelAndView(husit);

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int husitId) {
		ModelAndView result;
		Husit husit;

		husit = this.husitService.findOne(husitId);
		Assert.notNull(husit);
		result = this.createEditModelAndView(husit);

		return result;
	}

	// --- CREATION ---

	// Save Draft

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView createDraft(@ModelAttribute("husit") Husit husit,
			final BindingResult binding) {
		ModelAndView result;

		try {
			husit = this.husitService.reconstruct(husit, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(husit);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				husit = this.husitService.save(husit, true);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(husit, "husit.commit.error");
		}
		return result;
	}

	// Save Final

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView createFinal(@ModelAttribute("husit") Husit husit,
			final BindingResult binding) {
		ModelAndView result;

		try {
			husit = this.husitService.reconstruct(husit, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(husit);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				husit = this.husitService.save(husit, false);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(husit, "husit.commit.error");
		}
		return result;
	}

	// --- EDIT ---
	// Save Draft
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(@ModelAttribute("husit") Husit husit,
			final BindingResult binding) {
		ModelAndView result;

		try {
			husit = this.husitService.reconstruct(husit, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(husit);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				husit = this.husitService.save(husit, true);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(husit, "husit.commit.error");
		}

		return result;
	}

	// Save Final

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(@ModelAttribute("husit") Husit husit,
			final BindingResult binding) {
		ModelAndView result;

		try {
			husit = this.husitService.reconstruct(husit, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(husit);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				husit = this.husitService.save(husit, false);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(husit, "husit.commit.error");
		}

		return result;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int husitId) {
		ModelAndView result;
		Husit husit;

		husit = this.husitService.findOne(husitId);

		try {
			this.husitService.delete(husit);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(husit, "husit.commit.error");
		}
		return result;
	}

	// -------------------

	protected ModelAndView createEditModelAndView(final Husit husit) {
		ModelAndView result;

		result = this.createEditModelAndView(husit, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Husit husit,
			final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("husit/edit");
		result.addObject("husit", husit);
		result.addObject("message", messageCode);

		return result;
	}

	private ModelAndView createModelAndView(final Husit husit) {
		ModelAndView result;

		result = this.createModelAndView(husit, null);
		return result;
	}

	private ModelAndView createModelAndView(final Husit husit,
			final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("husit/create");
		result.addObject("husit", husit);
		result.addObject("message", messageCode);
		return result;
	}

}
