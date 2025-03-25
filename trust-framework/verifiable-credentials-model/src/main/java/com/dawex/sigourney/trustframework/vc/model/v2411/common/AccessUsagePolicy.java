package com.dawex.sigourney.trustframework.vc.model.v2411.common;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;

import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.GAIAX_NS;

@JsonLdType("gx:AccessUsagePolicy")
public class AccessUsagePolicy {

	@JsonLdProperty(value = "policyLanguage", namespace = GAIAX_NS)
	private final PolicyLanguage policyLanguage;

	@JsonLdProperty(value = "policyDocument", namespace = GAIAX_NS)
	private final String policyDocument;

	public AccessUsagePolicy(PolicyLanguage policyLanguage, String policyDocument) {
		this.policyLanguage = policyLanguage;
		this.policyDocument = policyDocument;
	}

	public static PolicyLanguageBuilder builder() {
		return new PolicyLanguageBuilder();
	}

	public PolicyLanguage getPolicyLanguage() {
		return policyLanguage;
	}

	public String getPolicyDocument() {
		return policyDocument;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AccessUsagePolicy that = (AccessUsagePolicy) o;
		return policyLanguage == that.policyLanguage && Objects.equals(policyDocument, that.policyDocument);
	}

	@Override
	public int hashCode() {
		return Objects.hash(policyLanguage, policyDocument);
	}

	@Override
	public String toString() {
		return "AccessUsagePolicy{" +
				"policyLanguage=" + policyLanguage +
				", policyDocument='" + policyDocument + '\'' +
				'}';
	}

	public static class PolicyLanguageBuilder {
		private PolicyLanguage policyLanguage;

		private String policyDocument;

		PolicyLanguageBuilder() {
		}

		public PolicyLanguageBuilder policyLanguage(PolicyLanguage policyLanguage) {
			this.policyLanguage = policyLanguage;
			return this;
		}

		public PolicyLanguageBuilder policyDocument(String policyDocument) {
			this.policyDocument = policyDocument;
			return this;
		}

		public AccessUsagePolicy build() {
			return new AccessUsagePolicy(policyLanguage, policyDocument);
		}

		@Override
		public String toString() {
			return "PolicyLanguageBuilder{" +
					"policyLanguage=" + policyLanguage +
					", policyDocument='" + policyDocument + '\'' +
					'}';
		}
	}
}
