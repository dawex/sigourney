package com.dawex.sigourney.trustframework.vc.model.v2210.termsandconditions;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2210.Namespace;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format;

import java.util.Objects;

@JsonLdType("gx:GaiaXTermsAndConditions")
public class TermsAndConditionsCredentialSubject {
	@JsonLdProperty(value = "id", formatName = Format.TERMS_AND_CONDITIONS_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "termsAndConditions", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final String termsAndConditions;

	public TermsAndConditionsCredentialSubject(String id, String termsAndConditions) {
		this.id = id;
		this.termsAndConditions = termsAndConditions;
	}

	public static TermsAndConditionsCredentialSubjectBuilder builder() {
		return new TermsAndConditionsCredentialSubjectBuilder();
	}

	public String getId() {
		return id;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TermsAndConditionsCredentialSubject that = (TermsAndConditionsCredentialSubject) o;
		return Objects.equals(id, that.id) && Objects.equals(termsAndConditions, that.termsAndConditions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, termsAndConditions);
	}

	@Override
	public String toString() {
		return "TermsAndConditionsCredentialSubject{" +
				"id='" + id + '\'' +
				", termsAndConditions='" + termsAndConditions + '\'' +
				'}';
	}

	public static class TermsAndConditionsCredentialSubjectBuilder {
		private String id;

		private String termsAndConditions;

		TermsAndConditionsCredentialSubjectBuilder() {
		}

		public TermsAndConditionsCredentialSubjectBuilder id(String id) {
			this.id = id;
			return this;
		}

		public TermsAndConditionsCredentialSubjectBuilder termsAndConditions(String termsAndConditions) {
			this.termsAndConditions = termsAndConditions;
			return this;
		}

		public TermsAndConditionsCredentialSubject build() {
			return new TermsAndConditionsCredentialSubject(id, termsAndConditions);
		}

		@Override
		public String toString() {
			return "TermsAndConditionsCredentialSubjectBuilder{" +
					"id='" + id + '\'' +
					", termsAndConditions='" + termsAndConditions + '\'' +
					'}';
		}
	}
}
