package com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.LinkTo;

import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.SERVICE_OFFERING_INVOLVED_PARTIES;

@JsonLdType("gx:LegalPerson")
public class InvolvedParty extends LinkTo {

	public InvolvedParty(String id) {
		super(id);
	}

	@JsonLdProperty(value = "id", formatName = SERVICE_OFFERING_INVOLVED_PARTIES)
	@Override
	public String getId() {
		return super.getId();
	}
}
