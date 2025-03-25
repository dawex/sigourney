package com.dawex.sigourney.trustframework.vc.model.v2411.legalperson;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.LEGAL_PERSON_VERIFIABLE_CREDENTIAL;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/LegalPerson/">Gaia-X Service Characteristics : LegalPerson</a>
 */
@JsonLdType({"VerifiableCredential", "gx:LegalPerson"})
public class LegalPersonVerifiableCredential extends BaseVerifiableCredential<LegalPersonCredentialSubject> {

	public LegalPersonVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			LegalPersonCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<LegalPersonVerifiableCredential, LegalPersonCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public LegalPersonVerifiableCredential build() {
				return new LegalPersonVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = LEGAL_PERSON_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
