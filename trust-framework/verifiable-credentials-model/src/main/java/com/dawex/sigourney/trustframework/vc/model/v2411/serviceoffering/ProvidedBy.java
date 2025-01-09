package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.LinkTo;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.SERVICE_OFFERING_PROVIDED_BY;

@JsonLdType("gx:LegalPerson")
public class ProvidedBy extends LinkTo {

	public ProvidedBy(String id) {
		super(id);
	}

	@JsonLdProperty(value = "id", formatName = SERVICE_OFFERING_PROVIDED_BY)
	@Override
	public String getId() {
		return super.getId();
	}
}
