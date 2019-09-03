package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ActivityComment;

@Repository
public interface ActivityCommentRepository extends
		JpaRepository<ActivityComment, Integer> {

	@Query("select a from ActivityComment a where a.activity.id = ?1")
	Collection<ActivityComment> findAllByActivity(int activityId);

}
