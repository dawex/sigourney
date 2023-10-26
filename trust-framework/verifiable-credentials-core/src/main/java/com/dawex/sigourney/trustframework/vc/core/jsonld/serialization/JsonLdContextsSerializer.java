package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdEmbeddedContext;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.function.Supplier;

public class JsonLdContextsSerializer extends JsonSerializer<JsonLdContexts> {

	private static final String CONTEXT_BASE = "@base";

	private Supplier<String> baseIri;

	public JsonLdContextsSerializer(Supplier<String> baseIri) {
		this.baseIri = baseIri;
	}

	@Override
	public void serialize(JsonLdContexts jsonLdContexts, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		jsonGenerator.writeStartArray();
		writeEmbeddedContexts(jsonLdContexts, jsonGenerator);
		writeReferencedContexts(jsonLdContexts, jsonGenerator);
		jsonGenerator.writeEndArray();
	}

	private void writeEmbeddedContexts(JsonLdContexts jsonLdContexts, JsonGenerator jsonGenerator) throws IOException {
		if ((!jsonLdContexts.addBaseContext() || baseIri == null) && jsonLdContexts.embeddedContexts().length == 0) {
			return;
		}

		jsonGenerator.writeStartObject();
		if (jsonLdContexts.addBaseContext() && baseIri != null) {
			jsonGenerator.writeStringField(CONTEXT_BASE, baseIri.get());
		}
		for (JsonLdEmbeddedContext embeddedContext : jsonLdContexts.embeddedContexts()) {
			jsonGenerator.writeStringField(embeddedContext.term(), embeddedContext.iri());
		}
		jsonGenerator.writeEndObject();
	}

	private static void writeReferencedContexts(JsonLdContexts jsonLdContexts, JsonGenerator jsonGenerator) throws IOException {
		for (String referencedContext : jsonLdContexts.referencedContexts()) {
			jsonGenerator.writeString(referencedContext);
		}
	}
}
