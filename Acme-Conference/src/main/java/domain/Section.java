package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import org.hibernate.validator.constraints.NotBlank;

import cz.jirutka.validator.collection.constraints.EachURL;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends DomainEntity {
	private String	sectionTitle;
	private String	sectionSummary;
	private Collection<String>	pictures;

	
	@NotBlank
	public String getSectionTitle() {
		return this.sectionTitle;
	}

	public void setSectionTitle(final String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	@NotBlank
	public String getSectionSummary() {
		return this.sectionSummary;
	}

	public void setSectionSummary(final String sectionSummary) {
		this.sectionSummary = sectionSummary;
	}
	
	@ElementCollection(fetch = FetchType.EAGER)
	@EachURL
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}
	
	
}
