package com.dawex.sigourney.trustframework.vc.model.v2210.dataresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2210.Namespace;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format;

import java.util.Collection;
import java.util.Objects;

@JsonLdType("gx:DataResource")
public class DataResourceCredentialSubject {
	@JsonLdProperty(value = "id", formatName = Format.DATA_RESOURCE_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "name", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String name;

	@JsonLdProperty(value = "description", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String description;

	@JsonLdProperty(value = "policy", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final Collection<String> policy;

	@JsonLdProperty(value = "license", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final Collection<String> licenses;

	@JsonLdProperty(value = "copyrightOwnedBy", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, formatName = Format.DATA_RESOURCE_COPYRIGHT_OWNED_BY, mandatory = true)
	private final Collection<String> copyrightOwnedBy;

	@JsonLdProperty(value = "producedBy", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final ProducedBy producedBy;

	@JsonLdProperty(value = "containsPII", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final boolean containsPII;

	@JsonLdProperty(value = "exposedThrough", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final ExposedThrough exposedThrough;

	@JsonLdProperty(value = "distribution", namespace = Namespace.DAWEX_NS)
	private final Distribution distribution;

	public DataResourceCredentialSubject(String id, String name, String description, Collection<String> policy, Collection<String> licenses,
			Collection<String> copyrightOwnedBy, ProducedBy producedBy, boolean containsPII, ExposedThrough exposedThrough,
			Distribution distribution) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.policy = policy;
		this.licenses = licenses;
		this.copyrightOwnedBy = copyrightOwnedBy;
		this.producedBy = producedBy;
		this.containsPII = containsPII;
		this.exposedThrough = exposedThrough;
		this.distribution = distribution;
	}

	public static DataResourceCredentialSubjectBuilder builder() {
		return new DataResourceCredentialSubjectBuilder();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Collection<String> getPolicy() {
		return policy;
	}

	public Collection<String> getLicenses() {
		return licenses;
	}

	public Collection<String> getCopyrightOwnedBy() {
		return copyrightOwnedBy;
	}

	public ProducedBy getProducedBy() {
		return producedBy;
	}

	public boolean getContainsPII() {
		return containsPII;
	}

	public ExposedThrough getExposedThrough() {
		return exposedThrough;
	}

	public Distribution getDistribution() {
		return distribution;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DataResourceCredentialSubject that = (DataResourceCredentialSubject) o;
		return containsPII == that.containsPII && Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
				Objects.equals(description, that.description) && Objects.equals(policy, that.policy) &&
				Objects.equals(licenses, that.licenses) && Objects.equals(copyrightOwnedBy, that.copyrightOwnedBy) &&
				Objects.equals(producedBy, that.producedBy) && Objects.equals(exposedThrough, that.exposedThrough) &&
				Objects.equals(distribution, that.distribution);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, policy, licenses, copyrightOwnedBy, producedBy, containsPII, exposedThrough,
				distribution);
	}

	@Override
	public String toString() {
		return "DataResourceCredentialSubject{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", policy=" + policy +
				", licenses=" + licenses +
				", copyrightOwnedBy=" + copyrightOwnedBy +
				", producedBy=" + producedBy +
				", containsPII=" + containsPII +
				", exposedThrough=" + exposedThrough +
				", distribution=" + distribution +
				'}';
	}

	public static class DataResourceCredentialSubjectBuilder {
		private String id;

		private String name;

		private String description;

		private Collection<String> policy;

		private Collection<String> licenses;

		private Collection<String> copyrightOwnedBy;

		private ProducedBy producedBy;

		private boolean containsPII;

		private ExposedThrough exposedThrough;

		private Distribution distribution;

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

		public DataResourceCredentialSubjectBuilder policy(Collection<String> policy) {
			this.policy = policy;
			return this;
		}

		public DataResourceCredentialSubjectBuilder licenses(Collection<String> licenses) {
			this.licenses = licenses;
			return this;
		}

		public DataResourceCredentialSubjectBuilder copyrightOwnedBy(Collection<String> copyrightOwnedBy) {
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

		public DataResourceCredentialSubjectBuilder distribution(Distribution distribution) {
			this.distribution = distribution;
			return this;
		}

		public DataResourceCredentialSubject build() {
			return new DataResourceCredentialSubject(id, name, description, policy, licenses, copyrightOwnedBy, producedBy, containsPII,
					exposedThrough, distribution);
		}

		@Override
		public String toString() {
			return "DataResourceCredentialSubjectBuilder{" +
					"id='" + id + '\'' +
					", name='" + name + '\'' +
					", description='" + description + '\'' +
					", policy=" + policy +
					", licenses=" + licenses +
					", copyrightOwnedBy=" + copyrightOwnedBy +
					", producedBy=" + producedBy +
					", containsPII=" + containsPII +
					", exposedThrough=" + exposedThrough +
					", distribution=" + distribution +
					'}';
		}
	}
}
