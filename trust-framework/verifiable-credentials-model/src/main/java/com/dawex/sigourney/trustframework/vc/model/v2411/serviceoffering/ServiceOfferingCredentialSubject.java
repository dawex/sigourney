package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.CredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.AccessUsagePolicy;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.ContactInformation;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.TermsAndConditions;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.LegalDocument;

import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.GAIAX_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.SCHEMA_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.SERVICE_OFFERING_CREDENTIAL_SUBJECT;

public class ServiceOfferingCredentialSubject implements CredentialSubject {
	@JsonLdProperty(value = "id", formatName = SERVICE_OFFERING_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "name", namespace = SCHEMA_NS)
	private final String name;

	@JsonLdProperty(value = "description", namespace = SCHEMA_NS)
	private final String description;

	@JsonLdProperty(value = "providedBy", namespace = GAIAX_NS, mandatory = true)
	private final ProvidedBy providedBy;

	@JsonLdProperty(value = "serviceOfferingTermsAndConditions", namespace = GAIAX_NS, mandatory = true)
	private final Collection<TermsAndConditions> serviceOfferingTermsAndConditions;

	@JsonLdProperty(value = "servicePolicy", namespace = GAIAX_NS)
	private final Collection<AccessUsagePolicy> servicePolicy;

	@JsonLdProperty(value = "dataAccountExport", namespace = GAIAX_NS, mandatory = true)
	private final Collection<DataAccountExport> dataAccountExport;

	@JsonLdProperty(value = "aggregationOfResources", namespace = GAIAX_NS)
	private final Collection<AggregationOfResource> aggregationOfResources;

	@JsonLdProperty(value = "legalDocuments", namespace = GAIAX_NS)
	private final Collection<LegalDocument> legalDocuments;

	@JsonLdProperty(value = "subContractors", namespace = GAIAX_NS)
	private final Collection<SubContractor> subContractors;

	@JsonLdProperty(value = "requiredMeasures", namespace = GAIAX_NS)
	private final Collection<Measure> requiredMeasures;

	@JsonLdProperty(value = "providerContactInformation", namespace = GAIAX_NS)
	private final ContactInformation providerContactInformation;

