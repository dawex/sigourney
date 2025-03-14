package com.dawex.sigourney.trustframework.vc.model.v2411.physicalresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.LinkTo;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.PHYSICAL_RESOURCE_MAINTAINED_BY;

@JsonLdType("gx:LegalPerson")
public class MaintainedBy extends LinkTo {

	public MaintainedBy(String id) {
		super(id);
	}

	@JsonLdProperty(value = "id", formatName = PHYSICAL_RESOURCE_MAINTAINED_BY)
	@Override
	public String getId() {
		return super.getId();
	}
}
