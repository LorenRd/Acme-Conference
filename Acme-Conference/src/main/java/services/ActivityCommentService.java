package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActivityCommentRepository;
import domain.ActivityComment;

@Service
@Transactional
public class ActivityCommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ActivityCommentRepository activityCommentRepository;

	public Collection<ActivityComment> findAllByActivity(int activityId) {

		Collection<ActivityComment> result;

		result = this.activityCommentRepository.findAllByActivity(activityId);
		return result;
	}

}
