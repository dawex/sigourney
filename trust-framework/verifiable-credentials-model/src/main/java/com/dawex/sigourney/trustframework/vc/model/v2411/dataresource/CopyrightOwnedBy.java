package com.dawex.sigourney.trustframework.vc.model.v2411.dataresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.LinkTo;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.DATA_RESOURCE_COPYRIGHT_OWNED_BY;

@JsonLdType("gx:LegalPerson")
public class CopyrightOwnedBy extends LinkTo {

	public CopyrightOwnedBy(String id) {
		super(id);
	}

	@JsonLdProperty(value = "id", formatName = DATA_RESOURCE_COPYRIGHT_OWNED_BY)
	@Override
	public String getId() {
		return super.getId();
	}
}
