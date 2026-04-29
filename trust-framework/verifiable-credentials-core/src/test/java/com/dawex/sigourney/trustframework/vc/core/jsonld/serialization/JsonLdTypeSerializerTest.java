package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import static org.assertj.core.api.Assertions.assertThat;

class JsonLdTypeSerializerTest {

	@Test
	void withNoTypeShouldSerialize() {
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
	void withSingleTypeShouldSerialize()  {
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
	void withMultipleTypesShouldSerialize()  {
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
		final var module = new SimpleModule();
		module.addSerializer(testClass, new JsonLdSerializer<>(testClass));
		module.addSerializer(JsonLdType.class, new JsonLdTypeSerializer());

		return JsonMapper.builder()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.addModule(module)
				.build();
	}
}