package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import domain.Activity;

@Service
@Transactional
public class ActivityService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ActivityRepository activityRepository;

	public Activity findOne(final int activityid) {
		Activity result;

		result = this.activityRepository.findOne(activityid);
		Assert.notNull(result);
		return result;
	}

}
