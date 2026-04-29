package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdEmbeddedContext;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.util.function.Supplier;

public class JsonLdContextsSerializer extends ValueSerializer<JsonLdContexts> {

	private static final String CONTEXT_BASE = "@base";

	private final Supplier<String> baseIri;

	public JsonLdContextsSerializer(Supplier<String> baseIri) {
		this.baseIri = baseIri;
	}

	@Override
	public void serialize(JsonLdContexts jsonLdContexts, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
		final boolean hasEmbeddedContexts =
				(jsonLdContexts.addBaseContext() && baseIri != null) || jsonLdContexts.embeddedContexts().length > 0;
		final boolean hasMultipleContexts = ((hasEmbeddedContexts ? 1 : 0) + jsonLdContexts.referencedContexts().length) > 1;

		if (hasMultipleContexts) {
			jsonGenerator.writeStartArray();
		}
		writeReferencedContexts(jsonLdContexts, jsonGenerator);

		if (hasEmbeddedContexts) {
			writeEmbeddedContexts(jsonLdContexts, jsonGenerator);
		}
		if (hasMultipleContexts) {
			jsonGenerator.writeEndArray();
		}
	}

	private void writeEmbeddedContexts(JsonLdContexts jsonLdContexts, JsonGenerator jsonGenerator) {
		jsonGenerator.writeStartObject();
		if (jsonLdContexts.addBaseContext() && baseIri != null) {
			jsonGenerator.writeStringProperty(CONTEXT_BASE, baseIri.get());
		}
		for (JsonLdEmbeddedContext embeddedContext : jsonLdContexts.embeddedContexts()) {
			jsonGenerator.writeStringProperty(embeddedContext.term(), embeddedContext.iri());
		}
		jsonGenerator.writeEndObject();
	}

	private static void writeReferencedContexts(JsonLdContexts jsonLdContexts, JsonGenerator jsonGenerator) {
		for (String referencedContext : jsonLdContexts.referencedContexts()) {
			jsonGenerator.writeString(referencedContext);
		}
	}
}
