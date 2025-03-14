package com.dawex.sigourney.trustframework.vc.model.v2411.legalperson;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.Address;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.DATA_PRODUCER_CREDENTIAL_SUBJECT;

public class DataProducerCredentialSubject extends LegalPersonCredentialSubject {

	public DataProducerCredentialSubject(String id, String name, String description, RegistrationNumber registrationNumber,
			Address headquartersAddress, Address legalAddress) {
		super(id, name, description, registrationNumber, headquartersAddress, legalAddress);
	}

	public static LegalPersonCredentialSubjectBuilder<DataProducerCredentialSubject> builder() {
		return new LegalPersonCredentialSubjectBuilder<>() {
			@Override
			public DataProducerCredentialSubject build() {
				return new DataProducerCredentialSubject(id, name, description, registrationNumber, headquartersAddress, legalAddress);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = DATA_PRODUCER_CREDENTIAL_SUBJECT)
	@Override
	public String getId() {
		return super.getId();
	}
}
