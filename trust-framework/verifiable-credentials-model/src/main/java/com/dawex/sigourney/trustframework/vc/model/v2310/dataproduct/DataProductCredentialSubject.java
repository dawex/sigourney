package com.dawex.sigourney.trustframework.vc.model.v2310.dataproduct;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2310.serialization.Format;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2310.Namespace.DC_TERMS_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2310.Namespace.GAIAX_TRUST_FRAMEWORK_NS;

@JsonLdType("gx:ServiceOffering")
public class DataProductCredentialSubject {
	@JsonLdProperty(value = "id", formatName = Format.DATA_PRODUCT_CREDENTIAL_SUBJECT, mandatory = true)
	private final String id;

	@JsonLdProperty(value = "title", namespace = DC_TERMS_NS, mandatory = true)
	private final String title;

	@JsonLdProperty(value = "description", namespace = DC_TERMS_NS)
	private final String description;

	@JsonLdProperty(value = "issued", namespace = DC_TERMS_NS)
	private final ZonedDateTime issued;

	@JsonLdProperty(value = "providedBy", namespace = GAIAX_TRUST_FRAMEWORK_NS, formatName = Format.DATA_PRODUCT_PROVIDED_BY, mandatory = true)
	private final String providedBy;

	@JsonLdProperty(value = "termsAndConditions", namespace = GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final Collection<String> termsAndConditions;

	@JsonLdProperty(value = "aggregationOf", namespace = GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final Collection<AggregationOf> aggregationOf;

	public DataProductCredentialSubject(String id, String title, String description, ZonedDateTime issued, String providedBy,
			Collection<String> termsAndConditions, Collection<AggregationOf> aggregationOf) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.issued = issued;
		this.providedBy = providedBy;
		this.termsAndConditions = termsAndConditions;
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

	public String getProvidedBy() {
		return providedBy;
	}

	public Collection<String> getTermsAndConditions() {
		return termsAndConditions;
	}

	public Collection<AggregationOf> getAggregationOf() {
		return aggregationOf;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		var that = (DataProductCredentialSubject) obj;
		return Objects.equals(this.id, that.id) &&
				Objects.equals(this.title, that.title) &&
				Objects.equals(this.description, that.description) &&
				Objects.equals(this.issued, that.issued) &&
				Objects.equals(this.providedBy, that.providedBy) &&
				Objects.equals(this.termsAndConditions, that.termsAndConditions) &&
				Objects.equals(this.aggregationOf, that.aggregationOf);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, description, issued, providedBy, termsAndConditions, aggregationOf);
	}

	@Override
	public String toString() {
		return "DataProductCredentialSubject[" +
				"id=" + id + ", " +
				"title=" + title + ", " +
				"description=" + description + ", " +
				"issued=" + issued + ", " +
				"providedBy=" + providedBy + ", " +
				"termsAndConditions=" + termsAndConditions + ", " +
				"aggregationOf=" + aggregationOf + ']';
	}

	public static class DataProductCredentialSubjectBuilder {
		private String id;

		private String title;

		private String description;

		private ZonedDateTime issued;

		private String providedBy;

		private Collection<String> termsAndConditions;

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

		public DataProductCredentialSubjectBuilder providedBy(String providedBy) {
			this.providedBy = providedBy;
			return this;
		}

		public DataProductCredentialSubjectBuilder termsAndConditions(Collection<String> termsAndConditions) {
			this.termsAndConditions = termsAndConditions;
			return this;
		}

		public DataProductCredentialSubjectBuilder aggregationOf(Collection<AggregationOf> aggregationOf) {
			this.aggregationOf = aggregationOf;
			return this;
		}

		public DataProductCredentialSubject build() {
			return new DataProductCredentialSubject(id, title, description, issued, providedBy, termsAndConditions, aggregationOf);
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
					", aggregationOf=" + aggregationOf +
					'}';
		}
	}
}
