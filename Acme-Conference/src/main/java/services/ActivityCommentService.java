package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ActivityCommentRepository;
import domain.ActivityComment;

@Service
@Transactional
public class ActivityCommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ActivityCommentRepository activityCommentRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActivityService activityService;

	@Autowired
	private Validator validator;

	public Collection<ActivityComment> findAllByActivity(int activityId) {

		Collection<ActivityComment> result;

		result = this.activityCommentRepository.findAllByActivity(activityId);
		return result;
	}

	public ActivityComment create() {
		ActivityComment result;

		result = new ActivityComment();

		return result;
	}

	public ActivityComment save(final ActivityComment activityComment) {
		ActivityComment result;

		Assert.notNull(activityComment);

		result = this.activityCommentRepository.save(activityComment);
		Assert.notNull(result);

		return result;
	}

	public ActivityComment reconstruct(final ActivityComment activityComment,
			final int activityId, final BindingResult binding) {
		ActivityComment result = activityComment;

		result.setTitle(activityComment.getTitle());
		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setAuthor(activityComment.getAuthor());
		result.setText(activityComment.getText());
		result.setActivity(this.activityService.findOne(activityId));

		this.validator.validate(result, binding);
		if (binding.hasErrors()) {
			throw new ValidationException();
		}

		return result;
	}

}
