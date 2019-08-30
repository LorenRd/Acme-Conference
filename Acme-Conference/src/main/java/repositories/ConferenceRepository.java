
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

	@Query("select c from Conference c where c.administrator.id = ?1")
	Collection<Conference> findByAdministratorId(int administratorId);

	@Query("select c from Conference c where c.administrator.id = ?1 AND c.submissionDeadline < NOW() AND c.submissionDeadline > ?2")
	Collection<Conference> submissionDeadline5daysOverByAdministratorId(int administratorId, String dateMax);

	@Query("select c from Conference c where c.administrator.id = ?1 AND c.notificationDeadline > ?2")
	Collection<Conference> notificationDeadline5daysOrLessByAdministratorId(int administratorId, String dateMax);

	@Query("select c from Conference c where c.administrator.id = ?1 AND c.cameraReadyDeadline > ?2")
	Collection<Conference> cameraReadyDeadline5daysOrLessByAdministratorId(int administratorId, String dateMax);

	@Query("select c from Conference c where c.administrator.id = ?1 AND c.startDate > ?2")
	Collection<Conference> conferences5daysOrLessByAdministratorId(int administratorId, String dateMax);

	//	@Query("select f from Conference f where (f.startDate BETWEEN ?1 AND ?2) AND (f.fee <= ?3) AND (f.title LIKE %:keyword% OR f.acronym LIKE %:keyword% OR f.venue LIKE %:keyword% OR f.summary LIKE %:keyword% OR f.category.id LIKE ?4) and f.isFinal = true")
	//	Collection<Conference> searchConference(Date dateMin, Date dateMax, Double maxFee, @Param("keyword") String keyword, int categoryId);
	//
	//	@Query("select f from Conference f where (f.startDate BETWEEN ?1 AND ?2) and f.isFinal = true")
	//	Collection<Conference> searchByDates(Date dateMin, Date dateMax);
	//
	//	@Query("select f from Conference f where (f.fee <= ?1) and f.isFinal = true")
	//	Collection<Conference> searchByMaxFee(Double maxFee);
}
