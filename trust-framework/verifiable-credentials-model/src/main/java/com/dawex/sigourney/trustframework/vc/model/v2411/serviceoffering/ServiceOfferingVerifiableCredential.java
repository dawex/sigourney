package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.CompositeValue;
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
	@JsonLdProperty(value = "id", formatName = SERVICE_OFFERING_VERIFIABLE_CREDENTIAL)
	private final Id compositeId;

	public ServiceOfferingVerifiableCredential(Id compositeId, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			ServiceOfferingCredentialSubject credentialSubject) {
		super(null, issuer, validFrom, validUntil, credentialSubject);
		this.compositeId = compositeId;
	}

	public static ServiceOfferingVerifiableCredentialBuilder builder() {
		return new ServiceOfferingVerifiableCredentialBuilder();
	}

	public Id getCompositeId() {
		return compositeId;
	}

	public record Id(String serviceOfferingId, String legalPersonId) implements CompositeValue {
		@Override
		public Object[] getValues() {
			return new Object[]{legalPersonId, serviceOfferingId};
		}
	}

	public static class ServiceOfferingVerifiableCredentialBuilder
			extends BaseVerifiableCredentialBuilder<ServiceOfferingVerifiableCredential, ServiceOfferingCredentialSubject> {

		private Id compositeId;

		ServiceOfferingVerifiableCredentialBuilder() {
		}

		public ServiceOfferingVerifiableCredentialBuilder compositeId(Id compositeId) {
			this.compositeId = compositeId;
			return this;
		}

		@Override
		public ServiceOfferingVerifiableCredentialBuilder id(String id) {
			this.compositeId = new Id(id, id);
			return this;
		}

		@Override
		public ServiceOfferingVerifiableCredential build() {
			return new ServiceOfferingVerifiableCredential(compositeId, issuer, validFrom, validUntil, credentialSubject);
		}
	}
}
