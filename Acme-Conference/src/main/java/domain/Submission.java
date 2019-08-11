package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Submission extends DomainEntity {

	private String ticker;
	private Date moment;
	private String status;

	@Column(unique = true)
	@Pattern(regexp = "^([A-Z]{3})-([A-Z, 0-9]{4})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Pattern(regexp = "^UNDER-REVIEW|REJECTED|ACCEPTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	// Relationships----------------------------------------------

	private Conference conference;
	private Author author;
	public Paper paper;
	public CameraReadyPaper cameraReadypaper;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Conference getConference() {
		return this.conference;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Author getAuthor() {
		return this.author;
	}

	public void setAuthor(final Author author) {
		this.author = author;
	}

	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(final Paper paper) {
		this.paper = paper;
	}

	@Valid
	@OneToOne(optional = true)
	public CameraReadyPaper getCameraReadyPaper() {
		return this.cameraReadypaper;
	}

	public void setCameraReadyPaper(final CameraReadyPaper cameraReadypaper) {
		this.cameraReadypaper = cameraReadypaper;
	}

}
