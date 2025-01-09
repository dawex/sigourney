package com.dawex.sigourney.trustframework.vc.model.v2411.issuer;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format;

import java.time.ZonedDateTime;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/Issuer/">Gaia-X Service Characteristics : Issuer</a>
 */
@JsonLdType({"VerifiableCredential", "gx:Issuer"})
public class IssuerVerifiableCredential extends BaseVerifiableCredential<IssuerCredentialSubject> {

	public IssuerVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			IssuerCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<IssuerVerifiableCredential, IssuerCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public IssuerVerifiableCredential build() {
				return new IssuerVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = Format.ISSUER_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
