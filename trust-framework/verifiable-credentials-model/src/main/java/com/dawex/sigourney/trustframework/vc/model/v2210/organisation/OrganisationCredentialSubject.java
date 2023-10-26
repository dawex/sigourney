package com.dawex.sigourney.trustframework.vc.model.v2210.organisation;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2210.Namespace;
import com.dawex.sigourney.trustframework.vc.model.v2210.common.Address;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format;

import java.util.Objects;

/**
 * @see <a href="https://www.w3.org/2018/credentials/v1">Verifiable Credential Schema</a>
 */
@JsonLdType("gx:LegalParticipant")
public class OrganisationCredentialSubject {
	@JsonLdProperty(value = "id", formatName = Format.ORGANISATION_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "name", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final String name;

	@JsonLdProperty(value = "legalRegistrationNumber", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final OrganisationLegalRegistrationNumber registrationNumber;

	@JsonLdProperty(value = "headquarterAddress", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final Address headquarterAddress;

	@JsonLdProperty(value = "legalAddress", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final Address legalAddress;

	public OrganisationCredentialSubject(String id, String name, OrganisationLegalRegistrationNumber registrationNumber,
			Address headquarterAddress, Address legalAddress) {
		this.id = id;
		this.name = name;
		this.registrationNumber = registrationNumber;
		this.headquarterAddress = headquarterAddress;
		this.legalAddress = legalAddress;
	}

	public static OrganisationCredentialSubjectBuilder builder() {
		return new OrganisationCredentialSubjectBuilder();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public OrganisationLegalRegistrationNumber getRegistrationNumber() {
		return registrationNumber;
	}

	public Address getHeadquarterAddress() {
		return headquarterAddress;
	}

	public Address getLegalAddress() {
		return legalAddress;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		var that = (OrganisationCredentialSubject) obj;
		return Objects.equals(this.id, that.id) &&
				Objects.equals(this.name, that.name) &&
				Objects.equals(this.registrationNumber, that.registrationNumber) &&
				Objects.equals(this.headquarterAddress, that.headquarterAddress) &&
				Objects.equals(this.legalAddress, that.legalAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, registrationNumber, headquarterAddress, legalAddress);
	}

	@Override
	public String toString() {
		return "OrganisationCredentialSubject[" +
				"id=" + id + ", " +
				"name=" + name + ", " +
				"registrationNumber=" + registrationNumber + ", " +
				"headquarterAddress=" + headquarterAddress + ", " +
				"legalAddress=" + legalAddress + ']';
	}

	public static class OrganisationCredentialSubjectBuilder {
		private String id;

		private String name;

		private OrganisationLegalRegistrationNumber registrationNumber;

		private Address headquarterAddress;

		private Address legalAddress;

		OrganisationCredentialSubjectBuilder() {
		}

		public OrganisationCredentialSubjectBuilder id(String id) {
			this.id = id;
			return this;
		}

		public OrganisationCredentialSubjectBuilder name(String name) {
			this.name = name;
			return this;
		}

		public OrganisationCredentialSubjectBuilder registrationNumber(OrganisationLegalRegistrationNumber registrationNumber) {
			this.registrationNumber = registrationNumber;
			return this;
		}

		public OrganisationCredentialSubjectBuilder headquarterAddress(Address headquarterAddress) {
			this.headquarterAddress = headquarterAddress;
			return this;
		}

		public OrganisationCredentialSubjectBuilder legalAddress(Address legalAddress) {
			this.legalAddress = legalAddress;
			return this;
		}

		public OrganisationCredentialSubject build() {
			return new OrganisationCredentialSubject(id, name, registrationNumber, headquarterAddress, legalAddress);
		}

		@Override
		public String toString() {
			return "OrganisationCredentialSubjectBuilder{" +
					"id='" + id + '\'' +
					", name='" + name + '\'' +
					", registrationNumber='" + registrationNumber + '\'' +
					", headquarterAddress=" + headquarterAddress +
					", legalAddress=" + legalAddress +
					'}';
		}
	}
}
