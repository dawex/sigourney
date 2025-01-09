package com.dawex.sigourney.trustframework.vc.model.v2411.dataresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.JsonLdValueObject;
import com.dawex.sigourney.trustframework.vc.core.jsonld.XsdDataType;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.CredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.Namespace;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format;

import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.GAIAX_NS;

public class DataResourceCredentialSubject implements CredentialSubject {
	@JsonLdProperty(value = "id", formatName = Format.DATA_RESOURCE_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "name", namespace = Namespace.SCHEMA_NS)
	private final String name;

	@JsonLdProperty(value = "description", namespace = Namespace.SCHEMA_NS)
	private final String description;

	@JsonLdProperty(value = "producedBy", namespace = GAIAX_NS, mandatory = true)
	private final ProducedBy producedBy;

	@JsonLdProperty(value = "exposedThrough", namespace = GAIAX_NS, mandatory = true)
	private final ExposedThrough exposedThrough;

	@JsonLdProperty(value = "containsPII", namespace = GAIAX_NS, mandatory = true)
	private final boolean containsPII;

	@JsonLdProperty(value = "copyrightOwnedBy", namespace = GAIAX_NS, mandatory = true)
	private final Collection<CopyrightOwnedBy> copyrightOwnedBy;

	@JsonLdProperty(value = "resourcePolicy", namespace = GAIAX_NS, mandatory = true)
	private final Collection<String> resourcePolicy;

	@JsonLdProperty(value = "license", namespace = GAIAX_NS, mandatory = true)
	private final Collection<JsonLdValueObject<String>> license;

	public DataResourceCredentialSubject(String id, String name, String description, ProducedBy producedBy, ExposedThrough exposedThrough,
			boolean containsPII, Collection<CopyrightOwnedBy> copyrightOwnedBy, Collection<String> resourcePolicy,
			Collection<JsonLdValueObject<String>> license) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.producedBy = producedBy;
		this.exposedThrough = exposedThrough;
		this.containsPII = containsPII;
		this.copyrightOwnedBy = copyrightOwnedBy;
		this.resourcePolicy = resourcePolicy;
		this.license = license;
	}

	public static DataResourceCredentialSubjectBuilder builder() {
		return new DataResourceCredentialSubjectBuilder();
	}

	@Override
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ProducedBy getProducedBy() {
		return producedBy;
	}

	public ExposedThrough getExposedThrough() {
		return exposedThrough;
	}

	public boolean getContainsPII() {
		return containsPII;
	}

	public Collection<CopyrightOwnedBy> getCopyrightOwnedBy() {
		return copyrightOwnedBy;
	}

	public Collection<String> getResourcePolicy() {
		return resourcePolicy;
	}

	public Collection<JsonLdValueObject<String>> getLicense() {
		return license;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DataResourceCredentialSubject that = (DataResourceCredentialSubject) o;
		return containsPII == that.containsPII && Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
				Objects.equals(description, that.description) && Objects.equals(producedBy, that.producedBy) &&
				Objects.equals(exposedThrough, that.exposedThrough) &&
				Objects.equals(copyrightOwnedBy, that.copyrightOwnedBy) &&
				Objects.equals(resourcePolicy, that.resourcePolicy) && Objects.equals(license, that.license);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, producedBy, exposedThrough, containsPII, copyrightOwnedBy, resourcePolicy, license);
	}

	@Override
	public String toString() {
		return "DataResourceCredentialSubject{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", producedBy=" + producedBy +
				", exposedThrough=" + exposedThrough +
				", containsPII=" + containsPII +
				", copyrightOwnedBy=" + copyrightOwnedBy +
				", resourcePolicy=" + resourcePolicy +
				", license=" + license +
				'}';
	}

	public static class DataResourceCredentialSubjectBuilder {
		private String id;

		private String name;

		private String description;

		private ProducedBy producedBy;

		private ExposedThrough exposedThrough;

		private boolean containsPII;

		private Collection<CopyrightOwnedBy> copyrightOwnedBy;

		private Collection<JsonLdValueObject<String>> license;

		private Collection<String> resourcePolicy;

		DataResourceCredentialSubjectBuilder() {
		}

		public DataResourceCredentialSubjectBuilder id(String id) {
			this.id = id;
			return this;
		}

		public DataResourceCredentialSubjectBuilder name(String name) {
			this.name = name;
			return this;
		}

		public DataResourceCredentialSubjectBuilder description(String description) {
			this.description = description;
			return this;
		}

		public DataResourceCredentialSubjectBuilder resourcePolicy(Collection<String> resourcePolicy) {
			this.resourcePolicy = resourcePolicy;
			return this;
		}

		public DataResourceCredentialSubjectBuilder license(Collection<JsonLdValueObject<String>> license) {
			this.license = license;
			return this;
		}

		public DataResourceCredentialSubjectBuilder licenseAsSPDX(Collection<String> license) {
			if (license == null) {
				this.license = null;
			} else {
				this.license = license.stream().map(l -> new JsonLdValueObject<>(XsdDataType.XSD_STRING, l)).toList();
			}
			return this;
		}

		public DataResourceCredentialSubjectBuilder licenseAsURI(Collection<String> license) {
			if (license == null) {
				this.license = null;
			} else {
				this.license = license.stream().map(l -> new JsonLdValueObject<>(XsdDataType.XSD_URI, l)).toList();
			}
			return this;
		}

		public DataResourceCredentialSubjectBuilder copyrightOwnedBy(Collection<CopyrightOwnedBy> copyrightOwnedBy) {
			this.copyrightOwnedBy = copyrightOwnedBy;
			return this;
		}

		public DataResourceCredentialSubjectBuilder producedBy(ProducedBy producedBy) {
			this.producedBy = producedBy;
			return this;
		}

		public DataResourceCredentialSubjectBuilder containsPII(boolean containsPII) {
			this.containsPII = containsPII;
			return this;
		}

		public DataResourceCredentialSubjectBuilder exposedThrough(ExposedThrough exposedThrough) {
			this.exposedThrough = exposedThrough;
			return this;
		}

		public DataResourceCredentialSubject build() {
			return new DataResourceCredentialSubject(id, name, description, producedBy, exposedThrough, containsPII, copyrightOwnedBy,
					resourcePolicy, license);
		}

		@Override
		public String toString() {
			return "DataResourceCredentialSubjectBuilder{" +
					"id='" + id + '\'' +
					", name='" + name + '\'' +
					", description='" + description + '\'' +
					", producedBy=" + producedBy +
					", exposedThrough=" + exposedThrough +
					", containsPII=" + containsPII +
					", copyrightOwnedBy=" + copyrightOwnedBy +
					", license=" + license +
					", resourcePolicy=" + resourcePolicy +
					'}';
		}
	}
}
