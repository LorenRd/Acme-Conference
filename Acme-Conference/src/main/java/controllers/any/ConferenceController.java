package controllers.any;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceCommentService;
import services.ConferenceService;
import controllers.AbstractController;
import domain.Conference;
import domain.ConferenceComment;

@Controller
@RequestMapping("/conference")
public class ConferenceController extends AbstractController {

	// Services

	@Autowired
	private ConferenceService conferenceService;

	@Autowired
	private ConferenceCommentService conferenceCommentService;

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(required = false) final String keyword,
			@RequestParam(required = false, defaultValue = "false") final Boolean keywordBool) {
		ModelAndView result;
		Collection<Conference> conferences;

		if (keywordBool && keyword != null)
			conferences = this.conferenceService.findFinalByKeyword(keyword);
		else
			conferences = this.conferenceService.findFinals();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/list.do");

		return result;
	}

	// List of forthcoming conferences

	@RequestMapping(value = "/listForthcoming", method = RequestMethod.GET)
	public ModelAndView listForthcoming(
			@RequestParam(required = false) final String keyword,
			@RequestParam(required = false, defaultValue = "false") final Boolean keywordBool) {
		ModelAndView result;
		Collection<Conference> conferences;

		if (keywordBool && keyword != null)
			conferences = this.conferenceService
					.findFinalForthcomingByKeyword(keyword);
		else
			conferences = this.conferenceService.findFinalForthcoming();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/list.do");

		return result;
	}

	// List of past conferences

	@RequestMapping(value = "/listPast", method = RequestMethod.GET)
	public ModelAndView listPast(
			@RequestParam(required = false) final String keyword,
			@RequestParam(required = false, defaultValue = "false") final Boolean keywordBool) {
		ModelAndView result;
		Collection<Conference> conferences;

		if (keywordBool && keyword != null)
			conferences = this.conferenceService
					.findFinalPastByKeyword(keyword);
		else
			conferences = this.conferenceService.findFinalPast();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/list.do");

		return result;
	}

	// List of running conferences

	@RequestMapping(value = "/listRunning", method = RequestMethod.GET)
	public ModelAndView listRunning(
			@RequestParam(required = false) final String keyword,
			@RequestParam(required = false, defaultValue = "false") final Boolean keywordBool) {
		ModelAndView result;
		Collection<Conference> conferences;

		if (keywordBool && keyword != null)
			conferences = this.conferenceService
					.findFinalRunningByKeyword(keyword);
		else
			conferences = this.conferenceService.findFinalRunning();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/list.do");

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int conferenceId) {
		// Inicializa resultado
		ModelAndView result;
		Conference conference;
		final Collection<ConferenceComment> conferenceComments;

		// Busca en el repositorio
		conference = this.conferenceService.findOne(conferenceId);
		Assert.notNull(conference);

		conferenceComments = this.conferenceCommentService
				.findAllByConference(conferenceId);

		// Crea y añade objetos a la vista
		result = new ModelAndView("conference/display");
		result.addObject("requestURI", "conference/display.do");
		result.addObject("conferenceComments", conferenceComments);
		result.addObject("conference", conference);

		// Envía la vista
		return result;
	}
}
