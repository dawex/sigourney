package com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/CustomerDataAccessTerms/">Gaia-X Service Characteristics : CustomerDataAccessTerms</a>
 */
@JsonLdType({"VerifiableCredential", "gx:CustomerDataAccessTerms"})
public class CustomerDataAccessTermsVerifiableCredential extends LegalDocumentVerifiableCredential {

	public CustomerDataAccessTermsVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			LegalDocumentCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<CustomerDataAccessTermsVerifiableCredential, LegalDocumentCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public CustomerDataAccessTermsVerifiableCredential build() {
				return new CustomerDataAccessTermsVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}
}
