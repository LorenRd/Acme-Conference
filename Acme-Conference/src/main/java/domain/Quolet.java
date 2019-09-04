package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Quolet extends DomainEntity {
	
	private String	ticker;
	private Date	publicationMoment;
	private String 	title;
	private String 	body;
	private String 	picture;
	private boolean	isDraft;


	@Column(unique = true)
	@Pattern(regexp = "^[0-9]{8}-([A-Z]{4})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublicationMoment() {
		return this.publicationMoment;
	}

	public void setPublicationMoment(final Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}
	
	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	
	@NotBlank
	@Size(min = 5, max = 100)
	public String getBody() {
		return this.body;
	}
	public void setBody(final String body) {
		this.body = body;
	}
	
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}
	
	public boolean getIsDraft() {
		return this.isDraft;
	}

	public void setIsDraft(final boolean isDraft) {
		this.isDraft = isDraft;
	}

	
	// Relationships--------------------------

	private Conference conference;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Conference getConference() {
		return this.conference;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}
	
}
