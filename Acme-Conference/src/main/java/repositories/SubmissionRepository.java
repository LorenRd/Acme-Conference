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
}
