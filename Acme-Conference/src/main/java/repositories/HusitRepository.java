package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Husit;

@Repository
public interface HusitRepository extends JpaRepository<Husit, Integer> {

	@Query("select q from Husit q where q.conference.id = ?1")
	Collection<Husit> findAllByConferenceId(int conferenceId);

	@Query("select count(q) from Husit q where q.ticker = ?1")
	Integer findRepeatedTickers(String ticker);

	@Query("select q from Husit q join q.conference cq where cq.administrator.id = ?1")
	Collection<Husit> findAllByAdministratorId(int conferenceId);

	@Query("select avg(1.0*(select count(h) from Husit h where h.conference.id = c.id AND h.isDraft = 'false')) from Conference c")
	Double avgHusitPerConference();

	@Query("select stddev(1.0*(select count(h) from Husit h where h.conference.id = c.id AND h.isDraft = 'false')) from Conference c")
	Double stddevHusitPerConference();

	@Query("select (count(a) /(select count(b) from Husit b)) * 1.0 from Husit a where a.isDraft = false")
	Double ratioPublishedHusits();

	@Query("select (count(a) /(select count(b) from Husit b)) * 1.0 from Husit a where a.isDraft = true")
	Double ratioUnpublishedHusits();

}
