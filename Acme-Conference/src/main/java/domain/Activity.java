
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Activity extends DomainEntity implements Cloneable {

	private String				activityTitle;
	private Collection<String>	speakers;
	private Date				schedule;
	private Date				startMoment;
	private Integer				duration;
	private String				room;
	private String				activitySummary;
	private Collection<String>	attachments;


	@NotBlank
	public String getActivityTitle() {
		return this.activityTitle;
	}

	public void setActivityTitle(final String activityTitle) {
		this.activityTitle = activityTitle;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@EachNotBlank
	@NotEmpty
	public Collection<String> getSpeakers() {
		return this.speakers;
	}

	public void setSpeakers(final Collection<String> speakers) {
		this.speakers = speakers;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSchedule() {
		return this.schedule;
	}

	public void setSchedule(final Date schedule) {
		this.schedule = schedule;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(final Integer duration) {
		this.duration = duration;
	}

	@NotBlank
	public String getRoom() {
		return this.room;
	}

	public void setRoom(final String room) {
		this.room = room;
	}

	@NotBlank
	public String getActivitySummary() {
		return this.activitySummary;
	}

	public void setActivitySummary(final String activitySummary) {
		this.activitySummary = activitySummary;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@EachNotBlank
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}


	// Relationships----------------------------------------------

	private Conference	conference;


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
