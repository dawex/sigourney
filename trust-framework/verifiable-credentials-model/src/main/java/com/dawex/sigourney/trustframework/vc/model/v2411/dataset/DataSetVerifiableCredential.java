package com.dawex.sigourney.trustframework.vc.model.v2411.dataset;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.DATA_SET_VERIFIABLE_CREDENTIAL;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/DataSet/">Gaia-X Service Characteristics : DataSet</a>
 */
@JsonLdType({"VerifiableCredential", "gx:DataSet"})
public class DataSetVerifiableCredential extends BaseVerifiableCredential<DataSetCredentialSubject> {

	public DataSetVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			DataSetCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<DataSetVerifiableCredential, DataSetCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public DataSetVerifiableCredential build() {
				return new DataSetVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = DATA_SET_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
