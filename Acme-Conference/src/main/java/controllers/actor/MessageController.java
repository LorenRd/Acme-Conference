
package controllers.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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

import services.ActorService;
import services.CustomisationService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Customisation;
import domain.Message;

@Controller
@RequestMapping("/message/actor")
public class MessageController extends AbstractController {

	// Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private CustomisationService	customisationService;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String topic) {
		ModelAndView result;
		final Collection<Message> messages;
		final Collection<Message> messagesBySender;
		final Collection<Message> messagesByRecipient;
		Customisation customisation;
		Collection<String> topics;

		try {
			final int actorId = this.actorService.findByPrincipal().getId();
			Assert.notNull(actorId);

			messages = this.messageService.findById(actorId);

			messagesBySender = this.messageService.findBySenderId(actorId);
			messagesByRecipient = this.messageService.findByRecipientId(actorId);

			customisation = this.customisationService.find();
			topics = customisation.getTopics();

			result = new ModelAndView("message/list");
			result.addObject("messages", messages);
			result.addObject("messagesBySender", messagesBySender);
			result.addObject("messagesByRecipient", messagesByRecipient);
			result.addObject("topics", topics);
			result.addObject("requestURI", "message/actor/list.do");

		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("message/list");
			result.addObject("message", "message.retrieve.error");
			result.addObject("messages", new ArrayList<Message>());
		}

		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message message;

		message = this.messageService.create();
		result = this.createModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "send")
	public ModelAndView createFinal(@ModelAttribute("mensaje") Message message, final BindingResult binding) {
		ModelAndView result;

		try {
			if (binding.hasErrors()) {
				result = this.createModelAndView(message);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				message = this.messageService.save(message);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(message, "message.commit.error");
		}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int messageId) {
		ModelAndView result;
		Message message;

		message = this.messageService.findOne(messageId);

		try {
			this.messageService.delete(message);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createModelAndView(message, "message.commit.error");
		}
		return result;
	}

	private ModelAndView createModelAndView(final Message mensaje) {
		ModelAndView result;

		result = this.createModelAndView(mensaje, null);
		return result;
	}

	private ModelAndView createModelAndView(final Message mensaje, final String messageCode) {
		ModelAndView result;
		Customisation customisation;
		Collection<String> topics;
		Collection<Actor> recipients;

		customisation = this.customisationService.find();
		topics = customisation.getTopics();

		recipients = this.actorService.findAllMinusPrincipal();

		result = new ModelAndView("message/create");
		result.addObject("mensaje", mensaje);
		result.addObject("recipients", recipients);
		result.addObject("topics", topics);
		result.addObject("message", messageCode);
		return result;
	}
}
