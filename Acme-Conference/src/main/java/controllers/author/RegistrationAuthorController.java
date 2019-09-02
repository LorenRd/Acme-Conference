
package controllers.author;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.ValidationException;

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

import services.AuthorService;
import services.ConferenceService;
import services.CustomisationService;
import services.RegistrationService;
import controllers.AbstractController;
import domain.Author;
import domain.Registration;
import forms.RegistrationForm;

@Controller
@RequestMapping("/registration/author")
public class RegistrationAuthorController extends AbstractController {

	@Autowired
	private RegistrationService		registrationService;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private CustomisationService	customisationService;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Registration> registrations;
		Author principal;

		principal = this.authorService.findByPrincipal();

		registrations = this.registrationService.findAllByAuthor(principal.getId());

		result = new ModelAndView("registration/list");
		result.addObject("registrations", registrations);
		result.addObject("requestURI", "registration/author/list.do");

		return result;

	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int registrationId) {
		final ModelAndView result;
		Registration registration;

		registration = this.registrationService.findOne(registrationId);

		result = new ModelAndView("registration/display");
		result.addObject("registration", registration);
		result.addObject("requestURI", "registration/display.do");
		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Registration registration;
		RegistrationForm registrationForm;

		registration = this.registrationService.create();
		registrationForm = this.registrationService.construct(registration);
		result = this.createEditModelAndView(registrationForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid @ModelAttribute("registrationForm") final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Registration registration = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(registrationForm);
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
		} else
			try {
				registration = this.registrationService.reconstruct(registrationForm, binding);
				this.registrationService.save(registration);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final ValidationException oops) {
				//				registrationForm = this.registrationService.construct(registration);
				result = this.createEditModelAndView(registrationForm);
				oops.printStackTrace();
			} catch (final Throwable oops) {
				//				registrationForm = this.registrationService.construct(registration);
				result = this.createEditModelAndView(registrationForm, "registration.commit.error");
				oops.printStackTrace();
			}
		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int registrationId) {
		ModelAndView result;
		Registration registration;
		RegistrationForm registrationForm;

		registration = this.registrationService.findOne(registrationId);
		registrationForm = this.registrationService.construct(registration);
		Assert.notNull(registration);
		result = this.createEditModelAndView(registrationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid @ModelAttribute("registrationForm") final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		Registration registration;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(registrationForm);
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
		} else
			try {
				registration = this.registrationService.reconstruct(registrationForm, binding);
				this.registrationService.save(registration);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final ValidationException oops) {
				result = this.createEditModelAndView(registrationForm);
				oops.printStackTrace();
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(registrationForm, "registration.commit.error");
				oops.printStackTrace();
			}
		return result;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int registrationId) {
		ModelAndView result;
		Registration registration;

		registration = this.registrationService.findOne(registrationId);

		try {
			this.registrationService.delete(registration);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.deleteModelAndView(registration, "registration.commit.error");
		}
		return result;
	}

	// -------------------

	protected ModelAndView createEditModelAndView(final RegistrationForm registrationForm) {
		ModelAndView result;
		result = this.createEditModelAndView(registrationForm, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final RegistrationForm registrationForm, final String messageCode) {
		ModelAndView result;

		if (registrationForm.getId() != 0)
			result = new ModelAndView("registration/edit");
		else
			result = new ModelAndView("registration/create");

		result.addObject("registrationForm", registrationForm);
		result.addObject("conferences", this.conferenceService.findAvailableConferencesForRegistration());
		result.addObject("creditCardMakes", this.customisationService.getCreditCardMakes());
		result.addObject("message", messageCode);
		return result;
	}

	protected ModelAndView deleteModelAndView(final Registration registration) {
		ModelAndView result;

		result = this.deleteModelAndView(registration, null);

		return result;
	}

	protected ModelAndView deleteModelAndView(final Registration registration, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("registration/edit");
		result.addObject("registration", registration);
		result.addObject("message", messageCode);

		return result;
	}

}
