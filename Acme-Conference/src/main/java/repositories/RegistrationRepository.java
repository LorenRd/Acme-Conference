
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

	@Query("select avg(1.0*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Double avgRegistrationPerConference();

	@Query("select min(1.0*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Double minRegistrationPerConference();

	@Query("select max(1.0*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Double maxRegistrationPerConference();

	@Query("select stddev(1.0*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Double stddevRegistrationPerConference();

	@Query("select r from Registration r where r.conference.id = ?1")
	Collection<Registration> findAllByConferenceId(int conferenceId);
	
	@Query("select r from Registration r where r.author.id = ?1")
	Collection<Registration> findAllByAuthorId(int authorId);

}
