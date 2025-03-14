package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.AccessUsagePolicy;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.ContactInformation;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.GaiaxCredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.TermsAndConditions;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.LegalDocument;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.providedby.ProvidedBy;

import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.GAIAX_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.SERVICE_OFFERING_CREDENTIAL_SUBJECT;

public class ServiceOfferingCredentialSubject extends GaiaxCredentialSubject {
	@JsonLdProperty(value = "id", formatName = SERVICE_OFFERING_CREDENTIAL_SUBJECT)
	protected final String id;

	@JsonLdProperty(value = "providedBy", namespace = GAIAX_NS, mandatory = true)
	protected final ProvidedBy providedBy;

	@JsonLdProperty(value = "serviceOfferingTermsAndConditions", namespace = GAIAX_NS, mandatory = true)
	protected final Collection<TermsAndConditions> serviceOfferingTermsAndConditions;

	@JsonLdProperty(value = "servicePolicy", namespace = GAIAX_NS)
	protected final Collection<AccessUsagePolicy> servicePolicy;

	@JsonLdProperty(value = "dataAccountExport", namespace = GAIAX_NS, mandatory = true)
	protected final Collection<DataAccountExport> dataAccountExport;

	@JsonLdProperty(value = "aggregationOfResources", namespace = GAIAX_NS)
	protected final Collection<AggregationOfResource> aggregationOfResources;

	@JsonLdProperty(value = "legalDocuments", namespace = GAIAX_NS)
	protected final Collection<LegalDocument> legalDocuments;

	@JsonLdProperty(value = "subContractors", namespace = GAIAX_NS)
	protected final Collection<SubContractor> subContractors;

	@JsonLdProperty(value = "requiredMeasures", namespace = GAIAX_NS)
	protected final Collection<Measure> requiredMeasures;

	@JsonLdProperty(value = "providerContactInformation", namespace = GAIAX_NS)
	protected final ContactInformation providerContactInformation;

	public ServiceOfferingCredentialSubject(String id, String name, String description, ProvidedBy providedBy,
			Collection<TermsAndConditions> serviceOfferingTermsAndConditions, Collection<AccessUsagePolicy> servicePolicy,
			Collection<DataAccountExport> dataAccountExport, Collection<AggregationOfResource> aggregationOfResources,
			Collection<LegalDocument> legalDocuments, Collection<SubContractor> subContractors, Collection<Measure> requiredMeasures,
			ContactInformation providerContactInformation) {
		super(name, description);
		this.id = id;
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

	public static ServiceOfferingCredentialSubjectBuilder<? extends ServiceOfferingCredentialSubject> builder() {
		return new ServiceOfferingCredentialSubjectBuilder<>() {
			@Override
			public ServiceOfferingCredentialSubject build() {
				return new ServiceOfferingCredentialSubject(id, name, description, providedBy, serviceOfferingTermsAndConditions,
						servicePolicy, dataAccountExport, aggregationOfResources, legalDocuments, subContractors, requiredMeasures,
						providerContactInformation);
			}
		};
	}

	public String getId() {
		return id;
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

	public abstract static class ServiceOfferingCredentialSubjectBuilder<T extends ServiceOfferingCredentialSubject> {
		protected String id;

		protected String name;

		protected String description;

		protected ProvidedBy providedBy;

		protected Collection<TermsAndConditions> serviceOfferingTermsAndConditions;

		protected Collection<AccessUsagePolicy> servicePolicy;

		protected Collection<DataAccountExport> dataAccountExport;

		protected Collection<AggregationOfResource> aggregationOfResources;

		protected Collection<LegalDocument> legalDocuments;

		protected Collection<SubContractor> subContractors;

		protected Collection<Measure> requiredMeasures;

		protected ContactInformation providerContactInformation;

		ServiceOfferingCredentialSubjectBuilder() {
		}

		public ServiceOfferingCredentialSubjectBuilder<T> id(String id) {
			this.id = id;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder<T> name(String name) {
			this.name = name;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder<T> description(String description) {
			this.description = description;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder<T> providedBy(ProvidedBy providedBy) {
			this.providedBy = providedBy;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder<T> serviceOfferingTermsAndConditions(
				Collection<TermsAndConditions> serviceOfferingTermsAndConditions) {
			this.serviceOfferingTermsAndConditions = serviceOfferingTermsAndConditions;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder<T> servicePolicy(Collection<AccessUsagePolicy> servicePolicy) {
			this.servicePolicy = servicePolicy;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder<T> dataAccountExport(Collection<DataAccountExport> dataAccountExport) {
			this.dataAccountExport = dataAccountExport;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder<T> aggregationOfResources(Collection<AggregationOfResource> aggregationOfResources) {
			this.aggregationOfResources = aggregationOfResources;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder<T> legalDocuments(Collection<LegalDocument> legalDocuments) {
			this.legalDocuments = legalDocuments;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder<T> subContractors(Collection<SubContractor> subContractors) {
			this.subContractors = subContractors;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder<T> requiredMeasures(Collection<Measure> requiredMeasures) {
			this.requiredMeasures = requiredMeasures;
			return this;
		}

		public ServiceOfferingCredentialSubjectBuilder<T> providerContactInformation(ContactInformation providerContactInformation) {
			this.providerContactInformation = providerContactInformation;
			return this;
		}

		public abstract T build();

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
