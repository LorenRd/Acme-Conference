
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select c from Conference c where c.startDate > NOW() and c.isFinal = true")
	Collection<Conference> findFinalForthcoming();

	@Query("select c from Conference c where c.endDate < NOW() and c.isFinal = true")
	Collection<Conference> findFinalPast();

	@Query("select c from Conference c where c.startDate < NOW() and c.endDate > NOW() and c.isFinal = true")
	Collection<Conference> findFinalRunning();

	@Query("select distinct c from Conference c where (c.title like %:keyword% or c.summary like %:keyword% or c.venue like %:keyword%) and c.isFinal = true")
	Collection<Conference> findFinalByKeyword(@Param("keyword") String keyword);

	@Query("select distinct c from Conference c where (c.title like %:keyword% or c.summary like %:keyword% or c.venue like %:keyword%) and c.isFinal = true and c.startDate > NOW()")
	Collection<Conference> findFinalForthcomingByKeyword(@Param("keyword") String keyword);

	@Query("select distinct c from Conference c where (c.title like %:keyword% or c.summary like %:keyword% or c.venue like %:keyword%) and c.isFinal = true and c.startDate < NOW()")
	Collection<Conference> findFinalPastByKeyword(@Param("keyword") String keyword);

	@Query("select distinct c from Conference c where (c.title like %:keyword% or c.summary like %:keyword% or c.venue like %:keyword%) and c.isFinal = true and c.startDate < NOW() and c.endDate > NOW()")
	Collection<Conference> findFinalRunningByKeyword(@Param("keyword") String keyword);

	@Query("select c from Conference c where c.isFinal = true")
	Collection<Conference> findFinals();
}
