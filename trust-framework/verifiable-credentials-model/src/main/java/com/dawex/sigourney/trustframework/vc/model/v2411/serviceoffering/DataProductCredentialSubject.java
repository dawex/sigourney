package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2411.Namespace;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.AccessUsagePolicy;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.ContactInformation;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.TermsAndConditions;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.LegalDocument;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.providedby.ProvidedBy;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.XsdDataType.XSD_DATE;
import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.DATA_PRODUCT_CREDENTIAL_SUBJECT;

public class DataProductCredentialSubject extends ServiceOfferingCredentialSubject {

	@JsonLdProperty(value = "identifier", namespace = Namespace.DC_TERMS_NS, mandatory = true)
	private final String identifier;

	@JsonLdProperty(value = "title", namespace = Namespace.DC_TERMS_NS, mandatory = true)
	private final String title;

	@JsonLdProperty(value = "issued", namespace = Namespace.DC_TERMS_NS, type = XSD_DATE)
	private final LocalDate issued;

	@JsonLdProperty(value = "termsAndConditions", namespace = Namespace.GAIAX_NS, mandatory = true)
	private final String termsAndConditions;

	@JsonLdProperty(value = "license", namespace = Namespace.DC_TERMS_NS, mandatory = true)
	private final Collection<String> licenses;

	@JsonLdProperty(value = "aggregationOf", namespace = Namespace.GAIAX_NS, mandatory = true)
	private final Collection<AggregationOf> aggregationOf;

	public DataProductCredentialSubject(String id, String name, String description, ProvidedBy providedBy,
			Collection<TermsAndConditions> serviceOfferingTermsAndConditions, Collection<AccessUsagePolicy> servicePolicy,
			Collection<DataAccountExport> dataAccountExport, Collection<AggregationOfResource> aggregationOfResources,
			Collection<LegalDocument> legalDocuments, Collection<SubContractor> subContractors, Collection<Measure> requiredMeasures,
			ContactInformation providerContactInformation, String identifier, String title, LocalDate issued, String termsAndConditions,
			Collection<String> licenses, Collection<AggregationOf> aggregationOf) {
		super(id, name, description, providedBy, serviceOfferingTermsAndConditions, servicePolicy, dataAccountExport,
				aggregationOfResources,
				legalDocuments, subContractors, requiredMeasures, providerContactInformation);
		this.identifier = identifier;
		this.title = title;
		this.issued = issued;
		this.termsAndConditions = termsAndConditions;
		this.licenses = licenses;
		this.aggregationOf = aggregationOf;
	}

	public static DataProductCredentialSubjectBuilder builder() {
		return new DataProductCredentialSubjectBuilder();
	}

	@JsonLdProperty(value = "id", formatName = DATA_PRODUCT_CREDENTIAL_SUBJECT)
	@Override
	public String getId() {
		return super.getId();
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getTitle() {
		return title;
	}

	public LocalDate getIssued() {
		return issued;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public Collection<String> getLicenses() {
		return licenses;
	}

	public Collection<AggregationOf> getAggregationOf() {
		return aggregationOf;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		DataProductCredentialSubject that = (DataProductCredentialSubject) o;
		return Objects.equals(identifier, that.identifier) && Objects.equals(title, that.title) &&
				Objects.equals(issued, that.issued) && Objects.equals(termsAndConditions, that.termsAndConditions) &&
				Objects.equals(licenses, that.licenses) && Objects.equals(aggregationOf, that.aggregationOf);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), identifier, title, issued, termsAndConditions, licenses, aggregationOf);
	}

	@Override
	public String toString() {
		return "DataProductCredentialSubject{" +
				"identifier='" + identifier + '\'' +
				", title='" + title + '\'' +
				", issued=" + issued +
				", termsAndConditions='" + termsAndConditions + '\'' +
				", licenses=" + licenses +
				", id='" + id + '\'' +
				", providedBy=" + providedBy +
				", serviceOfferingTermsAndConditions=" + serviceOfferingTermsAndConditions +
				", servicePolicy=" + servicePolicy +
				", dataAccountExport=" + dataAccountExport +
				", aggregationOfResources=" + aggregationOfResources +
				", legalDocuments=" + legalDocuments +
				", subContractors=" + subContractors +
				", requiredMeasures=" + requiredMeasures +
				", providerContactInformation=" + providerContactInformation +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", aggregationOf='" + aggregationOf + '\'' +
				'}';
	}

	public static class DataProductCredentialSubjectBuilder extends ServiceOfferingCredentialSubjectBuilder<DataProductCredentialSubject> {
		private String identifier;

		private String title;

		private LocalDate issued;

		private String termsAndConditions;

		private Collection<String> licenses;

		private Collection<AggregationOf> aggregationOf;

		DataProductCredentialSubjectBuilder() {
		}

		public DataProductCredentialSubjectBuilder identifier(String identifier) {
			this.identifier = identifier;
			return this;
		}

		public DataProductCredentialSubjectBuilder title(String title) {
			this.title = title;
			return this;
		}

		public DataProductCredentialSubjectBuilder issued(LocalDate issued) {
			this.issued = issued;
			return this;
		}

		public DataProductCredentialSubjectBuilder termsAndConditions(String termsAndConditions) {
			this.termsAndConditions = termsAndConditions;
			return this;
		}

		public DataProductCredentialSubjectBuilder licenses(Collection<String> licenses) {
			this.licenses = licenses;
			return this;
		}

		public DataProductCredentialSubjectBuilder aggregationOf(Collection<AggregationOf> aggregationOf) {
			this.aggregationOf = aggregationOf;
			return this;
		}

		@Override
		public DataProductCredentialSubject build() {
			return new DataProductCredentialSubject(id, name, description, providedBy, serviceOfferingTermsAndConditions, servicePolicy,
					dataAccountExport, aggregationOfResources, legalDocuments, subContractors, requiredMeasures,
					providerContactInformation, identifier, title, issued, termsAndConditions, licenses, aggregationOf);
		}

		@Override
		public String toString() {
			return "DataProductCredentialSubjectBuilder{" +
					"legalDocuments=" + legalDocuments +
					", identifier='" + identifier + '\'' +
					", title='" + title + '\'' +
					", issued=" + issued +
					", termsAndConditions='" + termsAndConditions + '\'' +
					", licenses=" + licenses +
					", aggregationOf=" + aggregationOf +
					", id='" + id + '\'' +
					", name='" + name + '\'' +
					", description='" + description + '\'' +
					", providedBy=" + providedBy +
					", serviceOfferingTermsAndConditions=" + serviceOfferingTermsAndConditions +
					", servicePolicy=" + servicePolicy +
					", dataAccountExport=" + dataAccountExport +
					", aggregationOfResources=" + aggregationOfResources +
					", subContractors=" + subContractors +
					", requiredMeasures=" + requiredMeasures +
					", providerContactInformation=" + providerContactInformation +
					'}';
		}
	}
}
