package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.providedby;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.LinkTo;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.DATA_PRODUCT_PROVIDED_BY;

@JsonLdType("gx:DataProducer")
public class ProvidedByDataProducer extends LinkTo implements ProvidedBy {

	public ProvidedByDataProducer(String id) {
		super(id);
	}

	@JsonLdProperty(value = "id", formatName = DATA_PRODUCT_PROVIDED_BY)
	@Override
	public String getId() {
		return super.getId();
	}
}
