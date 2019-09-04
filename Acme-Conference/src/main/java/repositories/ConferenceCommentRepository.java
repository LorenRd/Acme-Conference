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

	@Query("select avg(1.0*(select count(cc) from ConferenceComment cc where cc.conference.id = c.id)) from Conference c")
	Double avgCommentsPerConference();

	@Query("select max(1.0*(select count(cc) from ConferenceComment cc where cc.conference.id = c.id)) from Conference c")
	Double maxCommentsPerConference();

	@Query("select min(1.0*(select count(cc) from ConferenceComment cc where cc.conference.id = c.id)) from Conference c")
	Double minCommentsPerConference();

	@Query("select stddev(1.0*(select count(cc) from ConferenceComment cc where cc.conference.id = c.id)) from Conference c")
	Double stddevCommentsPerConference();
}
