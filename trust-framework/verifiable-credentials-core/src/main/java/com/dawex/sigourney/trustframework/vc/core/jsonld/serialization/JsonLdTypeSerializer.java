package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class JsonLdTypeSerializer extends ValueSerializer<JsonLdType> {

	@Override
	public void serialize(JsonLdType jsonLdType, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
		if (jsonLdType.value().length == 0) {
			jsonGenerator.writeNull();
		} else if (jsonLdType.value().length == 1) {
			jsonGenerator.writeString(jsonLdType.value()[0]);
		} else {
			jsonGenerator.writeArray(jsonLdType.value(), 0, jsonLdType.value().length);
		}
	}
}
