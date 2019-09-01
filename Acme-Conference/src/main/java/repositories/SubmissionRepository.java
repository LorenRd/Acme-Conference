package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Submission;

@Repository
public interface SubmissionRepository extends
		JpaRepository<Submission, Integer> {

	@Query("select s from Submission s where s.author.id = ?1")
	Collection<Submission> findAllByAuthorId(int authorId);

	@Query("select count(s) from Submission s where s.author.id = ?1 AND s.ticker = ?2")
	Integer findRepeatedTickers(int authorId, String ticker);

	@Query("select s from Submission s join s.conference c where c.administrator.id = ?1")
	Collection<Submission> findAllByAdministrator(int administratorId);
	
	@Query("select s from Submission s where status like 'UNDER-REVIEW'")
	Collection<Submission> findSubmissionUnderReview();
	
	@Query("select s from Submission s where s.conference.id = ?1")
	Collection<Submission> findAllByConferenceId(int conferenceId);
	
	@Query("select avg(1.0*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
	Double avgSubmissionPerConference();

	@Query("select min(1.0*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
	Double minSubmissionPerConference();

	@Query("select max(1.0*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
	Double maxSubmissionPerConference();

	@Query("select stddev(1.0*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
	Double stddevSubmissionPerConference();
	
	
}
