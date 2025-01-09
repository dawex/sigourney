package com.dawex.sigourney.trustframework.vc.model.v2411.physicalresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format;

import java.time.ZonedDateTime;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/PhysicalResource/">Gaia-X Service Characteristics : Physical Resource</a>
 */
@JsonLdType({"VerifiableCredential", "gx:PhysicalResource"})
public class PhysicalResourceVerifiableCredential extends BaseVerifiableCredential<PhysicalResourceCredentialSubject> {

	public PhysicalResourceVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			PhysicalResourceCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<PhysicalResourceVerifiableCredential, PhysicalResourceCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public PhysicalResourceVerifiableCredential build() {
				return new PhysicalResourceVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = Format.PHYSICAL_RESOURCE_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
