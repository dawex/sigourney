package com.dawex.sigourney.trustframework.vc.model.v2210.organisation;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2210.Namespace;

import java.util.Objects;

@JsonLdType("gx:legalRegistrationNumber")
public class OrganisationLegalRegistrationNumber {
	@JsonLdProperty(value = "id")
	private final String id;

	@JsonLdProperty(value = "taxID", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String taxId;

	@JsonLdProperty(value = "EUID", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String euId;

	@JsonLdProperty(value = "EORI", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String eori;

	@JsonLdProperty(value = "vatID", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String vatId;

	@JsonLdProperty(value = "leiCode", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String leiCode;

	public OrganisationLegalRegistrationNumber(String id, String taxId, String euId, String eori, String vatId, String leiCode) {
		this.id = id;
		this.taxId = taxId;
		this.euId = euId;
		this.eori = eori;
		this.vatId = vatId;
		this.leiCode = leiCode;
	}

	public static OrganisationLegalRegistrationNumberBuilder builder() {
		return new OrganisationLegalRegistrationNumberBuilder();
	}

	public String getId() {
		return id;
	}

	public String getTaxId() {
		return taxId;
	}

	public String getEuId() {
		return euId;
	}

	public String getEori() {
		return eori;
	}

	public String getVatId() {
		return vatId;
	}

	public String getLeiCode() {
		return leiCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OrganisationLegalRegistrationNumber that = (OrganisationLegalRegistrationNumber) o;
		return Objects.equals(id, that.id) && Objects.equals(taxId, that.taxId) &&
				Objects.equals(euId, that.euId) && Objects.equals(eori, that.eori) &&
				Objects.equals(vatId, that.vatId) && Objects.equals(leiCode, that.leiCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, taxId, euId, eori, vatId, leiCode);
	}

	@Override
	public String toString() {
		return "OrganisationLegalRegistrationNumber{" +
				"id='" + id + '\'' +
				", taxId='" + taxId + '\'' +
				", euId='" + euId + '\'' +
				", eori='" + eori + '\'' +
				", vatId='" + vatId + '\'' +
				", leiCode='" + leiCode + '\'' +
				'}';
	}

	public static class OrganisationLegalRegistrationNumberBuilder {
		private String id;

		private String taxId;

		private String euId;

		private String eori;

		private String vatId;

		private String leiCode;

		OrganisationLegalRegistrationNumberBuilder() {
		}

		public OrganisationLegalRegistrationNumberBuilder id(String id) {
			this.id = id;
			return this;
		}

		public OrganisationLegalRegistrationNumberBuilder taxId(String taxId) {
			this.taxId = taxId;
			return this;
		}

		public OrganisationLegalRegistrationNumberBuilder euId(String euId) {
			this.euId = euId;
			return this;
		}

		public OrganisationLegalRegistrationNumberBuilder eori(String eori) {
			this.eori = eori;
			return this;
		}

		public OrganisationLegalRegistrationNumberBuilder vatId(String vatId) {
			this.vatId = vatId;
			return this;
		}

		public OrganisationLegalRegistrationNumberBuilder leiCode(String leiCode) {
			this.leiCode = leiCode;
			return this;
		}

		public OrganisationLegalRegistrationNumber build() {
			return new OrganisationLegalRegistrationNumber(id, taxId, euId, eori, vatId, leiCode);
		}

		@Override
		public String toString() {
			return "OrganisationLegalRegistrationNumberBuilder{" +
					"id='" + id + '\'' +
					", taxId='" + taxId + '\'' +
					", euId='" + euId + '\'' +
					", eori='" + eori + '\'' +
					", vatId='" + vatId + '\'' +
					", leiCode='" + leiCode + '\'' +
					'}';
		}
	}
}
