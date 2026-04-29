package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.vc.signature.model.SignedObject;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

public class SignedObjectJsonLdSerializer extends JsonLdSerializer<SignedObject> {

	public SignedObjectJsonLdSerializer(FormatProvider formatProvider) {
		super(SignedObject.class, formatProvider);
	}

	@Override
	public void serialize(SignedObject value, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
		final Class<?> serializablePayloadClass = value.payload().getClass();
		jsonGenerator.writeStartObject();
		writeContext(serializablePayloadClass, jsonGenerator);
		writeType(serializablePayloadClass, jsonGenerator);
		writeJsonLdProperties(value.payload(), serializablePayloadClass, jsonGenerator);
		writeJsonLdProperties(value, SignedObject.class, jsonGenerator);
		jsonGenerator.writeEndObject();
	}
}
