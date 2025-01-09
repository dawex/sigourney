package com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format;

import java.time.ZonedDateTime;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/LegalDocument/">Gaia-X Service Characteristics : Legal Document</a>
 */
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

	@JsonLdProperty(value = "id", formatName = Format.LEGAL_DOCUMENT_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
