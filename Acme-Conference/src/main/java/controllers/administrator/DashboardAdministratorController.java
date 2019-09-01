package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import services.RegistrationService;
import services.SubmissionService;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController {

	// Services
	@Autowired
	private SubmissionService submissionService;
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private ConferenceService conferenceService;
	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		
		
		final Double avgSubmissionPerConference, minSubmissionPerConference, maxSubmissionPerConference, stddevSubmissionPerConference;
		final Double avgRegistrationPerConference, minRegistrationPerConference, maxRegistrationPerConference, stddevRegistrationPerConference;
		final Double avgConferenceFees, minConferenceFees, maxConferenceFees, stddevConferenceFees;
		final Double avgDaysPerConference, stddevDaysPerConference;
		final Long minDaysPerConference, maxDaysPerConference;

		// Stadistics
		// SubmissionPerConference
		avgSubmissionPerConference = this.submissionService.avgSubmissionPerConference();
		minSubmissionPerConference = this.submissionService.minSubmissionPerConference();
		maxSubmissionPerConference = this.submissionService.maxSubmissionPerConference();
		stddevSubmissionPerConference = this.submissionService.stddevSubmissionPerConference();
		
		// RegistrationPerConference
		avgRegistrationPerConference = this.registrationService.avgRegistrationPerConference();
		minRegistrationPerConference = this.registrationService.minRegistrationPerConference();
		maxRegistrationPerConference = this.registrationService.maxRegistrationPerConference();
		stddevRegistrationPerConference = this.registrationService.stddevRegistrationPerConference();
		
		// ConferenceFees
		avgConferenceFees = this.conferenceService.avgConferenceFees();
		minConferenceFees = this.conferenceService.minConferenceFees();
		maxConferenceFees = this.conferenceService.maxConferenceFees();
		stddevConferenceFees = this.conferenceService.stddevConferenceFees();

		// DaysPerConference
		avgDaysPerConference = this.conferenceService.avgDaysPerConference();
		minDaysPerConference = this.conferenceService.minDaysPerConference();
		maxDaysPerConference = this.conferenceService.maxDaysPerConference();
		stddevDaysPerConference = this.conferenceService.stddevDaysPerConference();

		
		

		//----------------------------------------------------------------------------------
		result = new ModelAndView("administrator/dashboard");
		result.addObject("avgSubmissionPerConference", avgSubmissionPerConference);
		result.addObject("minSubmissionPerConference", minSubmissionPerConference);
		result.addObject("maxSubmissionPerConference", maxSubmissionPerConference);
		result.addObject("stddevSubmissionPerConference", stddevSubmissionPerConference);
		
		result.addObject("avgRegistrationPerConference", avgRegistrationPerConference);
		result.addObject("minRegistrationPerConference", minRegistrationPerConference);
		result.addObject("maxRegistrationPerConference", maxRegistrationPerConference);
		result.addObject("stddevRegistrationPerConference", stddevRegistrationPerConference);
		
		result.addObject("avgConferenceFees", avgConferenceFees);
		result.addObject("minConferenceFees", minConferenceFees);
		result.addObject("maxConferenceFees", maxConferenceFees);
		result.addObject("stddevConferenceFees", stddevConferenceFees);
		
		result.addObject("avgDaysPerConference", avgDaysPerConference);
		result.addObject("minDaysPerConference", minDaysPerConference);
		result.addObject("maxDaysPerConference", maxDaysPerConference);
		result.addObject("stddevDaysPerConference", stddevDaysPerConference);
		
		return result;

	}

}
