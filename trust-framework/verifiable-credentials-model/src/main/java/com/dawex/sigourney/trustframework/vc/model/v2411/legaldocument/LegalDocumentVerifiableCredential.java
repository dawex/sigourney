package com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdEmbeddedContext;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.GAIAX_DEVELOPMENT;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS_V2;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.SCHEMA_IRI;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.SCHEMA_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.VCARD_IRI;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.VCARD_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.LEGAL_DOCUMENT_VERIFIABLE_CREDENTIAL;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/LegalDocument/">Gaia-X Service Characteristics : Legal Document</a>
 */
@JsonLdContexts(
		addBaseContext = true,
		referencedContexts = {
				VERIFIABLE_CREDENTIALS_V2,
				GAIAX_DEVELOPMENT
		},
		embeddedContexts = {
				@JsonLdEmbeddedContext(term = SCHEMA_NS, iri = SCHEMA_IRI),
				@JsonLdEmbeddedContext(term = VCARD_NS, iri = VCARD_IRI),
		})
@JsonLdType({"VerifiableCredential", "gx:LegalDocument"})
public class LegalDocumentVerifiableCredential extends BaseVerifiableCredential<LegalDocumentCredentialSubject> {

	public LegalDocumentVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			LegalDocumentCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<? extends LegalDocumentVerifiableCredential, LegalDocumentCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public LegalDocumentVerifiableCredential build() {
				return new LegalDocumentVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = LEGAL_DOCUMENT_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
