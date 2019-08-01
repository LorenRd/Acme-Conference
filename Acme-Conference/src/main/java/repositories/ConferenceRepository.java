
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select c from Conference c where c.startDate > NOW()")
	Collection<Conference> findForthcoming();

	@Query("select c from Conference c where c.endDate < NOW()")
	Collection<Conference> findPast();

	@Query("select c from Conference c where c.startDate < NOW() and c.endDate > NOW()")
	Collection<Conference> findRunning();

	@Query("select distinct c from Conference c where (c.title like %:keyword% or c.summary like %:keyword% or c.venue like %:keyword%)")
	Collection<Conference> findByKeyword(@Param("keyword") String keyword);
}
