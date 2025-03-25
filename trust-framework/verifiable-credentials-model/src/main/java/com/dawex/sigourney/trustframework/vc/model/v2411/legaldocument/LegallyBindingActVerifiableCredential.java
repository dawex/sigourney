package com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/LegallyBindingAct/">Gaia-X Service Characteristics : LegallyBindingAct</a>
 */
@JsonLdType({"VerifiableCredential", "gx:LegallyBindingAct"})
public class LegallyBindingActVerifiableCredential extends LegalDocumentVerifiableCredential {

	public LegallyBindingActVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			LegalDocumentCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<LegallyBindingActVerifiableCredential, LegalDocumentCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public LegallyBindingActVerifiableCredential build() {
				return new LegallyBindingActVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}
}
