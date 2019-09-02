package controllers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import services.ConferenceCommentService;
import controllers.AbstractController;

@Controller
@RequestMapping("/conferenceComment")
public class ConferenceCommentController extends AbstractController {

	// Services

	@Autowired
	private ConferenceCommentService conferenceCommentService;

}
