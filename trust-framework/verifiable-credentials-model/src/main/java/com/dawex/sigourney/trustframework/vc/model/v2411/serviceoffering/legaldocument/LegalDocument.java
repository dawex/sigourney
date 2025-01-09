package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.LinkTo;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format;

@JsonLdType("gx:LegalDocument")
public class LegalDocument extends LinkTo {

	public LegalDocument(String id) {
		super(id);
	}

	@JsonLdProperty(value = "id", formatName = Format.SERVICE_OFFERING_LEGAL_DOCUMENT)
	@Override
	public String getId() {
		return super.getId();
	}
}
