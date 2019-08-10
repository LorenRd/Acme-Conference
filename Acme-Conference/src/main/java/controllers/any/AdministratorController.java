
package controllers.any;

import java.util.Arrays;

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
import services.CustomisationService;
import controllers.AbstractController;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomisationService	customisationService;


	// Display ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) final Integer administratorId) {
		final ModelAndView result;
		Administrator administrator = new Administrator();

		if (administratorId == null)
			administrator = this.administratorService.findByPrincipal();
		else
			administrator = this.administratorService.findOne(administratorId);

		result = new ModelAndView("administrator/display");
		result.addObject("administrator", administrator);

		return result;

	}

	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.create();
		result = this.createEditModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@ModelAttribute("administrator") @Valid Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		try {
			if (binding.hasErrors()) {
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
				result = this.createEditModelAndView(administrator);
			} else {
				administrator = this.administratorService.save(administrator);
				result = new ModelAndView("welcome/index");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(administrator, "administrator.commit.error");
		}

		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);
		result = this.editModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("administrator") Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		try {
			if (binding.hasErrors()) {
				result = this.editModelAndView(administrator);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else
				administrator = this.administratorService.save(administrator);
			result = new ModelAndView("welcome/index");
		} catch (final Throwable oops) {
			result = this.editModelAndView(administrator, "administrator.commit.error");

		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	private ModelAndView editModelAndView(final Administrator administrator) {
		ModelAndView result;
		result = this.editModelAndView(administrator, null);
		return result;
	}

	private ModelAndView editModelAndView(final Administrator administrator, final String messageCode) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);
		result.addObject("countryCode", countryCode);
		result.addObject("message", messageCode);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView result;
		result = this.createEditModelAndView(administrator, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();
		if (administrator.getId() != 0)
			result = new ModelAndView("administrator/edit");
		else
			result = new ModelAndView("administrator/register");

		result.addObject("administrator", administrator);
		result.addObject("actionURI", "administrator/edit.do");
		result.addObject("redirectURI", "welcome/index.do");
		result.addObject("countryCode", countryCode);

		result.addObject("message", message);

		return result;
	}

}
