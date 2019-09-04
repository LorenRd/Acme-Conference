
package controllers.administrator;

import java.util.Arrays;
import java.util.Calendar;
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
import services.QuoletService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Conference;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/administrator")
public class QuoletAdministratorController extends AbstractController {

	// Services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private QuoletService	quoletService;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Quolet> quolets;
		Administrator principal;
		
		principal = this.administratorService.findByPrincipal();
		quolets = this.quoletService.findAllByAdministratorId(principal.getId());
		
		result = new ModelAndView("quolet/list");
		result.addObject("quolets", quolets);
		result.addObject("requestURI", "quolet/administrator/list.do");
	
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
	public ModelAndView display(@RequestParam final int quoletId) {
		// Inicializa resultado
		ModelAndView result;
		Quolet quolet;
		Conference conference;

		quolet = this.quoletService.findOne(quoletId);
		conference = quolet.getConference();

		result = new ModelAndView("quolet/display");
		result.addObject("requestURI", "quolet/display.do");
		result.addObject("conference", conference);
		result.addObject("quolet", quolet);

		return result;
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;
		Quolet quolet;

		quolet = this.quoletService.create(conferenceId);
		result = this.createModelAndView(quolet);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int quoletId) {
		ModelAndView result;
		Quolet quolet;

		quolet = this.quoletService.findOne(quoletId);
		Assert.notNull(quolet);
		result = this.createEditModelAndView(quolet);

		return result;
	}
	// --- CREATION ---

	//Save Draft

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView createDraft(@Valid @ModelAttribute("quolet") Quolet quolet, final BindingResult binding) {
		ModelAndView result;

		try {
			quolet = this.quoletService.reconstruct(quolet, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(quolet);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				quolet = this.quoletService.save(quolet,true);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(quolet, "quolet.commit.error");
		}
		return result;
	}

	//Save Final

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView createFinal(@Valid @ModelAttribute("quolet") Quolet quolet, final BindingResult binding) {
		ModelAndView result;

		try {
			quolet = this.quoletService.reconstruct(quolet, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(quolet);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				quolet = this.quoletService.save(quolet, false);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(quolet, "quolet.commit.error");
		}
		return result;
	}

	// --- EDIT ---
	//Save Draft
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(@ModelAttribute("quolet") Quolet quolet, final BindingResult binding) {
		ModelAndView result;

		try {
			quolet = this.quoletService.reconstruct(quolet, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(quolet);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				quolet = this.quoletService.save(quolet, true);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(quolet, "quolet.commit.error");
		}

		return result;
	}

	//Save Final

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(@ModelAttribute("quolet") Quolet quolet, final BindingResult binding) {
		ModelAndView result;

		try {
			quolet = this.quoletService.reconstruct(quolet, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(quolet);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				quolet = this.quoletService.save(quolet, false);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(quolet, "quolet.commit.error");
		}

		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int quoletId) {
		ModelAndView result;
		Quolet quolet;

		quolet = this.quoletService.findOne(quoletId);

		try {
			this.quoletService.delete(quolet);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(quolet, "quolet.commit.error");
		}
		return result;
	}



	// -------------------

	protected ModelAndView createEditModelAndView(final Quolet quolet) {
		ModelAndView result;

		result = this.createEditModelAndView(quolet, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Quolet quolet, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("quolet/edit");
		result.addObject("quolet", quolet);
		result.addObject("message", messageCode);

		return result;
	}

	private ModelAndView createModelAndView(final Quolet quolet) {
		ModelAndView result;

		result = this.createModelAndView(quolet, null);
		return result;
	}

	private ModelAndView createModelAndView(final Quolet quolet, final String messageCode) {
		ModelAndView result;


		result = new ModelAndView("quolet/create");
		result.addObject("quolet", quolet);
		result.addObject("message", messageCode);
		return result;
	}

}
