package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.SERVICE_OFFERING_VERIFIABLE_CREDENTIAL;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/ServiceOffering/">Gaia-X Service Characteristics : ServiceOffering</a>
 */
@JsonLdType({"VerifiableCredential", "gx:ServiceOffering"})
public class ServiceOfferingVerifiableCredential extends BaseVerifiableCredential<ServiceOfferingCredentialSubject> {

	public ServiceOfferingVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			ServiceOfferingCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<ServiceOfferingVerifiableCredential, ServiceOfferingCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public ServiceOfferingVerifiableCredential build() {
				return new ServiceOfferingVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = SERVICE_OFFERING_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
