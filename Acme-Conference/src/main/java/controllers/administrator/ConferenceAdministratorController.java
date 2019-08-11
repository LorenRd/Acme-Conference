package controllers.administrator;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConferenceService;

import controllers.AbstractController;
import domain.Administrator;
import domain.Conference;
@Controller
@RequestMapping("/conference/administrator")
public class ConferenceAdministratorController extends AbstractController {
	// Services

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AdministratorService administratorService;

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Conference> conferences;
		final int administratorId;
		
		administratorId = this.administratorService.findByPrincipal().getId();
		
		conferences = this.conferenceService.findByAdministratorId(administratorId);
				
		result = new ModelAndView("application/list");
		result.addObject("conferences", conferences);
		result.addObject("requestURI", "conference/administrator/list.do");

		return result;

	}
	@RequestMapping(value = "/list", method = RequestMethod.GET, params = {
		"conferenceStatus"
	})
	public ModelAndView listByStatus(@RequestParam final int conferenceStatus) {
		final ModelAndView result;
		Map<String, List<Conference>> groupedConference;
		Collection<Conference> conferences;
		Administrator principal;
		principal = this.administratorService.findByPrincipal();

		//Submission deadline pasó hace 5 dias
		if (conferenceStatus == 0)
			conferences = this.conferenceService.findAllByCompanyId(principal.getId());
		//Notification deadline va a pasar en 5 dias o menos
		else if (conferenceStatus == 1)
			conferences = ;
		//Notification deadline va a pasar en 5 dias o menos
		else if (conferenceStatus == 2)
			conferences = ;
		//Camera deadline va a pasar en 5 dias o menos
		else if (conferenceStatus == 3)
			conferences =;
		//Conferencias que van a ser organizadas en menos de 5 dias
		else if (conferenceStatus == 4)
			conferences = ;
		else //En cualquier otro caso devolvemos todas
			conferences = this.conferenceService.findByAdministratorId(principal.getId());

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("conferenceURI", "conference/administrator/list.do");

		return result;

	}

}
