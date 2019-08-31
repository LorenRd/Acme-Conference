package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.AdministratorService;
import services.SubmissionService;

@Controller
@RequestMapping("/activity/administrator")
public class ActivityAdministratorController {

	

	// Services

	@Autowired
	private Activity	submissionService;

	@Autowired
	private AdministratorService	administratorService;

	// Listing

}
