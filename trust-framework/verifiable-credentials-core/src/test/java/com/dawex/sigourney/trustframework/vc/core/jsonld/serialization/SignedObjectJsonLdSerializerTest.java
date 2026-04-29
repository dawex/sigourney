package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.signature.model.Proof;
import com.dawex.sigourney.trustframework.vc.core.vc.signature.model.SignedObject;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SignedObjectJsonLdSerializerTest {

	@Test
	void shouldSerialize()  {
		@JsonLdContexts(referencedContexts = {"https://dawex.com/ref"})
		@JsonLdType("MyType")
		record Test(@JsonLdProperty("property") String property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(
				new SignedObject<>(
						new Test("value"),
						Proof.builder()
								.type("JsonWebSignature2020")
								.build()));
		assertThat(actual).isEqualTo("""
				{
				  "@context" : "https://dawex.com/ref",
				  "type" : "MyType",
				  "property" : "value",
				  "proof" : {
				    "type" : "JsonWebSignature2020"
				  }
				}""");
	}

	private static <T> ObjectMapper getObjectMapper(Class<T> testClass) {
		final var module = new SimpleModule();
		module.addSerializer(testClass, new JsonLdSerializer<>(testClass));
		module.addSerializer(JsonLdContexts.class, new JsonLdContextsSerializer(() -> "https://dawex.com"));
		module.addSerializer(JsonLdType.class, new JsonLdTypeSerializer());
		module.addSerializer(Proof.class, new JsonLdSerializer<>(Proof.class, formatName -> Optional.empty()));
		module.addSerializer(SignedObject.class, new SignedObjectJsonLdSerializer(formatName -> Optional.empty()));

		return JsonMapper.builder()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.addModule(module)
				.build();
	}
}