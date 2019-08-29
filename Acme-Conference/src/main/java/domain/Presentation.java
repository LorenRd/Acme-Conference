package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Presentation extends Activity {

	private String cameraReadyVersion;

	@NotBlank
	public String getCameraReadyVersion() {
		return this.cameraReadyVersion;
	}

	public void setCameraReadyVersion(final String cameraReadyVersion) {
		this.cameraReadyVersion = cameraReadyVersion;
	}

}
