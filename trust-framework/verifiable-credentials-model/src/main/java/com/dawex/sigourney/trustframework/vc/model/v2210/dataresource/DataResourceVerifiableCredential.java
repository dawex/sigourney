package com.dawex.sigourney.trustframework.vc.model.v2210.dataresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdEmbeddedContext;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2210.Namespace;

import java.time.ZonedDateTime;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.GAIAX_TRUST_FRAMEWORK_V1;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.SECURITY_JWS_2020;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS_V1;
import static com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format.DATA_RESOURCE_ISSUER;
import static com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format.DATA_RESOURCE_VERIFIABLE_CREDENTIAL;

@JsonLdContexts(
		addBaseContext = true,
		embeddedContexts = {
				@JsonLdEmbeddedContext(term = Namespace.DAWEX_NS, iri = Namespace.DAWEX_IRI)
		},
		referencedContexts = {
				VERIFIABLE_CREDENTIALS_V1,
				SECURITY_JWS_2020,
				GAIAX_TRUST_FRAMEWORK_V1
		})
@JsonLdType("VerifiableCredential")
public class DataResourceVerifiableCredential {
	@JsonLdProperty(value = "id", formatName = DATA_RESOURCE_VERIFIABLE_CREDENTIAL)
	private final String id;

	@JsonLdProperty(value = "issuer", formatName = DATA_RESOURCE_ISSUER)
	private final String issuer;

	@JsonLdProperty(value = "issuanceDate")
	private final ZonedDateTime issuanceDate;

	@JsonLdProperty(value = "credentialSubject")
	private final DataResourceCredentialSubject credentialSubject;

	public DataResourceVerifiableCredential(String id, String issuer, ZonedDateTime issuanceDate,
			DataResourceCredentialSubject credentialSubject) {
		this.id = id;
		this.issuer = issuer;
		this.issuanceDate = issuanceDate;
		this.credentialSubject = credentialSubject;
	}

	public static DataResourceVerifiableCredentialBuilder builder() {
		return new DataResourceVerifiableCredentialBuilder();
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

	public DataResourceCredentialSubject getCredentialSubject() {
		return credentialSubject;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DataResourceVerifiableCredential that = (DataResourceVerifiableCredential) o;
		return Objects.equals(id, that.id) && Objects.equals(issuer, that.issuer) &&
				Objects.equals(issuanceDate, that.issuanceDate) &&
				Objects.equals(credentialSubject, that.credentialSubject);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, issuer, issuanceDate, credentialSubject);
	}

	@Override
	public String toString() {
		return "DataResourceVerifiableCredential{" +
				"id='" + id + '\'' +
				", issuer='" + issuer + '\'' +
				", issuanceDate=" + issuanceDate +
				", dataResourceCredentialSubject=" + credentialSubject +
				'}';
	}

	public static class DataResourceVerifiableCredentialBuilder {
		private String id;

		private String issuer;

		private ZonedDateTime issuanceDate;

		private DataResourceCredentialSubject dataResourceCredentialSubject;

		DataResourceVerifiableCredentialBuilder() {
		}

		public DataResourceVerifiableCredentialBuilder id(String id) {
			this.id = id;
			return this;
		}

		public DataResourceVerifiableCredentialBuilder issuer(String issuer) {
			this.issuer = issuer;
			return this;
		}

		public DataResourceVerifiableCredentialBuilder issuanceDate(ZonedDateTime issuanceDate) {
			this.issuanceDate = issuanceDate;
			return this;
		}

		public DataResourceVerifiableCredentialBuilder dataResourceCredentialSubject(
				DataResourceCredentialSubject dataResourceCredentialSubject) {
			this.dataResourceCredentialSubject = dataResourceCredentialSubject;
			return this;
		}

		public DataResourceVerifiableCredential build() {
			return new DataResourceVerifiableCredential(id, issuer, issuanceDate, dataResourceCredentialSubject);
		}

		@Override
		public String toString() {
			return "DataResourceVerifiableCredentialBuilder{" +
					"id='" + id + '\'' +
					", issuer='" + issuer + '\'' +
					", issuanceDate=" + issuanceDate +
					", dataResourceCredentialSubject=" + dataResourceCredentialSubject +
					'}';
		}
	}
}
