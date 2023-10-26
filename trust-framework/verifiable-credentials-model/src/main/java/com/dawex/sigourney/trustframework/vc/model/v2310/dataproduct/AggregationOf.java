package com.dawex.sigourney.trustframework.vc.model.v2310.dataproduct;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2310.serialization.Format;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2310.Namespace.DAWEX_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2310.Namespace.DC_TERMS_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2310.Namespace.GAIAX_TRUST_FRAMEWORK_NS;

public class AggregationOf {
	@JsonLdProperty(value = "identifier", namespace = DC_TERMS_NS, mandatory = true)
	private final String id;

	@JsonLdProperty(value = "dataLicensors", namespace = GAIAX_TRUST_FRAMEWORK_NS, formatName = Format.DATA_PRODUCT_DATA_LICENSORS)
	private final List<String> dataLicensors;

	@JsonLdProperty(value = "license", namespace = DC_TERMS_NS, mandatory = true)
	private final Collection<String> licenses;

	@JsonLdProperty(value = "personalDataPolicy", namespace = DAWEX_NS)
	private final PersonalDataPolicy personalDataPolicy;

	@JsonLdProperty(value = "distribution", namespace = DC_TERMS_NS, mandatory = true)
	private final Collection<Distribution> distributions;

	public AggregationOf(String id, List<String> dataLicensors, Collection<String> licenses, PersonalDataPolicy personalDataPolicy,
			Collection<Distribution> distributions) {
		this.id = id;
		this.dataLicensors = dataLicensors;
		this.licenses = licenses;
		this.personalDataPolicy = personalDataPolicy;
		this.distributions = distributions;
	}

	public static AggregationOfBuilder builder() {
		return new AggregationOfBuilder();
	}

	public String getId() {
		return id;
	}

	public List<String> getDataLicensors() {
		return dataLicensors;
	}

	public Collection<String> getLicenses() {
		return licenses;
	}

	public PersonalDataPolicy getPersonalDataPolicy() {
		return personalDataPolicy;
	}

	public Collection<Distribution> getDistributions() {
		return distributions;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		var that = (AggregationOf) obj;
		return Objects.equals(this.id, that.id) &&
				Objects.equals(this.dataLicensors, that.dataLicensors) &&
				Objects.equals(this.licenses, that.licenses) &&
				Objects.equals(this.personalDataPolicy, that.personalDataPolicy) &&
				Objects.equals(this.distributions, that.distributions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, dataLicensors, licenses, personalDataPolicy, distributions);
	}

	@Override
	public String toString() {
		return "AggregationOf[" +
				"id=" + id + ", " +
				"dataLicensors=" + dataLicensors + ", " +
				"licenses=" + licenses + ", " +
				"personalDataPolicy=" + personalDataPolicy + ", " +
				"distributions=" + distributions + ']';
	}

	public static class AggregationOfBuilder {
		private String id;

		private List<String> dataLicensors;

		private Collection<String> licenses;

		private PersonalDataPolicy personalDataPolicy;

		private Collection<Distribution> distributions;

		AggregationOfBuilder() {
		}

		public AggregationOfBuilder id(String id) {
			this.id = id;
			return this;
		}

		public AggregationOfBuilder dataLicensors(List<String> dataLicensors) {
			this.dataLicensors = dataLicensors;
			return this;
		}

		public AggregationOfBuilder licenses(Collection<String> licenses) {
			this.licenses = licenses;
			return this;
		}

		public AggregationOfBuilder personalDataPolicy(PersonalDataPolicy personalDataPolicy) {
			this.personalDataPolicy = personalDataPolicy;
			return this;
		}

		public AggregationOfBuilder distributions(Collection<Distribution> distributions) {
			this.distributions = distributions;
			return this;
		}

		public AggregationOf build() {
			return new AggregationOf(id, dataLicensors, licenses, personalDataPolicy, distributions);
		}

		@Override
		public String toString() {
			return "AggregationOfBuilder{" +
					"id='" + id + '\'' +
					", dataLicensors='" + dataLicensors + '\'' +
					", licenses=" + licenses +
					", personalDataPolicy=" + personalDataPolicy +
					", distributions=" + distributions +
					'}';
		}
	}
}
