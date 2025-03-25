package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonLdTypeSerializerTest {

	@Test
	void withNoTypeShouldSerialize() throws JsonProcessingException {
		@JsonLdType
		class Test {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test());
		assertThat(actual).isEqualTo("""
				{
				  "type" : null
				}""");
	}

	@Test
	void withSingleTypeShouldSerialize() throws JsonProcessingException {
		@JsonLdType("MyType")
		class Test {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test());
		assertThat(actual).isEqualTo("""
				{
				  "type" : "MyType"
				}""");
	}

	@Test
	void withMultipleTypesShouldSerialize() throws JsonProcessingException {
		@JsonLdType({"MyType", "MyType2"})
		class Test {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test());
		assertThat(actual).isEqualTo("""
				{
				  "type" : [ "MyType", "MyType2" ]
				}""");
	}

	private static <T> ObjectMapper getObjectMapper(Class<T> testClass) {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		final var module = new SimpleModule();
		module.addSerializer(testClass, new JsonLdSerializer<>(testClass));
		module.addSerializer(JsonLdType.class, new JsonLdTypeSerializer());
		objectMapper.registerModule(module);
		return objectMapper;
	}
}