package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Settle;

@Repository
public interface SettleRepository extends JpaRepository<Settle, Integer> {

	@Query("select q from Settle q where q.conference.id = ?1")
	Collection<Settle> findAllByConferenceId(int conferenceId);

	@Query("select count(q) from Settle q where q.ticker = ?1")
	Integer findRepeatedTickers(String ticker);
	
	@Query("select q from Settle q join q.conference cq where cq.administrator.id = ?1")
	Collection<Settle> findAllByAdministratorId(int administratorId);
	
	@Query("select avg(1.0*(select count(s) from Settle s where s.conference.id = c.id)) from Conference c")
	Double avgSettlePerConference();
	
	@Query("select stddev(1.0*(select count(s) from Settle s where s.conference.id = c.id)) from Conference c")
	Double stddevSettlePerConference();
	
	@Query("select (count(a) /(select count(b) from Settle b)) * 1.0 from Settle a where a.isDraft = false")
	Double settlesPublishedVersusTotal();
	
	@Query("select (count(a) /(select count(b) from Settle b)) * 1.0 from Settle a where a.isDraft = true")
	Double settlesUnpublishedVersusTotal();
}
