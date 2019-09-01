package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Tutorial extends Activity {
	// Relationships----------------------------------------------

	private Collection<Section> sections;
	
	@NotNull
	@Valid
	@OneToMany
	public Collection<Section> getSections() {
		return this.sections;
	}

	public void setSections(final Collection<Section> sections) {
		this.sections = sections;
	}
	
}
