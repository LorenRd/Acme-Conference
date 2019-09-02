package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ConferenceCommentRepository;
import domain.ConferenceComment;

@Service
@Transactional
public class ConferenceCommentService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ConferenceCommentRepository conferenceCommentRepository;

	public Collection<ConferenceComment> findAllByConference(int conferenceId) {

		Collection<ConferenceComment> result;

		result = this.conferenceCommentRepository
				.findAllByConference(conferenceId);
		return result;
	}

}
