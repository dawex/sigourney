package com.dawex.sigourney.trustframework.vc.model.v2411.legalperson;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.CredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.Namespace;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.Address;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format;

import java.util.Objects;

public class LegalPersonCredentialSubject implements CredentialSubject {
	@JsonLdProperty(value = "id", formatName = Format.LEGAL_PERSON_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "name", namespace = Namespace.SCHEMA_NS, mandatory = true)
	private final String name;

	@JsonLdProperty(value = "registrationNumber", namespace = Namespace.GAIAX_NS, mandatory = true)
	private final RegistrationNumber registrationNumber;

	@JsonLdProperty(value = "headquartersAddress", namespace = Namespace.GAIAX_NS, mandatory = true)
	private final Address headquartersAddress;

	@JsonLdProperty(value = "legalAddress", namespace = Namespace.GAIAX_NS, mandatory = true)
	private final Address legalAddress;

	public LegalPersonCredentialSubject(String id, String name, RegistrationNumber registrationNumber,
			Address headquartersAddress, Address legalAddress) {
		this.id = id;
		this.name = name;
		this.registrationNumber = registrationNumber;
		this.headquartersAddress = headquartersAddress;
		this.legalAddress = legalAddress;
	}

	public static LegalPersonCredentialSubjectBuilder builder() {
		return new LegalPersonCredentialSubjectBuilder();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public RegistrationNumber getRegistrationNumber() {
		return registrationNumber;
	}

	public Address getHeadquartersAddress() {
		return headquartersAddress;
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
		var that = (LegalPersonCredentialSubject) obj;
		return Objects.equals(this.id, that.id) &&
				Objects.equals(this.name, that.name) &&
				Objects.equals(this.registrationNumber, that.registrationNumber) &&
				Objects.equals(this.headquartersAddress, that.headquartersAddress) &&
				Objects.equals(this.legalAddress, that.legalAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, registrationNumber, headquartersAddress, legalAddress);
	}

	@Override
	public String toString() {
		return "LegalPersonCredentialSubject[" +
				"id=" + id + ", " +
				"name=" + name + ", " +
				"registrationNumber=" + registrationNumber + ", " +
				"headquartersAddress=" + headquartersAddress + ", " +
				"legalAddress=" + legalAddress + ']';
	}

	public static class LegalPersonCredentialSubjectBuilder {
		private String id;

		private String name;

		private RegistrationNumber registrationNumber;

		private Address headquartersAddress;

		private Address legalAddress;

		LegalPersonCredentialSubjectBuilder() {
		}

		public LegalPersonCredentialSubjectBuilder id(String id) {
			this.id = id;
			return this;
		}

		public LegalPersonCredentialSubjectBuilder name(String name) {
			this.name = name;
			return this;
		}

		public LegalPersonCredentialSubjectBuilder registrationNumber(RegistrationNumber registrationNumber) {
			this.registrationNumber = registrationNumber;
			return this;
		}

		public LegalPersonCredentialSubjectBuilder headquartersAddress(Address headquartersAddress) {
			this.headquartersAddress = headquartersAddress;
			return this;
		}

		public LegalPersonCredentialSubjectBuilder legalAddress(Address legalAddress) {
			this.legalAddress = legalAddress;
			return this;
		}

		public LegalPersonCredentialSubject build() {
			return new LegalPersonCredentialSubject(id, name, registrationNumber, headquartersAddress, legalAddress);
		}

		@Override
		public String toString() {
			return "LegalPersonCredentialSubjectBuilder{" +
					"id='" + id + '\'' +
					", name='" + name + '\'' +
					", registrationNumber='" + registrationNumber + '\'' +
					", headquartersAddress=" + headquartersAddress +
					", legalAddress=" + legalAddress +
					'}';
		}
	}
}
