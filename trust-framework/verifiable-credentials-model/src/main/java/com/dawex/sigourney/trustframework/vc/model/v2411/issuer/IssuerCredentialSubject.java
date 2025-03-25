package com.dawex.sigourney.trustframework.vc.model.v2411.issuer;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.CredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.Namespace;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.GaiaxTermsAndConditions;

import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.ISSUER_CREDENTIAL_SUBJECT;

public class IssuerCredentialSubject implements CredentialSubject {
	@JsonLdProperty(value = "id", formatName = ISSUER_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "gaiaxTermsAndConditions", namespace = Namespace.GAIAX_NS)
	private final GaiaxTermsAndConditions gaiaxTermsAndConditions;

	public IssuerCredentialSubject(String id, GaiaxTermsAndConditions gaiaxTermsAndConditions) {
		this.id = id;
		this.gaiaxTermsAndConditions = gaiaxTermsAndConditions;
	}

	public static IssuerCredentialSubjectBuilder builder() {
		return new IssuerCredentialSubjectBuilder();
	}

	@Override
	public String getId() {
		return id;
	}

	public GaiaxTermsAndConditions getGaiaxTermsAndConditions() {
		return gaiaxTermsAndConditions;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		IssuerCredentialSubject that = (IssuerCredentialSubject) o;
		return Objects.equals(id, that.id) && gaiaxTermsAndConditions == that.gaiaxTermsAndConditions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, gaiaxTermsAndConditions);
	}

	@Override
	public String toString() {
		return "IssuerCredentialSubject{" +
				"id='" + id + '\'' +
				", gaiaxTermsAndConditions=" + gaiaxTermsAndConditions +
				'}';
	}

	public static class IssuerCredentialSubjectBuilder {
		private String id;

		private GaiaxTermsAndConditions gaiaxTermsAndConditions;

		IssuerCredentialSubjectBuilder() {
		}

		public IssuerCredentialSubjectBuilder id(String id) {
			this.id = id;
			return this;
		}

		public IssuerCredentialSubjectBuilder gaiaxTermsAndConditions(GaiaxTermsAndConditions gaiaxTermsAndConditions) {
			this.gaiaxTermsAndConditions = gaiaxTermsAndConditions;
			return this;
		}

		public IssuerCredentialSubject build() {
			return new IssuerCredentialSubject(id, gaiaxTermsAndConditions);
		}

		@Override
		public String toString() {
			return "IssuerCredentialSubjectBuilder{" +
					"id='" + id + '\'' +
					", gaiaxTermsAndConditions=" + gaiaxTermsAndConditions +
					'}';
		}
	}
}
