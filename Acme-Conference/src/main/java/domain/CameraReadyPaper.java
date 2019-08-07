package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class CameraReadyPaper extends DomainEntity {

	private String title;
	private String author;
	private String summary;
	private String document;

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@NotBlank
	public String getDocument() {
		return this.document;
	}

	public void setDocument(final String document) {
		this.document = document;
	}

}
