package com.dawex.sigourney.trustframework.vc.model.v2411.physicalresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.CredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.Namespace;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.Address;

import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.GAIAX_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.PHYSICAL_RESOURCE_CREDENTIAL_SUBJECT;

public class PhysicalResourceCredentialSubject implements CredentialSubject {
	@JsonLdProperty(value = "id", formatName = PHYSICAL_RESOURCE_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "maintainedBy", namespace = GAIAX_NS)
	private final MaintainedBy maintainedBy;

	@JsonLdProperty(value = "location", namespace = Namespace.GAIAX_NS, mandatory = true)
	private final Address location;

	public PhysicalResourceCredentialSubject(String id, MaintainedBy maintainedBy, Address location) {
		this.id = id;
		this.maintainedBy = maintainedBy;
		this.location = location;
	}

	public static PhysicalResourceCredentialSubjectBuilder builder() {
		return new PhysicalResourceCredentialSubjectBuilder();
	}

	@Override
	public String getId() {
		return id;
	}

	public MaintainedBy getMaintainedBy() {
		return maintainedBy;
	}

	public Address getLocation() {
		return location;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PhysicalResourceCredentialSubject that = (PhysicalResourceCredentialSubject) o;
		return Objects.equals(id, that.id) && Objects.equals(maintainedBy, that.maintainedBy) &&
				Objects.equals(location, that.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, maintainedBy, location);
	}

	@Override
	public String toString() {
		return "PhysicalResourceCredentialSubject{" +
				"id='" + id + '\'' +
				", maintainedBy='" + maintainedBy + '\'' +
				", location=" + location +
				'}';
	}

	public static class PhysicalResourceCredentialSubjectBuilder {
		private String id;

		private MaintainedBy maintainedBy;

		private Address location;

		PhysicalResourceCredentialSubjectBuilder() {
		}

		public PhysicalResourceCredentialSubjectBuilder id(String id) {
			this.id = id;
			return this;
		}

		public PhysicalResourceCredentialSubjectBuilder maintainedBy(MaintainedBy maintainedBy) {
			this.maintainedBy = maintainedBy;
			return this;
		}

		public PhysicalResourceCredentialSubjectBuilder location(Address location) {
			this.location = location;
			return this;
		}

		public PhysicalResourceCredentialSubject build() {
			return new PhysicalResourceCredentialSubject(id, maintainedBy, location);
		}

		@Override
		public String toString() {
			return "PhysicalResourceCredentialSubjectBuilder{" +
					"id='" + id + '\'' +
					", maintainedBy='" + maintainedBy + '\'' +
					", location=" + location +
					'}';
		}
	}
}
