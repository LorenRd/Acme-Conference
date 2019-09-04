package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

	@Query("select q from Quolet q where q.conference.id = ?1")
	Collection<Quolet> findAllByConferenceId(int conferenceId);

	@Query("select count(q) from Quolet q where q.ticker = ?1")
	Integer findRepeatedTickers(String ticker);
	
	@Query("select q from Quolet q join q.conference cq where cq.administrator.id = ?1")
	Collection<Quolet> findAllByAdministratorId(int conferenceId);

}
