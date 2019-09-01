
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
import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	private double	originalityScore;
	private double	qualityScore;
	private double	readabilityScore;
	private String	decision;
	private Collection<String>	comments;
	

	public double getOriginalityScore() {
		return this.originalityScore;
	}

	public void setOriginalityScore(final double originalityScore) {
		this.originalityScore = originalityScore;
	}
	
	public double getQualityScore() {
		return this.qualityScore;
	}

	public void setQualityScore(final double qualityScore) {
		this.qualityScore = qualityScore;
	}
	
	public double getReadabilityScore() {
		return this.readabilityScore;
	}

	public void setReadabilityScore(final double readabilityScore) {
		this.readabilityScore = readabilityScore;
	}
	

	@NotBlank
	public String getDecision() {
		return this.decision;
	}

	public void setDecision(final String decision) {
		this.decision = decision;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@EachNotBlank
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}

	// Relationships----------------------------------------------

	private Reviewer	reviewer;
	private Submission	submission;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Reviewer getReviewer() {
		return this.reviewer;
	}

	public void setReviewer(final Reviewer reviewer) {
		this.reviewer = reviewer;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Submission getSubmission() {
		return this.submission;
	}

	public void setSubmission(final Submission submission) {
		this.submission = submission;
	}

}
