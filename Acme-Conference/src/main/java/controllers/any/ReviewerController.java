
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
import services.ReviewerService;
import controllers.AbstractController;
import domain.Reviewer;

@Controller
@RequestMapping("/reviewer")
public class ReviewerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ReviewerService			reviewerService;

	@Autowired
	private CustomisationService	customisationService;


	// Display ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) final Integer reviewerId) {
		final ModelAndView result;
		Reviewer reviewer = new Reviewer();

		if (reviewerId == null)
			reviewer = this.reviewerService.findByPrincipal();
		else
			reviewer = this.reviewerService.findOne(reviewerId);

		result = new ModelAndView("reviewer/display");
		result.addObject("reviewer", reviewer);

		return result;

	}

	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Reviewer reviewer;

		reviewer = this.reviewerService.create();
		result = this.createEditModelAndView(reviewer);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@ModelAttribute("reviewer") @Valid Reviewer reviewer, final BindingResult binding) {
		ModelAndView result;

		try {
			if (binding.hasErrors()) {
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
				result = this.createEditModelAndView(reviewer);
			} else {
				reviewer = this.reviewerService.save(reviewer);
				result = new ModelAndView("welcome/index");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(reviewer, "reviewer.commit.error");
		}

		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Reviewer reviewer;

		reviewer = this.reviewerService.findByPrincipal();
		Assert.notNull(reviewer);
		result = this.editModelAndView(reviewer);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("reviewer") Reviewer reviewer, final BindingResult binding) {
		ModelAndView result;

		try {
			if (binding.hasErrors()) {
				result = this.editModelAndView(reviewer);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else
				reviewer = this.reviewerService.save(reviewer);
			result = new ModelAndView("welcome/index");
		} catch (final Throwable oops) {
			result = this.editModelAndView(reviewer, "reviewer.commit.error");

		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	private ModelAndView editModelAndView(final Reviewer reviewer) {
		ModelAndView result;
		result = this.editModelAndView(reviewer, null);
		return result;
	}

	private ModelAndView editModelAndView(final Reviewer reviewer, final String messageCode) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("reviewer/edit");
		result.addObject("reviewer", reviewer);
		result.addObject("countryCode", countryCode);
		result.addObject("message", messageCode);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Reviewer reviewer) {
		ModelAndView result;
		result = this.createEditModelAndView(reviewer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Reviewer reviewer, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();
		if (reviewer.getId() != 0)
			result = new ModelAndView("reviewer/edit");
		else
			result = new ModelAndView("reviewer/register");

		result.addObject("reviewer", reviewer);
		result.addObject("actionURI", "rookie/edit.do");
		result.addObject("redirectURI", "welcome/index.do");
		result.addObject("countryCode", countryCode);

		result.addObject("message", message);

		return result;
	}

}
