
package repositories;

import java.util.Collection;
import java.util.Date;

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
	Collection<Conference> submissionDeadline5daysOverByAdministratorId(int administratorId, Date dateMax);

	@Query("select c from Conference c where c.administrator.id = ?1 AND c.notificationDeadline > ?2")
	Collection<Conference> notificationDeadline5daysOrLessByAdministratorId(int administratorId, Date dateMax);

	@Query("select c from Conference c where c.administrator.id = ?1 AND c.cameraReadyDeadline > ?2")
	Collection<Conference> cameraReadyDeadline5daysOrLessByAdministratorId(int administratorId, Date dateMax);

	@Query("select c from Conference c where c.administrator.id = ?1 AND c.startDate > ?2")
	Collection<Conference> conferences5daysOrLessByAdministratorId(int administratorId, Date dateMax);

	@Query("select avg(c.fee) from Conference c")
	Double avgConferenceFees();

	@Query("select min(c.fee) from Conference c")
	Double minConferenceFees();

	@Query("select max(c.fee) from Conference c")
	Double maxConferenceFees();

	@Query("select stddev(c.fee) from Conference c")
	Double stddevConferenceFees();

	@Query("select c from Conference c where c.startDate > NOW() and c.isFinal = true")
	Collection<Conference> findAvailableConferencesForRegistration();

	@Query("select f from Conference f where (f.fee <= ?1) and f.isFinal = true")
	Collection<Conference> searchByMaxFee(Double maxFee);

	@Query("select f from Conference f where (f.fee <= ?1)")
	Collection<Conference> searchByMaxFeeAdminId(Double maxFee);

	@Query("select distinct c from Conference c where (c.title like %:keyword% or c.acronym like %:keyword% or c.summary like %:keyword% or c.venue like %:keyword%) and c.isFinal = true")
	Collection<Conference> findByKeyword(@Param("keyword") String keyword);

	@Query("select distinct c from Conference c where (c.title like %:keyword% or c.acronym like %:keyword% or c.summary like %:keyword% or c.venue like %:keyword%)")
	Collection<Conference> findByKeywordAdminId(@Param("keyword") String keyword);

	@Query("select f from Conference f where (f.category.englishName like %:keyword% or f.category.spanishName like %:keyword%) and f.isFinal = true")
	Collection<Conference> searchByCategory(@Param("keyword") String category);

	@Query("select f from Conference f where (f.category.englishName like %:keyword% or f.category.spanishName like %:keyword%)")
	Collection<Conference> searchByCategoryAdminId(@Param("keyword") String category);

	@Query("select f from Conference f where (f.startDate BETWEEN ?1 AND ?2) and f.isFinal = true")
	Collection<Conference> searchByDateRange(Date dateMin, Date dateMax);

	@Query("select f from Conference f where (f.startDate BETWEEN ?1 AND ?2)")
	Collection<Conference> searchByDateRangeAdminId(Date dateMin, Date dateMax);

	@Query("select avg(1.0*(select count (co) from Conference co where co.category.id = c.id)) from Category c")
	Double avgConferencePerCategory();

	@Query("select min(1.0*(select count (co) from Conference co where co.category.id = c.id)) from Category c")
	Double minConferencePerCategory();

	@Query("select max(1.0*(select count (co) from Conference co where co.category.id = c.id)) from Category c")
	Double maxConferencePerCategory();

	@Query("select stddev(1.0*(select count (co) from Conference co where co.category.id = c.id)) from Category c")
	Double stddevConferencePerCategory();

	@Query("select c from Conference c where c.submissionDeadline > NOW() and c.isFinal = true")
	Collection<Conference> findConferencesBeforeSubmissionDeadline();
}
