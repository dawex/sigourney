package com.dawex.sigourney.trustframework.vc.model.v2411.dataset;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2411.Namespace;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.GaiaxCredentialSubject;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.XsdDataType.XSD_DATE;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.XsdDataType.XSD_DATETIME;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.GAIAX_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.DATA_SET_CREDENTIAL_SUBJECT;
import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.DATA_SET_EXPOSED_THROUGH;

public class DataSetCredentialSubject extends GaiaxCredentialSubject {

	@JsonLdProperty(value = "id", formatName = DATA_SET_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "identifier", namespace = Namespace.DC_TERMS_NS, mandatory = true)
	private final String identifier;

	@JsonLdProperty(value = "title", namespace = Namespace.DC_TERMS_NS, mandatory = true)
	private final String title;

	@JsonLdProperty(value = "distribution", namespace = Namespace.DC_TERMS_NS, mandatory = true)
	private final Collection<Distribution> distributions;

	@JsonLdProperty(value = "issued", namespace = Namespace.DC_TERMS_NS, type = XSD_DATE)
	private final LocalDate issued;

	@JsonLdProperty(value = "expirationDateTime", namespace = Namespace.GAIAX_NS, type = XSD_DATETIME)
	private final ZonedDateTime expirationDateTime;

	@JsonLdProperty(value = "exposedThrough", namespace = GAIAX_NS, formatName = DATA_SET_EXPOSED_THROUGH, mandatory = true)
	private final String exposedThrough;

	public DataSetCredentialSubject(String name, String description, String id, String identifier, String title,
			Collection<Distribution> distributions, LocalDate issued, ZonedDateTime expirationDateTime, String exposedThrough) {
		super(name, description);
		this.id = id;
		this.identifier = identifier;
		this.title = title;
		this.distributions = distributions;
		this.issued = issued;
		this.expirationDateTime = expirationDateTime;
		this.exposedThrough = exposedThrough;
	}

	public static DataSetCredentialSubjectBuilder builder() {
		return new DataSetCredentialSubjectBuilder();
	}

	@Override
	public String getId() {
		return id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getTitle() {
		return title;
	}

	public Collection<Distribution> getDistributions() {
		return distributions;
	}

	public LocalDate getIssued() {
		return issued;
	}

	public ZonedDateTime getExpirationDateTime() {
		return expirationDateTime;
	}

	public String getExposedThrough() {
		return exposedThrough;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DataSetCredentialSubject that = (DataSetCredentialSubject) o;
		return Objects.equals(id, that.id) && Objects.equals(identifier, that.identifier) &&
				Objects.equals(title, that.title) && Objects.equals(distributions, that.distributions) &&
				Objects.equals(issued, that.issued) && Objects.equals(expirationDateTime, that.expirationDateTime) &&
				Objects.equals(exposedThrough, that.exposedThrough);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, identifier, title, distributions, issued, expirationDateTime, exposedThrough);
	}

	@Override
	public String toString() {
		return "DataSetCredentialSubject{" +
				"id='" + id + '\'' +
				", identifier='" + identifier + '\'' +
				", title='" + title + '\'' +
				", distributions=" + distributions +
				", issued=" + issued +
				", expirationDateTime=" + expirationDateTime +
				", exposedThrough=" + exposedThrough +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}

	public static class DataSetCredentialSubjectBuilder {
		private String id;

		private String name;

		private String description;

		private String identifier;

		private String title;

		private Collection<Distribution> distributions;

		private LocalDate issued;

		private ZonedDateTime expirationDateTime;

		private String exposedThrough;

		DataSetCredentialSubjectBuilder() {
		}

		public DataSetCredentialSubjectBuilder id(String id) {
			this.id = id;
			return this;
		}

		public DataSetCredentialSubjectBuilder name(String name) {
			this.name = name;
			return this;
		}

		public DataSetCredentialSubjectBuilder description(String description) {
			this.description = description;
			return this;
		}

		public DataSetCredentialSubjectBuilder identifier(String identifier) {
			this.identifier = identifier;
			return this;
		}

		public DataSetCredentialSubjectBuilder title(String title) {
			this.title = title;
			return this;
		}

		public DataSetCredentialSubjectBuilder distributions(Collection<Distribution> distributions) {
			this.distributions = distributions;
			return this;
		}

		public DataSetCredentialSubjectBuilder issued(LocalDate issued) {
			this.issued = issued;
			return this;
		}

		public DataSetCredentialSubjectBuilder expirationDateTime(ZonedDateTime expirationDateTime) {
			this.expirationDateTime = expirationDateTime;
			return this;
		}

		public DataSetCredentialSubjectBuilder exposedThrough(String exposedThrough) {
			this.exposedThrough = exposedThrough;
			return this;
		}

		public DataSetCredentialSubject build() {
			return new DataSetCredentialSubject(name, description, id, identifier, title, distributions, issued, expirationDateTime,
					exposedThrough);
		}

		@Override
		public String toString() {
			return "DataSetCredentialSubjectBuilder{" +
					"id='" + id + '\'' +
					", name='" + name + '\'' +
					", description='" + description + '\'' +
					", identifier='" + identifier + '\'' +
					", title='" + title + '\'' +
					", distributions=" + distributions +
					", issued=" + issued +
					", expirationDateTime=" + expirationDateTime +
					", exposedThrough=" + exposedThrough +
					'}';
		}
	}
}
