package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.LegalDocument;

import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.GAIAX_NS;

@JsonLdType("gx:SubContractor")
public class SubContractor {
	@JsonLdProperty(value = "applicableJurisdiction", namespace = GAIAX_NS, mandatory = true)
	private final String applicableJurisdiction;

	@JsonLdProperty(value = "legalName", namespace = GAIAX_NS, mandatory = true)
	private final String legalName;

	@JsonLdProperty(value = "communicationMethods", namespace = GAIAX_NS, mandatory = true)
	private final Collection<LegalDocument> communicationMethods;

	@JsonLdProperty(value = "informationDocuments", namespace = GAIAX_NS, mandatory = true)
	private final Collection<LegalDocument> informationDocuments;

	public SubContractor(String applicableJurisdiction, String legalName, Collection<LegalDocument> communicationMethods,
			Collection<LegalDocument> informationDocuments) {
		this.applicableJurisdiction = applicableJurisdiction;
		this.legalName = legalName;
		this.communicationMethods = communicationMethods;
		this.informationDocuments = informationDocuments;
	}

	public static SubContractorBuilder builder() {
		return new SubContractorBuilder();
	}

	public String getApplicableJurisdiction() {
		return applicableJurisdiction;
	}

	public String getLegalName() {
		return legalName;
	}

	public Collection<LegalDocument> getCommunicationMethods() {
		return communicationMethods;
	}

	public Collection<LegalDocument> getInformationDocuments() {
		return informationDocuments;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SubContractor that = (SubContractor) o;
		return Objects.equals(applicableJurisdiction, that.applicableJurisdiction) &&
				Objects.equals(legalName, that.legalName) &&
				Objects.equals(communicationMethods, that.communicationMethods) &&
				Objects.equals(informationDocuments, that.informationDocuments);
	}

	@Override
	public int hashCode() {
		return Objects.hash(applicableJurisdiction, legalName, communicationMethods, informationDocuments);
	}

	@Override
	public String toString() {
		return "SubContractor{" +
				"applicableJurisdiction='" + applicableJurisdiction + '\'' +
				", legalName='" + legalName + '\'' +
				", communicationMethods=" + communicationMethods +
				", informationDocuments=" + informationDocuments +
				'}';
	}

	public static class SubContractorBuilder {
		private String applicableJurisdiction;

		private String legalName;

		private Collection<LegalDocument> communicationMethods;

		private Collection<LegalDocument> informationDocuments;

		SubContractorBuilder() {
		}

		public SubContractorBuilder applicableJurisdiction(String applicableJurisdiction) {
			this.applicableJurisdiction = applicableJurisdiction;
			return this;
		}

		public SubContractorBuilder legalName(String legalName) {
			this.legalName = legalName;
			return this;
		}

		public SubContractorBuilder communicationMethods(Collection<LegalDocument> communicationMethods) {
			this.communicationMethods = communicationMethods;
			return this;
		}

		public SubContractorBuilder informationDocuments(Collection<LegalDocument> informationDocuments) {
			this.informationDocuments = informationDocuments;
			return this;
		}

		public SubContractor build() {
			return new SubContractor(applicableJurisdiction, legalName, communicationMethods, informationDocuments);
		}

		@Override
		public String toString() {
			return "SubContractorBuilder{" +
					"applicableJurisdiction='" + applicableJurisdiction + '\'' +
					", legalName='" + legalName + '\'' +
					", communicationMethods=" + communicationMethods +
					", informationDocuments=" + informationDocuments +
					'}';
		}
	}
}
