package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.DATA_PRODUCT_VERIFIABLE_CREDENTIAL;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/DataProduct/">Gaia-X Service Characteristics : DataProduct</a>
 */
@JsonLdType({"VerifiableCredential", "gx:DataProduct"})
public class DataProductVerifiableCredential extends BaseVerifiableCredential<DataProductCredentialSubject> {

	public DataProductVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			DataProductCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<DataProductVerifiableCredential, DataProductCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public DataProductVerifiableCredential build() {
				return new DataProductVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = DATA_PRODUCT_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
