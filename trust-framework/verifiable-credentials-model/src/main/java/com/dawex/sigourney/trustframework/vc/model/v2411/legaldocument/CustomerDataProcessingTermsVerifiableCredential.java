package com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/CustomerDataProcessingTerms/">Gaia-X Service Characteristics : CustomerDataProcessingTerms</a>
 */
@JsonLdType({"VerifiableCredential", "gx:CustomerDataProcessingTerms"})
public class CustomerDataProcessingTermsVerifiableCredential extends LegalDocumentVerifiableCredential {

	public CustomerDataProcessingTermsVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			LegalDocumentCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<CustomerDataProcessingTermsVerifiableCredential, LegalDocumentCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public CustomerDataProcessingTermsVerifiableCredential build() {
				return new CustomerDataProcessingTermsVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}
}
