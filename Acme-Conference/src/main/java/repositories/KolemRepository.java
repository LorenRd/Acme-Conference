
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Kolem;

@Repository
public interface KolemRepository extends JpaRepository<Kolem, Integer> {

	@Query("select q from Kolem q where q.conference.id = ?1 and q.publicationMoment != null")
	Collection<Kolem> findAllByConferenceId(int conferenceId);

	@Query("select count(q) from Kolem q where q.ticker = ?1")
	Integer findRepeatedTickers(String ticker);

	@Query("select q from Kolem q join q.conference cq where cq.administrator.id = ?1")
	Collection<Kolem> findAllByAdministratorId(int conferenceId);

	@Query("select avg(1.0*(select count(a) from Kolem a where a.conference.id = p.id)) from Conference p")
	Double avgKolemScoreConference();

	@Query("select stddev(1.0*(select count(a) from Kolem a where a.conference.id = p.id)) from Conference p")
	Double stddevKolemScoreConference();

	@Query("select 1.0*count(a) / (select count(n) from Kolem n) from Kolem a where a.publicationMoment != null")
	Double ratioPublishedKolems();
}
