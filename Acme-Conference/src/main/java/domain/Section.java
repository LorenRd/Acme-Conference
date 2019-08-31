package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import cz.jirutka.validator.collection.constraints.EachURL;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends Activity {
	private String	title;
	private String	summary;
	private Collection<String>	pictures;

	
	
	
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}
	
	@ElementCollection(fetch = FetchType.EAGER)
	@EachURL
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}
	
	// Relationships----------------------------------------------

	private Tutorial tutorial;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Tutorial getTutorial() {
		return this.tutorial;
	}

	public void setTutorial(final Tutorial tutorial) {
		this.tutorial = tutorial;
	}
	
}
