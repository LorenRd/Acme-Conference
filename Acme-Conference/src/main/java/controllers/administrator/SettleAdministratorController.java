
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
import services.SettleService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Conference;
import domain.Settle;

@Controller
@RequestMapping("/settle/administrator")
public class SettleAdministratorController extends AbstractController {

	// Services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SettleService	settleService;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Settle> settles;
		Administrator principal;
		
		principal = this.administratorService.findByPrincipal();
		settles = this.settleService.findAllByAdministratorId(principal.getId());
		
		result = new ModelAndView("settle/list");
		result.addObject("settles", settles);
		result.addObject("requestURI", "settle/administrator/list.do");
	
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
	public ModelAndView display(@RequestParam final int settleId) {
		// Inicializa resultado
		ModelAndView result;
		Settle settle;
		Conference conference;

		settle = this.settleService.findOne(settleId);
		conference = settle.getConference();

		result = new ModelAndView("settle/display");
		result.addObject("requestURI", "settle/display.do");
		result.addObject("conference", conference);
		result.addObject("settle", settle);

		return result;
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;
		Settle settle;

		settle = this.settleService.create(conferenceId);
		result = this.createModelAndView(settle);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int settleId) {
		ModelAndView result;
		Settle settle;

		settle = this.settleService.findOne(settleId);
		Assert.notNull(settle);
		result = this.createEditModelAndView(settle);

		return result;
	}
	// --- CREATION ---

	//Save Draft

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView createDraft(@Valid @ModelAttribute("settle") Settle settle, final BindingResult binding) {
		ModelAndView result;

		try {
			settle = this.settleService.reconstruct(settle, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(settle);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				settle = this.settleService.save(settle,true);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(settle, "settle.commit.error");
		}
		return result;
	}

	//Save Final

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView createFinal(@Valid @ModelAttribute("settle") Settle settle, final BindingResult binding) {
		ModelAndView result;

		try {
			settle = this.settleService.reconstruct(settle, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(settle);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				settle = this.settleService.save(settle, false);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(settle, "settle.commit.error");
		}
		return result;
	}

	// --- EDIT ---
	//Save Draft
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(@ModelAttribute("settle") Settle settle, final BindingResult binding) {
		ModelAndView result;

		try {
			settle = this.settleService.reconstruct(settle, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(settle);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				settle = this.settleService.save(settle, true);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(settle, "settle.commit.error");
		}

		return result;
	}

	//Save Final

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(@ModelAttribute("settle") Settle settle, final BindingResult binding) {
		ModelAndView result;

		try {
			settle = this.settleService.reconstruct(settle, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(settle);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				settle = this.settleService.save(settle, false);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(settle, "settle.commit.error");
		}

		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int settleId) {
		ModelAndView result;
		Settle settle;

		settle = this.settleService.findOne(settleId);

		try {
			this.settleService.delete(settle);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(settle, "settle.commit.error");
		}
		return result;
	}



	// -------------------

	protected ModelAndView createEditModelAndView(final Settle settle) {
		ModelAndView result;

		result = this.createEditModelAndView(settle, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Settle settle, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("settle/edit");
		result.addObject("settle", settle);
		result.addObject("message", messageCode);

		return result;
	}

	private ModelAndView createModelAndView(final Settle settle) {
		ModelAndView result;

		result = this.createModelAndView(settle, null);
		return result;
	}

	private ModelAndView createModelAndView(final Settle settle, final String messageCode) {
		ModelAndView result;


		result = new ModelAndView("settle/create");
		result.addObject("settle", settle);
		result.addObject("message", messageCode);
		return result;
	}

}
