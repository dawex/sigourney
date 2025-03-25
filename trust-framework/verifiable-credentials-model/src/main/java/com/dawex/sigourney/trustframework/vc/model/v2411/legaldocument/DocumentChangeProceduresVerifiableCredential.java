package com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/DocumentChangeProcedures/">Gaia-X Service Characteristics : DocumentChangeProcedures</a>
 */
@JsonLdType({"VerifiableCredential", "gx:DocumentChangeProcedures"})
public class DocumentChangeProceduresVerifiableCredential extends LegalDocumentVerifiableCredential {

	public DocumentChangeProceduresVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			LegalDocumentCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<DocumentChangeProceduresVerifiableCredential, LegalDocumentCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public DocumentChangeProceduresVerifiableCredential build() {
				return new DocumentChangeProceduresVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}
}
