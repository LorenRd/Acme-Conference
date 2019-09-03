package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ConferenceComment;

@Repository
public interface ConferenceCommentRepository extends
		JpaRepository<ConferenceComment, Integer> {

	@Query("select c from ConferenceComment c where c.conference.id = ?1")
	Collection<ConferenceComment> findAllByConference(int conferenceId);

}
