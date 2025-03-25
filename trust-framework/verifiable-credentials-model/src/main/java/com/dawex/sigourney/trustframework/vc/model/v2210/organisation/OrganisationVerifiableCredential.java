package com.dawex.sigourney.trustframework.vc.model.v2210.organisation;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format;

import java.time.ZonedDateTime;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.GAIAX_TRUST_FRAMEWORK_V1;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.SECURITY_JWS_2020;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS_V1;

/**
 * @see <a href="https://www.w3.org/2018/credentials/v1">Verifiable Credential Schema</a>
 */
@JsonLdContexts(
		addBaseContext = true,
		referencedContexts = {
				VERIFIABLE_CREDENTIALS_V1,
				SECURITY_JWS_2020,
				GAIAX_TRUST_FRAMEWORK_V1
		})
@JsonLdType("VerifiableCredential")
public class OrganisationVerifiableCredential {
	@JsonLdProperty(value = "id", formatName = Format.ORGANISATION_VERIFIABLE_CREDENTIAL)
	private final String id;

	@JsonLdProperty(value = "issuer", formatName = Format.ORGANISATION_ISSUER)
	private final String issuer;

	@JsonLdProperty(value = "issuanceDate")
	private final ZonedDateTime issuanceDate;

	@JsonLdProperty(value = "credentialSubject")
	private final OrganisationCredentialSubject credentialSubject;

	public OrganisationVerifiableCredential(String id, String issuer, ZonedDateTime issuanceDate,
			OrganisationCredentialSubject credentialSubject) {
		this.id = id;
		this.issuer = issuer;
		this.issuanceDate = issuanceDate;
		this.credentialSubject = credentialSubject;
	}

	public static OrganisationVerifiableCredentialBuilder builder() {
		return new OrganisationVerifiableCredentialBuilder();
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

	public OrganisationCredentialSubject getCredentialSubject() {
		return credentialSubject;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		var that = (OrganisationVerifiableCredential) obj;
		return Objects.equals(this.id, that.id) &&
				Objects.equals(this.issuer, that.issuer) &&
				Objects.equals(this.issuanceDate, that.issuanceDate) &&
				Objects.equals(this.credentialSubject, that.credentialSubject);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, issuer, issuanceDate, credentialSubject);
	}

	@Override
	public String toString() {
		return "OrganisationVerifiableCredential[" +
				"id=" + id + ", " +
				"issuer=" + issuer + ", " +
				"issuanceDate=" + issuanceDate + ", " +
				"organisationCredentialSubject=" + credentialSubject + ']';
	}

	public static class OrganisationVerifiableCredentialBuilder {
		private String id;

		private String issuer;

		private ZonedDateTime issuanceDate;

		private OrganisationCredentialSubject organisationCredentialSubject;

		OrganisationVerifiableCredentialBuilder() {
		}

		public OrganisationVerifiableCredentialBuilder id(String id) {
			this.id = id;
			return this;
		}

		public OrganisationVerifiableCredentialBuilder issuer(String issuer) {
			this.issuer = issuer;
			return this;
		}

		public OrganisationVerifiableCredentialBuilder issuanceDate(ZonedDateTime issuanceDate) {
			this.issuanceDate = issuanceDate;
			return this;
		}

		public OrganisationVerifiableCredentialBuilder organisationCredentialSubject(
				OrganisationCredentialSubject organisationCredentialSubject) {
			this.organisationCredentialSubject = organisationCredentialSubject;
			return this;
		}

		public OrganisationVerifiableCredential build() {
			return new OrganisationVerifiableCredential(id, issuer, issuanceDate, organisationCredentialSubject);
		}

		@Override
		public String toString() {
			return "OrganisationVerifiableCredentialBuilder{" +
					"id='" + id + '\'' +
					", issuer='" + issuer + '\'' +
					", issuanceDate=" + issuanceDate +
					", organisationCredentialSubject=" + organisationCredentialSubject +
					'}';
		}
	}
}
