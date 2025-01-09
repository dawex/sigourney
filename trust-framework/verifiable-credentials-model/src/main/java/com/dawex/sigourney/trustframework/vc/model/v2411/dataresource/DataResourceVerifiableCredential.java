package com.dawex.sigourney.trustframework.vc.model.v2411.dataresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.DATA_RESOURCE_VERIFIABLE_CREDENTIAL;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/DataResource/">Gaia-X Service Characteristics : DataResource</a>
 */
@JsonLdType({"VerifiableCredential", "gx:DataResource"})
public class DataResourceVerifiableCredential extends BaseVerifiableCredential<DataResourceCredentialSubject> {

	public DataResourceVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			DataResourceCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<DataResourceVerifiableCredential, DataResourceCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public DataResourceVerifiableCredential build() {
				return new DataResourceVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = DATA_RESOURCE_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
