package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CameraReadyPaper;

@Repository
public interface CameraReadyPaperRepository extends
		JpaRepository<CameraReadyPaper, Integer> {

	@Query("select c from CameraReadyPaper c where c.submission.id = ?1")
	Collection<CameraReadyPaper> findBySubmissionId(int submissionId);

}
