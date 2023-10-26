package com.dawex.sigourney.trustframework.vc.model.v2210.termsandconditions;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format;

import java.time.ZonedDateTime;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.GAIAX_TRUST_FRAMEWORK;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.SECURITY_JWS_2020;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS;

/**
 * @see <a href="https://www.w3.org/2018/credentials/v1">Verifiable Credential Schema</a>
 */
@JsonLdContexts(
		addBaseContext = true,
		referencedContexts = {
				VERIFIABLE_CREDENTIALS,
				SECURITY_JWS_2020,
				GAIAX_TRUST_FRAMEWORK
		})
@JsonLdType("VerifiableCredential")
public class TermsAndConditionsVerifiableCredential {

	@JsonLdProperty(value = "id", formatName = Format.TERMS_AND_CONDITIONS_VERIFIABLE_CREDENTIAL)
	private final String id;

	@JsonLdProperty(value = "issuer", formatName = Format.TERMS_AND_CONDITIONS_ISSUER)
	private final String issuer;

	@JsonLdProperty(value = "issuanceDate")
	private final ZonedDateTime issuanceDate;

	@JsonLdProperty(value = "credentialSubject")
	private final TermsAndConditionsCredentialSubject termsAndConditionsCredentialSubject;

	public TermsAndConditionsVerifiableCredential(String id, String issuer, ZonedDateTime issuanceDate,
			TermsAndConditionsCredentialSubject termsAndConditionsCredentialSubject) {
		this.id = id;
		this.issuer = issuer;
		this.issuanceDate = issuanceDate;
		this.termsAndConditionsCredentialSubject = termsAndConditionsCredentialSubject;
	}

	public static TermsAndConditionsVerifiableCredentialBuilder builder() {
		return new TermsAndConditionsVerifiableCredentialBuilder();
	}

	public String getId() {
		return id;
	}

	public String getIssuer() {
		return issuer;
	}

	public ZonedDateTime getIssuanceDate() {
		return issuanceDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TermsAndConditionsVerifiableCredential that = (TermsAndConditionsVerifiableCredential) o;
		return Objects.equals(id, that.id) && Objects.equals(issuer, that.issuer) &&
				Objects.equals(issuanceDate, that.issuanceDate) &&
				Objects.equals(termsAndConditionsCredentialSubject, that.termsAndConditionsCredentialSubject);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, issuer, issuanceDate, termsAndConditionsCredentialSubject);
	}

	@Override
	public String toString() {
		return "TermsAndConditionsVerifiableCredential{" +
				"id='" + id + '\'' +
				", issuer='" + issuer + '\'' +
				", issuanceDate=" + issuanceDate +
				", termsAndConditionsCredentialSubject=" + termsAndConditionsCredentialSubject +
				'}';
	}

	public TermsAndConditionsCredentialSubject getTermsAndConditionsCredentialSubject() {
		return termsAndConditionsCredentialSubject;
	}

	public static class TermsAndConditionsVerifiableCredentialBuilder {
		private String id;

		private String issuer;

		private ZonedDateTime issuanceDate;

		private TermsAndConditionsCredentialSubject termsAndConditionsCredentialSubject;

		TermsAndConditionsVerifiableCredentialBuilder() {
		}

		public TermsAndConditionsVerifiableCredentialBuilder id(String id) {
			this.id = id;
			return this;
		}

		public TermsAndConditionsVerifiableCredentialBuilder issuer(String issuer) {
			this.issuer = issuer;
			return this;
		}

		public TermsAndConditionsVerifiableCredentialBuilder issuanceDate(ZonedDateTime issuanceDate) {
			this.issuanceDate = issuanceDate;
			return this;
		}

		public TermsAndConditionsVerifiableCredentialBuilder termsAndConditionsCredentialSubject(
				TermsAndConditionsCredentialSubject termsAndConditionsCredentialSubject) {
			this.termsAndConditionsCredentialSubject = termsAndConditionsCredentialSubject;
			return this;
		}

		public TermsAndConditionsVerifiableCredential build() {
			return new TermsAndConditionsVerifiableCredential(id, issuer, issuanceDate, termsAndConditionsCredentialSubject);
		}

		@Override
		public String toString() {
			return "TermsAndConditionsVerifiableCredentialBuilder{" +
					"id='" + id + '\'' +
					", issuer='" + issuer + '\'' +
					", issuanceDate=" + issuanceDate +
					", termsAndConditionsCredentialSubject=" + termsAndConditionsCredentialSubject +
					'}';
		}
	}
}
