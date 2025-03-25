package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class JsonLdTypeSerializer extends JsonSerializer<JsonLdType> {

	@Override
	public void serialize(JsonLdType jsonLdType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		if (jsonLdType.value().length == 0) {
			jsonGenerator.writeNull();
		} else if (jsonLdType.value().length == 1) {
			jsonGenerator.writeString(jsonLdType.value()[0]);
		} else {
			jsonGenerator.writeArray(jsonLdType.value(), 0, jsonLdType.value().length);
		}
	}
}
