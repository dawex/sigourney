package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdEmbeddedContext;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import static org.assertj.core.api.Assertions.assertThat;

class JsonLdContextsSerializerTest {

	@Test
	void withBaseContextShouldSerializeJsonLd()  {
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
	void withEmbeddedContextShouldSerializeJsonLd()  {
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
	void withBaseAndEmbeddedContextShouldSerializeJsonLd()  {
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
	void withReferencedContextShouldSerializeJsonLd()  {
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
	void withMultipleReferencedContextShouldSerializeJsonLd()  {
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
	void withMultipleReferencedAndBaseContextShouldSerializeJsonLd()  {
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
	void withEmbeddedAndReferencedContextShouldSerializeJsonLd()  {
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
	void withBaseAndEmbeddedAndReferencedContextShouldSerializeJsonLd()  {
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
		final var module = new SimpleModule();
		module.addSerializer(testClass, new JsonLdSerializer<>(testClass));
		module.addSerializer(JsonLdContexts.class, new JsonLdContextsSerializer(() -> "https://dawex.com/base"));

		return JsonMapper.builder()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.addModule(module)
				.build();
	}
}