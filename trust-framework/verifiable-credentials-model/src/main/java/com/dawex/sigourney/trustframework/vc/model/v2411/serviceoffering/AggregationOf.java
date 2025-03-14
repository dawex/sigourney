package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.LinkTo;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.DATA_PRODUCT_AGGREGATION_OF;

@JsonLdType("gx:DataSet")
public class AggregationOf extends LinkTo {

	public AggregationOf(String id) {
		super(id);
	}

	@JsonLdProperty(value = "id", formatName = DATA_PRODUCT_AGGREGATION_OF)
	@Override
	public String getId() {
		return super.getId();
	}
}
