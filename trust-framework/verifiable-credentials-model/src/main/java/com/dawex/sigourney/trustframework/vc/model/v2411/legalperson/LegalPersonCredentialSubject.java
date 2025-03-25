package com.dawex.sigourney.trustframework.vc.model.v2411.legalperson;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2411.Namespace;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.Address;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.GaiaxCredentialSubject;

import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.LEGAL_PERSON_CREDENTIAL_SUBJECT;

public class LegalPersonCredentialSubject extends GaiaxCredentialSubject {
	@JsonLdProperty(value = "id", formatName = LEGAL_PERSON_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "registrationNumber", namespace = Namespace.GAIAX_NS, mandatory = true)
	private final RegistrationNumber registrationNumber;

	@JsonLdProperty(value = "headquartersAddress", namespace = Namespace.GAIAX_NS, mandatory = true)
	private final Address headquartersAddress;

	@JsonLdProperty(value = "legalAddress", namespace = Namespace.GAIAX_NS, mandatory = true)
	private final Address legalAddress;

	public LegalPersonCredentialSubject(String id, String name, String description, RegistrationNumber registrationNumber,
			Address headquartersAddress, Address legalAddress) {
		super(name, description);
		this.id = id;
		this.registrationNumber = registrationNumber;
		this.headquartersAddress = headquartersAddress;
		this.legalAddress = legalAddress;
	}

	public static LegalPersonCredentialSubjectBuilder<? extends LegalPersonCredentialSubject> builder() {
		return new LegalPersonCredentialSubjectBuilder<>() {
			@Override
			public LegalPersonCredentialSubject build() {
				return new LegalPersonCredentialSubject(id, name, description, registrationNumber, headquartersAddress, legalAddress);
			}
		};
	}

	public String getId() {
		return id;
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
				Objects.equals(this.description, that.description) &&
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
				"description=" + description + ", " +
				"registrationNumber=" + registrationNumber + ", " +
				"headquartersAddress=" + headquartersAddress + ", " +
				"legalAddress=" + legalAddress + ']';
	}

	public abstract static class LegalPersonCredentialSubjectBuilder<T extends LegalPersonCredentialSubject> {
		protected String id;

		protected String name;

		protected String description;

		protected RegistrationNumber registrationNumber;

		protected Address headquartersAddress;

		protected Address legalAddress;

		LegalPersonCredentialSubjectBuilder() {
		}

		public LegalPersonCredentialSubjectBuilder<T> id(String id) {
			this.id = id;
			return this;
		}

		public LegalPersonCredentialSubjectBuilder<T> name(String name) {
			this.name = name;
			return this;
		}

		public LegalPersonCredentialSubjectBuilder<T> description(String description) {
			this.description = description;
			return this;
		}

		public LegalPersonCredentialSubjectBuilder<T> registrationNumber(RegistrationNumber registrationNumber) {
			this.registrationNumber = registrationNumber;
			return this;
		}

		public LegalPersonCredentialSubjectBuilder<T> headquartersAddress(Address headquartersAddress) {
			this.headquartersAddress = headquartersAddress;
			return this;
		}

		public LegalPersonCredentialSubjectBuilder<T> legalAddress(Address legalAddress) {
			this.legalAddress = legalAddress;
			return this;
		}

		public abstract T build();

		@Override
		public String toString() {
			return "LegalPersonCredentialSubjectBuilder{" +
					"id='" + id + '\'' +
					", name='" + name + '\'' +
					", description='" + description + '\'' +
					", registrationNumber='" + registrationNumber + '\'' +
					", headquartersAddress=" + headquartersAddress +
					", legalAddress=" + legalAddress +
					'}';
		}
	}
}
