package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {

	@Query("select p from Paper p where p.submission.id = ?1")
	Paper findBySubmissionId(int submissionId);

}
