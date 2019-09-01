package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Registration;

@Repository
public interface RegistrationRepository extends	JpaRepository<Registration, Integer> {

	@Query("select avg(1.0*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Double avgRegistrationPerConference();

	@Query("select min(1.0*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Double minRegistrationPerConference();

	@Query("select max(1.0*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Double maxRegistrationPerConference();

	@Query("select sttdev(1.0*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Double stddevRegistrationPerConference();
	
	
}
