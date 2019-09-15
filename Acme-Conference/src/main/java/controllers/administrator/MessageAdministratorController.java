
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import services.CustomisationService;
import services.MessageService;
import controllers.AbstractController;
import domain.Conference;
import domain.Customisation;
import domain.Message;

@Controller
@RequestMapping("/message/administrator")
public class MessageAdministratorController extends AbstractController {

	// Services

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private CustomisationService	customisationService;


	//Message to: the authors who have made a submission to a conference

	@RequestMapping(value = "/createBySubmission", method = RequestMethod.GET)
	public ModelAndView createBySubmission() {
		final ModelAndView result;
		Message mensaje;

		mensaje = this.messageService.create();

		result = this.modelAndViewSubmissionAndRegistered(mensaje);

		return result;

	}

	@RequestMapping(value = "/createBySubmission", method = RequestMethod.POST, params = "send")
	public ModelAndView createBySubmission(@ModelAttribute("mensaje") @Valid final Message mensaje, final BindingResult binding, final Conference conference) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.modelAndViewSubmissionAndRegistered(mensaje);
		}

		else if (mensaje.getRecipients().isEmpty())
			result = this.modelAndViewSubmissionAndRegistered(mensaje, "message.commit.noActors");

		else if (conference == null)
			result = this.modelAndViewSubmissionAndRegistered(mensaje, "message.commit.noConference");

		else
			try {
				this.messageService.broadcastSubmission(mensaje, conference);
				result = new ModelAndView("redirect:/message/actor/list.do");
			} catch (final Throwable oops) {
				result = this.modelAndViewSubmissionAndRegistered(mensaje, "message.commit.error");
			}

		return result;
	}

	//Message to:  the authors who have registered to a conference

	@RequestMapping(value = "/createByRegistered", method = RequestMethod.GET)
	public ModelAndView createByRegistered() {
		final ModelAndView result;
		Message mensaje;

		mensaje = this.messageService.create();

		result = this.modelAndViewSubmissionAndRegistered(mensaje);

		return result;

	}

	@RequestMapping(value = "/createByRegistered", method = RequestMethod.POST, params = "send")
	public ModelAndView createByRegistered(@ModelAttribute("mensaje") @Valid final Message mensaje, final BindingResult binding, final Conference conference) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.modelAndViewSubmissionAndRegistered(mensaje);

		}

		else if (mensaje.getRecipients().isEmpty())
			result = this.modelAndViewSubmissionAndRegistered(mensaje, "message.commit.noActors");

		else if (conference == null)
			result = this.modelAndViewSubmissionAndRegistered(mensaje, "message.commit.noConference");

		else
			try {
				this.messageService.broadcastRegistered(mensaje, conference);
				result = new ModelAndView("redirect:/message/actor/list.do");
			} catch (final Throwable oops) {
				result = this.modelAndViewSubmissionAndRegistered(mensaje, "message.commit.error");
			}

		return result;
	}

	//Message to: all of the authors in the system

	@RequestMapping(value = "/createBySystem", method = RequestMethod.GET)
	public ModelAndView createBySystem() {
		final ModelAndView result;
		Message mensaje;

		mensaje = this.messageService.create();

		result = this.modelAndViewAuthorsAndAll(mensaje);

		return result;

	}

	@RequestMapping(value = "/createBySystem", method = RequestMethod.POST, params = "send")
	public ModelAndView createBySystem(@ModelAttribute("mensaje") @Valid final Message mensaje, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.modelAndViewAuthorsAndAll(mensaje);
		}

		else if (mensaje.getRecipients().isEmpty())
			result = this.modelAndViewAuthorsAndAll(mensaje, "message.commit.noActors");

		else
			try {
				this.messageService.broadcastAuthors(mensaje);
				result = new ModelAndView("redirect:/message/actor/list.do");
			} catch (final Throwable oops) {
				result = this.modelAndViewAuthorsAndAll(mensaje, "message.commit.error");
			}

		return result;
	}

	//Message to: all of the actors in the system (including administrators)

	@RequestMapping(value = "/createByAll", method = RequestMethod.GET)
	public ModelAndView createByAll() {
		final ModelAndView result;
		Message mensaje;

		mensaje = this.messageService.create();

		result = this.modelAndViewAuthorsAndAll(mensaje);

		return result;

	}

	@RequestMapping(value = "/createByAll", method = RequestMethod.POST, params = "send")
	public ModelAndView createByAll(@ModelAttribute("mensaje") @Valid final Message mensaje, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.modelAndViewAuthorsAndAll(mensaje);
		} else
			try {
				this.messageService.broadcast(mensaje);
				result = new ModelAndView("redirect:/message/actor/list.do");
			} catch (final Throwable oops) {
				result = this.modelAndViewAuthorsAndAll(mensaje, "message.commit.error");
			}

		return result;
	}

	//Ancillary methods

	protected ModelAndView modelAndViewSubmissionAndRegistered(final Message mensaje) {
		ModelAndView result;

		result = this.modelAndViewSubmissionAndRegistered(mensaje, null);

		return result;
	}

	protected ModelAndView modelAndViewSubmissionAndRegistered(final Message mensaje, final String messageCode) {
		final ModelAndView result;
		Collection<Conference> conferences;
		Customisation customisation;
		Collection<String> englishTopics;
		Collection<String> spanishTopics;

		conferences = this.conferenceService.findFinals();
		customisation = this.customisationService.find();
		englishTopics = customisation.getEnglishTopics();
		spanishTopics = customisation.getSpanishTopics();

		result = new ModelAndView("message/create");
		result.addObject("mensaje", mensaje);
		result.addObject("conference", true);
		result.addObject("conferences", conferences);
		result.addObject("englishTopics", englishTopics);
		result.addObject("spanishTopics", spanishTopics);
		result.addObject("permission", true);

		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView modelAndViewAuthorsAndAll(final Message mensaje) {
		ModelAndView result;

		result = this.modelAndViewAuthorsAndAll(mensaje, null);

		return result;
	}

	protected ModelAndView modelAndViewAuthorsAndAll(final Message mensaje, final String messageCode) {
		final ModelAndView result;
		Customisation customisation;
		Collection<String> englishTopics;
		Collection<String> spanishTopics;

		customisation = this.customisationService.find();
		englishTopics = customisation.getEnglishTopics();
		spanishTopics = customisation.getSpanishTopics();
		result = new ModelAndView("message/create");
		result.addObject("mensaje", mensaje);
		result.addObject("broadcast", true);
		result.addObject("englishTopics", englishTopics);
		result.addObject("spanishTopics", spanishTopics);
		result.addObject("permission", true);

		result.addObject("message", messageCode);

		return result;
	}

}
