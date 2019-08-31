
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

	@Query("select a from Author a where a.userAccount.id = ?1")
	Author findByUserAccountId(int userAccountId);
	
	@Query("select s.author from Submission s where s.id = ?1")
	Collection<Author> findAllBySubmission(int submissionId);
}
