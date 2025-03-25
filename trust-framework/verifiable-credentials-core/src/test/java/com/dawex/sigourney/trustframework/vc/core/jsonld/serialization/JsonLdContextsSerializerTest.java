package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdEmbeddedContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonLdContextsSerializerTest {

	@Test
	void withBaseContextShouldSerializeJsonLd() throws JsonProcessingException {
		@JsonLdContexts(addBaseContext = true)
		class Test {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test());
		assertThat(actual).isEqualTo("""
				{
				  "@context" : {
				    "@base" : "https://dawex.com/base"
				  }
				}""");
	}

	@Test
	void withEmbeddedContextShouldSerializeJsonLd() throws JsonProcessingException {
		@JsonLdContexts(embeddedContexts = {
				@JsonLdEmbeddedContext(term = "dw", iri = "https://dawex.com/dw")
		})
		class Test {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test());
		assertThat(actual).isEqualTo("""
				{
				  "@context" : {
				    "dw" : "https://dawex.com/dw"
				  }
				}""");
	}

	@Test
	void withBaseAndEmbeddedContextShouldSerializeJsonLd() throws JsonProcessingException {
		@JsonLdContexts(
				addBaseContext = true,
				embeddedContexts = {@JsonLdEmbeddedContext(term = "dw", iri = "https://dawex.com/dw")}
		)
		class Test {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test());
		assertThat(actual).isEqualTo("""
				{
				  "@context" : {
				    "@base" : "https://dawex.com/base",
				    "dw" : "https://dawex.com/dw"
				  }
				}""");
	}

	@Test
	void withReferencedContextShouldSerializeJsonLd() throws JsonProcessingException {
		@JsonLdContexts(referencedContexts = {"https://dawex.com/ref"})
		class Test {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test());
		assertThat(actual).isEqualTo("""
				{
				  "@context" : "https://dawex.com/ref"
				}""");
	}

	@Test
	void withMultipleReferencedContextShouldSerializeJsonLd() throws JsonProcessingException {
		@JsonLdContexts(referencedContexts = {"https://dawex.com/ref", "https://dawex.com/ref2"})
		class Test {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test());
		assertThat(actual).isEqualTo("""
				{
				  "@context" : [ "https://dawex.com/ref", "https://dawex.com/ref2" ]
				}""");
	}

	@Test
	void withMultipleReferencedAndBaseContextShouldSerializeJsonLd() throws JsonProcessingException {
		@JsonLdContexts(
				addBaseContext = true,
				referencedContexts = {"https://dawex.com/ref", "https://dawex.com/ref2"})
		class Test {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test());
		assertThat(actual).isEqualTo("""
				{
				  "@context" : [ "https://dawex.com/ref", "https://dawex.com/ref2", {
				    "@base" : "https://dawex.com/base"
				  } ]
				}""");
	}

	@Test
	void withEmbeddedAndReferencedContextShouldSerializeJsonLd() throws JsonProcessingException {
		@JsonLdContexts(
				embeddedContexts = {@JsonLdEmbeddedContext(term = "dw", iri = "https://dawex.com/dw")},
				referencedContexts = {"https://dawex.com/ref", "https://dawex.com/ref2"})
		class Test {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test());
		assertThat(actual).isEqualTo("""
				{
				  "@context" : [ "https://dawex.com/ref", "https://dawex.com/ref2", {
				    "dw" : "https://dawex.com/dw"
				  } ]
				}""");
	}

	@Test
	void withBaseAndEmbeddedAndReferencedContextShouldSerializeJsonLd() throws JsonProcessingException {
		@JsonLdContexts(
				addBaseContext = true,
				embeddedContexts = {@JsonLdEmbeddedContext(term = "dw", iri = "https://dawex.com/dw")},
				referencedContexts = {"https://dawex.com/ref", "https://dawex.com/ref2"})
		class Test {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test());
		assertThat(actual).isEqualTo("""
				{
				  "@context" : [ "https://dawex.com/ref", "https://dawex.com/ref2", {
				    "@base" : "https://dawex.com/base",
				    "dw" : "https://dawex.com/dw"
				  } ]
				}""");
	}

	private static <T> ObjectMapper getObjectMapper(Class<T> testClass) {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		final var module = new SimpleModule();
		module.addSerializer(testClass, new JsonLdSerializer<>(testClass));
		module.addSerializer(JsonLdContexts.class, new JsonLdContextsSerializer(() -> "https://dawex.com/base"));
		objectMapper.registerModule(module);
		return objectMapper;
	}
}