	public ServiceOfferingCredentialSubject(String id, String name, String description, ProvidedBy providedBy,
			Collection<TermsAndConditions> serviceOfferingTermsAndConditions, Collection<AccessUsagePolicy> servicePolicy,
			Collection<DataAccountExport> dataAccountExport, Collection<AggregationOfResource> aggregationOfResources,
			Collection<LegalDocument> legalDocuments, Collection<SubContractor> subContractors, Collection<Measure> requiredMeasures,
			ContactInformation providerContactInformation) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.providedBy = providedBy;
		this.serviceOfferingTermsAndConditions = serviceOfferingTermsAndConditions;
		this.servicePolicy = servicePolicy;
		this.dataAccountExport = dataAccountExport;
		this.aggregationOfResources = aggregationOfResources;
		this.legalDocuments = legalDocuments;
		this.subContractors = subContractors;
		this.requiredMeasures = requiredMeasures;
		this.providerContactInformation = providerContactInformation;
	}

	public static ServiceOfferingCredentialSubjectBuilder builder() {
		return new ServiceOfferingCredentialSubjectBuilder();
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

	public ProvidedBy getProvidedBy() {
		return providedBy;
	}

	public Collection<TermsAndConditions> getServiceOfferingTermsAndConditions() {
		return serviceOfferingTermsAndConditions;
	}

	public Collection<AccessUsagePolicy> getServicePolicy() {
		return servicePolicy;
	}

	public Collection<DataAccountExport> getDataAccountExport() {
		return dataAccountExport;
	}

	public Collection<AggregationOfResource> getAggregationOfResources() {
		return aggregationOfResources;
	}

	public Collection<LegalDocument> getLegalDocuments() {
		return legalDocuments;
	}

	public Collection<SubContractor> getSubContractors() {
		return subContractors;
	}

	public Collection<Measure> getRequiredMeasures() {
		return requiredMeasures;
	}

	public ContactInformation getProviderContactInformation() {
		return providerContactInformation;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ServiceOfferingCredentialSubject that = (ServiceOfferingCredentialSubject) o;
		return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
				Objects.equals(description, that.description) && Objects.equals(providedBy, that.providedBy) &&
				Objects.equals(serviceOfferingTermsAndConditions, that.serviceOfferingTermsAndConditions) &&
				Objects.equals(servicePolicy, that.servicePolicy) && Objects.equals(dataAccountExport, that.dataAccountExport) &&
				Objects.equals(aggregationOfResources, that.aggregationOfResources) &&
				Objects.equals(legalDocuments, that.legalDocuments) &&
				Objects.equals(subContractors, that.subContractors) &&
				Objects.equals(requiredMeasures, that.requiredMeasures) &&
				Objects.equals(providerContactInformation, that.providerContactInformation);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, providedBy, serviceOfferingTermsAndConditions, servicePolicy, dataAccountExport,
				aggregationOfResources, legalDocuments, subContractors, requiredMeasures, providerContactInformation);
	}

	@Override
	public String toString() {
		return "ServiceOfferingCredentialSubject{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", providedBy=" + providedBy +
				", serviceOfferingTermsAndConditions=" + serviceOfferingTermsAndConditions +
				", servicePolicy=" + servicePolicy +
				", dataAccountExport=" + dataAccountExport +
				", aggregationOfResources=" + aggregationOfResources +
				", legalDocuments=" + legalDocuments +
				", subContractors=" + subContractors +
				", requiredMeasures=" + requiredMeasures +
				", providerContactInformation=" + providerContactInformation +
				'}';
	}

	public static class ServiceOfferingCredentialSubjectBuilder {
		private String id;

		private String name;

		private String description;

		private ProvidedBy providedBy;

		private Collection<TermsAndConditions> serviceOfferingTermsAndConditions;

		private Collection<AccessUsagePolicy> servicePolicy;

		private Collection<DataAccountExport> dataAccountExport;

		private Collection<AggregationOfResource> aggregationOfResources;

		private Collection<LegalDocument> legalDocuments;

		private Collection<SubContractor> subContractors;

		private Collection<Measure> requiredMeasures;

		private ContactInformation providerContactInformation;

		ServiceOfferingCredentialSubjectBuilder() {
		}

		public ServiceOfferingCredentialSubjectBuilder id(String id) {
			this.id = id;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder description(String description) {
			this.description = description;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder providedBy(ProvidedBy providedBy) {
			this.providedBy = providedBy;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder termsAndConditions(
				Collection<TermsAndConditions> serviceOfferingTermsAndConditions) {
			this.serviceOfferingTermsAndConditions = serviceOfferingTermsAndConditions;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder servicePolicy(Collection<AccessUsagePolicy> servicePolicy) {
			this.servicePolicy = servicePolicy;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder dataAccountExport(Collection<DataAccountExport> dataAccountExport) {
			this.dataAccountExport = dataAccountExport;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder aggregationOfResources(Collection<AggregationOfResource> aggregationOfResources) {
			this.aggregationOfResources = aggregationOfResources;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder legalDocuments(Collection<LegalDocument> legalDocuments) {
			this.legalDocuments = legalDocuments;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder subContractors(Collection<SubContractor> subContractors) {
			this.subContractors = subContractors;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder requiredMeasures(Collection<Measure> requiredMeasures) {
			this.requiredMeasures = requiredMeasures;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder providerContactInformation(ContactInformation providerContactInformation) {
			this.providerContactInformation = providerContactInformation;
			return this;
		}

		public ServiceOfferingCredentialSubject build() {
			return new ServiceOfferingCredentialSubject(id, name, description, providedBy, serviceOfferingTermsAndConditions,
					servicePolicy, dataAccountExport, aggregationOfResources, legalDocuments, subContractors, requiredMeasures,
					providerContactInformation);
		}

		@Override
		public String toString() {
			return "ServiceOfferingCredentialSubjectBuilder{" +
					"id='" + id + '\'' +
					", name='" + name + '\'' +
					", description='" + description + '\'' +
					", providedBy=" + providedBy +
					", serviceOfferingTermsAndConditions=" + serviceOfferingTermsAndConditions +
					", servicePolicy=" + servicePolicy +
					", dataAccountExport=" + dataAccountExport +
					", aggregationOfResources=" + aggregationOfResources +
					", legalDocuments=" + legalDocuments +
					", subContractors=" + subContractors +
					", requiredMeasures=" + requiredMeasures +
					", providerContactInformation=" + providerContactInformation +
					'}';
		}
	}
}
