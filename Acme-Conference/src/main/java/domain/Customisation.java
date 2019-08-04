
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Customisation extends DomainEntity {

	private String				systemName;
	private String				welcomeBanner;
	private String				welcomeMessageEn;
	private String				welcomeMessageEs;
	private String				countryCode;
	private Collection<String>	creditCardsMakes;
	private Collection<String>	topics;


	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@URL
	public String getWelcomeBanner() {
		return this.welcomeBanner;
	}

	public void setWelcomeBanner(final String welcomeBanner) {
		this.welcomeBanner = welcomeBanner;
	}

	@NotBlank
	public String getWelcomeMessageEn() {
		return this.welcomeMessageEn;
	}

	public void setWelcomeMessageEn(final String welcomeMessageEn) {
		this.welcomeMessageEn = welcomeMessageEn;
	}

	@NotBlank
	public String getWelcomeMessageEs() {
		return this.welcomeMessageEs;
	}

	public void setWelcomeMessageEs(final String welcomeMessageEs) {
		this.welcomeMessageEs = welcomeMessageEs;
	}

	@Pattern(regexp = "^\\+?\\d{1,3}$")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@ElementCollection
	@EachNotBlank
	public Collection<String> getCreditCardsMakes() {
		return this.creditCardsMakes;
	}

	public void setCreditCardsMakes(final Collection<String> creditCardsMakes) {
		this.creditCardsMakes = creditCardsMakes;
	}

	@ElementCollection
	@EachNotBlank
	public Collection<String> getTopics() {
		return this.topics;
	}

	public void setTopics(final Collection<String> topics) {
		this.topics = topics;
	}
}
