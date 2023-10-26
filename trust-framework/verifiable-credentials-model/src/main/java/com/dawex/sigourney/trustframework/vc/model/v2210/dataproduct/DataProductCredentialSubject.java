package com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2210.Namespace;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format.DATA_PRODUCT_CREDENTIAL_SUBJECT;

@JsonLdType("gx:ServiceOffering")
public class DataProductCredentialSubject {
	@JsonLdProperty(value = "id", formatName = DATA_PRODUCT_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "title", namespace = Namespace.DC_TERMS_NS, mandatory = true)
	private final String title;

	@JsonLdProperty(value = "description", namespace = Namespace.DC_TERMS_NS)
	private final String description;

	@JsonLdProperty(value = "issued", namespace = Namespace.DC_TERMS_NS, mandatory = true)
	private final ZonedDateTime issued;

	@JsonLdProperty(value = "providedBy", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final ProvidedBy providedBy;

	@JsonLdProperty(value = "termsAndConditions", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final Collection<TermsAndConditionURI> termsAndConditions;

	@JsonLdProperty(value = "policy", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final Collection<String> policy;

	@JsonLdProperty(value = "dataAccountExport", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final DataAccountExport dataAccountExport;

	@JsonLdProperty(value = "aggregationOf", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final Collection<AggregationOf> aggregationOf;

	public DataProductCredentialSubject(String id, String title, String description, ZonedDateTime issued, ProvidedBy providedBy,
			Collection<TermsAndConditionURI> termsAndConditions, Collection<String> policy, DataAccountExport dataAccountExport,
			Collection<AggregationOf> aggregationOf) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.issued = issued;
		this.providedBy = providedBy;
		this.termsAndConditions = termsAndConditions;
		this.policy = policy;
		this.dataAccountExport = dataAccountExport;
		this.aggregationOf = aggregationOf;
	}

	public static DataProductCredentialSubjectBuilder builder() {
		return new DataProductCredentialSubjectBuilder();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public ZonedDateTime getIssued() {
		return issued;
	}

	public ProvidedBy getProvidedBy() {
		return providedBy;
	}

	public Collection<TermsAndConditionURI> getTermsAndConditions() {
		return termsAndConditions;
	}

	public Collection<String> getPolicy() {
		return policy;
	}

	public DataAccountExport getDataAccountExport() {
		return dataAccountExport;
	}

	public Collection<AggregationOf> getAggregationOf() {
		return aggregationOf;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DataProductCredentialSubject that = (DataProductCredentialSubject) o;
		return Objects.equals(id, that.id) && Objects.equals(title, that.title) &&
				Objects.equals(description, that.description) && Objects.equals(issued, that.issued) &&
				Objects.equals(providedBy, that.providedBy) &&
				Objects.equals(termsAndConditions, that.termsAndConditions) && Objects.equals(policy, that.policy) &&
				Objects.equals(dataAccountExport, that.dataAccountExport) &&
				Objects.equals(aggregationOf, that.aggregationOf);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, description, issued, providedBy, termsAndConditions, policy, dataAccountExport, aggregationOf);
	}

	@Override
	public String toString() {
		return "DataProductCredentialSubject{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", issued=" + issued +
				", providedBy='" + providedBy + '\'' +
				", termsAndConditions=" + termsAndConditions +
				", policy=" + policy +
				", dataAccountExport=" + dataAccountExport +
				", aggregationOf=" + aggregationOf +
				'}';
	}

	public static class DataProductCredentialSubjectBuilder {
		private String id;

		private String title;

		private String description;

		private ZonedDateTime issued;

		private ProvidedBy providedBy;

		private Collection<TermsAndConditionURI> termsAndConditions;

		private Collection<String> policy;

		private DataAccountExport dataAccountExport;

		private Collection<AggregationOf> aggregationOf;

		DataProductCredentialSubjectBuilder() {
		}

		public DataProductCredentialSubjectBuilder id(String id) {
			this.id = id;
			return this;
		}

		public DataProductCredentialSubjectBuilder title(String title) {
			this.title = title;
			return this;
		}

		public DataProductCredentialSubjectBuilder description(String description) {
			this.description = description;
			return this;
		}

		public DataProductCredentialSubjectBuilder issued(ZonedDateTime issued) {
			this.issued = issued;
			return this;
		}

		public DataProductCredentialSubjectBuilder providedBy(ProvidedBy providedBy) {
			this.providedBy = providedBy;
			return this;
		}

		public DataProductCredentialSubjectBuilder termsAndConditions(Collection<TermsAndConditionURI> termsAndConditions) {
			this.termsAndConditions = termsAndConditions;
			return this;
		}

		public DataProductCredentialSubjectBuilder policy(Collection<String> policy) {
			this.policy = policy;
			return this;
		}

		public DataProductCredentialSubjectBuilder dataAccountExport(DataAccountExport dataAccountExport) {
			this.dataAccountExport = dataAccountExport;
			return this;
		}

		public DataProductCredentialSubjectBuilder aggregationOf(Collection<AggregationOf> aggregationOf) {
			this.aggregationOf = aggregationOf;
			return this;
		}

		public DataProductCredentialSubject build() {
			return new DataProductCredentialSubject(id, title, description, issued, providedBy, termsAndConditions, policy,
					dataAccountExport, aggregationOf);
		}

		@Override
		public String toString() {
			return "DataProductCredentialSubjectBuilder{" +
					"id='" + id + '\'' +
					", title='" + title + '\'' +
					", description='" + description + '\'' +
					", issued=" + issued +
					", providedBy='" + providedBy + '\'' +
					", termsAndConditions=" + termsAndConditions +
					", policy=" + policy +
					", dataAccountExport=" + dataAccountExport +
					", aggregationOf=" + aggregationOf +
					'}';
		}
	}
}
