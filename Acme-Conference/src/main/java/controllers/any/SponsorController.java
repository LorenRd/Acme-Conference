
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

import services.CustomisationService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private CustomisationService	customisationService;


	// Display ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) final Integer sponsorId) {
		final ModelAndView result;
		Sponsor sponsor = new Sponsor();

		if (sponsorId == null)
			sponsor = this.sponsorService.findByPrincipal();
		else
			sponsor = this.sponsorService.findOne(sponsorId);

		result = new ModelAndView("sponsor/display");
		result.addObject("sponsor", sponsor);

		return result;

	}

	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Sponsor sponsor;

		sponsor = this.sponsorService.create();
		result = this.createEditModelAndView(sponsor);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@ModelAttribute("sponsor") @Valid Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;

		try {
			if (binding.hasErrors()) {
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
				result = this.createEditModelAndView(sponsor);
			} else {
				sponsor = this.sponsorService.save(sponsor);
				result = new ModelAndView("welcome/index");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsor, "sponsor.commit.error");
		}

		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Sponsor sponsor;

		sponsor = this.sponsorService.findByPrincipal();
		Assert.notNull(sponsor);
		result = this.editModelAndView(sponsor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.editModelAndView(sponsor);
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
		} else
			try {
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/sponsor/display.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(sponsor, "sponsor.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	private ModelAndView editModelAndView(final Sponsor sponsor) {
		ModelAndView result;
		result = this.editModelAndView(sponsor, null);
		return result;
	}

	private ModelAndView editModelAndView(final Sponsor sponsor, final String messageCode) {
		ModelAndView result;
		String countryCode;
		Sponsor principal;
		boolean permission = false;

		principal = this.sponsorService.findByPrincipal();
		if (sponsor.getId() == principal.getId())
			permission = true;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", sponsor);
		result.addObject("permission", permission);
		result.addObject("countryCode", countryCode);
		result.addObject("message", messageCode);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor) {
		ModelAndView result;
		result = this.createEditModelAndView(sponsor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();
		if (sponsor.getId() != 0)
			result = new ModelAndView("sponsor/edit");
		else
			result = new ModelAndView("sponsor/register");

		result.addObject("sponsor", sponsor);
		result.addObject("actionURI", "rookie/edit.do");
		result.addObject("redirectURI", "welcome/index.do");
		result.addObject("countryCode", countryCode);

		result.addObject("message", message);

		return result;
	}

}
