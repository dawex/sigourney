package com.dawex.sigourney.trustframework.vc.model.v2411.legalperson;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.DATA_PRODUCER_VERIFIABLE_CREDENTIAL;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/DataProducer/">Gaia-X Service Characteristics : DataProducer</a>
 */
@JsonLdType({"VerifiableCredential", "gx:LegalPerson", "gx:DataProducer"})
public class DataProducerVerifiableCredential extends BaseVerifiableCredential<DataProducerCredentialSubject> {

	public DataProducerVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			DataProducerCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<DataProducerVerifiableCredential, DataProducerCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public DataProducerVerifiableCredential build() {
				return new DataProducerVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = DATA_PRODUCER_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
