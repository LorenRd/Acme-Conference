
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.Pattern;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Reviewer extends Actor {

	private String				email;
	private Collection<String>	expertises;


	@Pattern(regexp = "^[a-zA-Z0-9 ]*[<]?\\w+[@][a-zA-Z0-9.]+[>]?$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@EachNotBlank
	public Collection<String> getExpertises() {
		return this.expertises;
	}

	public void setExpertises(final Collection<String> expertises) {
		this.expertises = expertises;
	}
}
