package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.LinkTo;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.SERVICE_OFFERING_AGGREGATION_OF_RESOURCE;

@JsonLdType({"gx:PhysicalResource"})
public class AggregationOfResource extends LinkTo {

	public AggregationOfResource(String id) {
		super(id);
	}

	@JsonLdProperty(value = "id", formatName = SERVICE_OFFERING_AGGREGATION_OF_RESOURCE)
	@Override
	public String getId() {
		return super.getId();
	}
}
