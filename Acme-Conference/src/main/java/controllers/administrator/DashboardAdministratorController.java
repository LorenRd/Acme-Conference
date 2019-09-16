package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityCommentService;
import services.ConferenceCommentService;
import services.ConferenceService;
import services.RegistrationService;
import services.SettleService;
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
	
	@Autowired
	private ActivityCommentService activityCommentService;

	@Autowired
	private ConferenceCommentService conferenceCommentService;

	@Autowired
	private SettleService settleService;
	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		
		
		final Double avgSubmissionPerConference, minSubmissionPerConference, maxSubmissionPerConference, stddevSubmissionPerConference;
		final Double avgRegistrationPerConference, minRegistrationPerConference, maxRegistrationPerConference, stddevRegistrationPerConference;
		final Double avgConferenceFees, minConferenceFees, maxConferenceFees, stddevConferenceFees;
		final Double avgDaysPerConference, stddevDaysPerConference;
		final Long minDaysPerConference, maxDaysPerConference;
		final Double avgCommentPerConference, minCommentPerConference, maxCommentPerConference, stddevCommentPerConference;
		final Double avgCommentPerActivity, minCommentPerActivity, maxCommentPerActivity, stddevCommentPerActivity;
		final Double avgConferencePerCategory, minConferencePerCategory, maxConferencePerCategory, stddevConferencePerCategory;
		
		final Double avgSettlesPerConference, stddevSettlesPerConference, settlesPublishedVersusTotal, settlesUnpublishedVersusTotal;
		
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

		
		//ConferencePerCategory
		avgConferencePerCategory = this.conferenceService.avgConferencePerCategory();
		minConferencePerCategory = this.conferenceService.minConferencePerCategory();
		maxConferencePerCategory = this.conferenceService.maxConferencePerCategory();
		stddevConferencePerCategory = this.conferenceService.stddevConferencePerCategory();

		
		//CommentPerConference
		avgCommentPerConference = this.conferenceCommentService.avgCommentsPerConference();
		minCommentPerConference = this.conferenceCommentService.minCommentsPerConference();
		maxCommentPerConference = this.conferenceCommentService.maxCommentsPerConference();
		stddevCommentPerConference = this.conferenceCommentService.stddevCommentsPerConference();

		//CommentPerActivity
		avgCommentPerActivity = this.activityCommentService.avgCommentsPerActivity();
		minCommentPerActivity = this.activityCommentService.minCommentsPerActivity();
		maxCommentPerActivity = this.activityCommentService.maxCommentsPerActivity();
		stddevCommentPerActivity = this.activityCommentService.stddevCommentsPerActivity();
		
		//Settles
		avgSettlesPerConference = this.settleService.avgSettlePerConference();
		stddevSettlesPerConference = this.settleService.stddevSettlePerConference();
		settlesPublishedVersusTotal = this.settleService.settlesPublishedVersusTotal();
		settlesUnpublishedVersusTotal = this.settleService.settlesUnpublishedVersusTotal();
		

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
		
		result.addObject("avgConferencePerCategory", avgConferencePerCategory);
		result.addObject("minConferencePerCategory", minConferencePerCategory);
		result.addObject("maxConferencePerCategory", maxConferencePerCategory);
		result.addObject("stddevConferencePerCategory", stddevConferencePerCategory);
		
		result.addObject("avgCommentPerConference", avgCommentPerConference);
		result.addObject("minCommentPerConference", minCommentPerConference);
		result.addObject("maxCommentPerConference", maxCommentPerConference);
		result.addObject("stddevCommentPerConference", stddevCommentPerConference);
		
		result.addObject("avgCommentPerActivity", avgCommentPerActivity);
		result.addObject("minCommentPerActivity", minCommentPerActivity);
		result.addObject("maxCommentPerActivity", maxCommentPerActivity);
		result.addObject("stddevCommentPerActivity", stddevCommentPerActivity);
		
		result.addObject("avgSettlesPerConference", avgSettlesPerConference);
		result.addObject("stddevSettlesPerConference", stddevSettlesPerConference);
		result.addObject("settlesPublishedVersusTotal", settlesPublishedVersusTotal);
		result.addObject("settlesUnpublishedVersusTotal", settlesUnpublishedVersusTotal);
		
		return result;

	}

}
