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

	@Query("select avg(1.0*(select count(ac) from ActivityComment ac where ac.activity.id = a.id)) from Activity a")
	Double avgCommentsPerActivity();

	@Query("select min(1.0*(select count(ac) from ActivityComment ac where ac.activity.id = a.id)) from Activity a")
	Double minCommentsPerActivity();
	
	@Query("select max(1.0*(select count(ac) from ActivityComment ac where ac.activity.id = a.id)) from Activity a")
	Double maxCommentsPerActivity();
	
	@Query("select stddev(1.0*(select count(ac) from ActivityComment ac where ac.activity.id = a.id)) from Activity a")
	Double stddevCommentsPerActivity();

}
