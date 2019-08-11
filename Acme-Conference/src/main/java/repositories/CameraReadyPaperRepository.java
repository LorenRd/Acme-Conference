package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.CameraReadyPaper;

@Repository
public interface CameraReadyPaperRepository extends
		JpaRepository<CameraReadyPaper, Integer> {

}
