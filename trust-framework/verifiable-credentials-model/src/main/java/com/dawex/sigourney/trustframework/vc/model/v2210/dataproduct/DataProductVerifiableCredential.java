package com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct;

import com.dawex.sigourney.trustframework.vc.core.jsonld.CompositeValue;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdEmbeddedContext;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2210.Namespace;

import java.time.ZonedDateTime;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.GAIAX_TRUST_FRAMEWORK;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.SECURITY_JWS_2020;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS;
import static com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format.DATA_PRODUCT_ISSUER;
import static com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format.DATA_PRODUCT_VERIFIABLE_CREDENTIAL;

/**
 * @see <a href="https://www.w3.org/2018/credentials/v1">Verifiable Credential Schema</a>
 */
@JsonLdContexts(
		addBaseContext = true,
		embeddedContexts = {
				@JsonLdEmbeddedContext(term = Namespace.DC_TERMS_NS, iri = Namespace.DC_TERMS_IRI),
		},
		referencedContexts = {
				VERIFIABLE_CREDENTIALS,
				SECURITY_JWS_2020,
				GAIAX_TRUST_FRAMEWORK
		})
@JsonLdType("VerifiableCredential")
public class DataProductVerifiableCredential {
	@JsonLdProperty(value = "id", formatName = DATA_PRODUCT_VERIFIABLE_CREDENTIAL)
	private final Id id;

	@JsonLdProperty(value = "issuer", formatName = DATA_PRODUCT_ISSUER)
	private final String issuer;

	@JsonLdProperty(value = "issuanceDate")
	private final ZonedDateTime issuanceDate;

	@JsonLdProperty(value = "credentialSubject")
	private final DataProductCredentialSubject credentialSubject;

	public DataProductVerifiableCredential(Id id, String issuer, ZonedDateTime issuanceDate,
			DataProductCredentialSubject credentialSubject) {
		this.id = id;
		this.issuer = issuer;
		this.issuanceDate = issuanceDate;
		this.credentialSubject = credentialSubject;
	}

	public static DataProductVerifiableCredentialBuilder builder() {
		return new DataProductVerifiableCredentialBuilder();
	}

	public Id getId() {
		return id;
	}

	public String getIssuer() {
		return issuer;
	}

	public ZonedDateTime getIssuanceDate() {
		return issuanceDate;
	}

	public DataProductCredentialSubject getCredentialSubject() {
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
		var that = (DataProductVerifiableCredential) obj;
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
		return "DataProductVerifiableCredential[" +
				"id=" + id + ", " +
				"issuer=" + issuer + ", " +
				"issuanceDate=" + issuanceDate + ", " +
				"credentialSubject=" + credentialSubject + ']';
	}

	public static class DataProductVerifiableCredentialBuilder {
		private Id id;

		private String issuer;

		private ZonedDateTime issuanceDate;

		private DataProductCredentialSubject credentialSubject;

		DataProductVerifiableCredentialBuilder() {
		}

		public DataProductVerifiableCredentialBuilder id(Id id) {
			this.id = id;
			return this;
		}

		public DataProductVerifiableCredentialBuilder issuer(String issuer) {
			this.issuer = issuer;
			return this;
		}

		public DataProductVerifiableCredentialBuilder issuanceDate(ZonedDateTime issuanceDate) {
			this.issuanceDate = issuanceDate;
			return this;
		}

		public DataProductVerifiableCredentialBuilder credentialSubject(DataProductCredentialSubject credentialSubject) {
			this.credentialSubject = credentialSubject;
			return this;
		}

		public DataProductVerifiableCredential build() {
			return new DataProductVerifiableCredential(id, issuer, issuanceDate, credentialSubject);
		}

		@Override
		public String toString() {
			return "DataProductVerifiableCredentialBuilder{" +
					"id=" + id +
					", issuer='" + issuer + '\'' +
					", issuanceDate=" + issuanceDate +
					", credentialSubject=" + credentialSubject +
					'}';
		}
	}

	public record Id(String dataProductId, String organisationId) implements CompositeValue {
		@Override
		public Object[] getValues() {
			return new Object[]{organisationId, dataProductId};
		}
	}
}
