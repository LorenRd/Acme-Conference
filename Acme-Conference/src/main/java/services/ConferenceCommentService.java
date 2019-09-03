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

import repositories.ConferenceCommentRepository;
import domain.ConferenceComment;

@Service
@Transactional
public class ConferenceCommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ConferenceCommentRepository conferenceCommentRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private Validator validator;

	public Collection<ConferenceComment> findAllByConference(int conferenceId) {

		Collection<ConferenceComment> result;

		result = this.conferenceCommentRepository
				.findAllByConference(conferenceId);
		return result;
	}

	public ConferenceComment create() {
		ConferenceComment result;

		result = new ConferenceComment();

		return result;
	}

	public ConferenceComment save(final ConferenceComment conferenceComment) {
		ConferenceComment result;

		Assert.notNull(conferenceComment);

		result = this.conferenceCommentRepository.save(conferenceComment);
		Assert.notNull(result);

		return result;
	}

	public ConferenceComment reconstruct(
			final ConferenceComment conferenceComment,
			final BindingResult binding) {
		ConferenceComment result = conferenceComment;

		result.setTitle(conferenceComment.getTitle());
		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setAuthor(conferenceComment.getAuthor());
		result.setText(conferenceComment.getText());
		result.setConference(conferenceComment.getConference());

		this.validator.validate(result, binding);
		if (binding.hasErrors()) {
			throw new ValidationException();
		}

		return result;
	}

}
