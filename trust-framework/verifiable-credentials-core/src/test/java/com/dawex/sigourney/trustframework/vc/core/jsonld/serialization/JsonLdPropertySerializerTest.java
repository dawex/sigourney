package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.CompositeValue;
import com.dawex.sigourney.trustframework.vc.core.jsonld.JsonLdValueObject;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JsonLdPropertySerializerTest {

	@Test
	void withNullPropertyShouldSerialize()  {
		record Test(@JsonLdProperty("property") String property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test(null));
		assertThat(actual).isEqualTo("{ }");
	}

	@Test
	void withAnnotatedAndNotAnnotatedPropertyShouldSerialize()  {
		record Test(@JsonLdProperty("property") String property, String otherProperty) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test("value", "other"));
		assertThat(actual).isEqualTo("""
				{
				  "property" : "value"
				}""");
	}

	@Test
	void withNamespaceShouldSerialize()  {
		record Test(@JsonLdProperty(value = "property", namespace = "dw") String property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test("value"));
		assertThat(actual).isEqualTo("""
				{
				  "dw:property" : "value"
				}""");
	}

	@Test
	void withFormatShouldSerialize()  {
		record Test(@JsonLdProperty(value = "property", formatName = "MY_FORMAT") String property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test("value"));
		assertThat(actual).isEqualTo("""
				{
				  "property" : "/test/MY_FORMAT/value"
				}""");
	}

	@Test
	void withMandatoryShouldSerialize()  {
		record Test(@JsonLdProperty(value = "property", mandatory = true) String property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		String actual = objectMapper.writeValueAsString(new Test("value"));
		assertThat(actual).isEqualTo("""
				{
				  "property" : "value"
				}""");

		actual = objectMapper.writeValueAsString(new Test(null));
		assertThat(actual).isEqualTo("""
				{
				  "property" : null
				}""");
	}

	@Test
	void withMandatoryCollectionShouldSerialize()  {
		record Test(@JsonLdProperty(value = "property", mandatory = true) Collection<String> property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		String actual = objectMapper.writeValueAsString(new Test(List.of("value")));
		assertThat(actual).isEqualTo("""
				{
				  "property" : [ "value" ]
				}""");

		actual = objectMapper.writeValueAsString(new Test(null));
		assertThat(actual).isEqualTo("""
				{
				  "property" : [ ]
				}""");
	}

	@Test
	void withCollectionPropertyShouldSerialize()  {
		record Test(@JsonLdProperty("property") Collection<String> property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test(List.of("value1", "value2")));
		assertThat(actual).isEqualTo("""
				{
				  "property" : [ "value1", "value2" ]
				}""");
	}

	@Test
	void withCompositePropertyShouldSerialize()  {
		class Property implements CompositeValue {

			private String property;

			public Property(String property) {
				this.property = property;
			}

			@Override
			public Object[] getValues() {
				return new Object[]{property};
			}
		}
		record Test(@JsonLdProperty(value = "property", formatName = "MY_FORMAT") Property property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test(new Property("value")));
		assertThat(actual).isEqualTo("""
				{
				  "property" : "/test/MY_FORMAT/value"
				}""");
	}

	@Test
	void withEnumPropertyShouldSerialize()  {
		enum TestEnum {
			VAL1("val1");

			private final String value;

			TestEnum(String value) {
				this.value = value;
			}

			@Override
			public String toString() {
				return this.value;
			}
		}
		record Test(@JsonLdProperty("property") TestEnum property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test(TestEnum.VAL1));
		assertThat(actual).isEqualTo("""
				{
				  "property" : "val1"
				}""");
	}

	@Test
	void withZonedDateTimePropertyShouldSerialize()  {
		record Test(@JsonLdProperty("property") ZonedDateTime property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test(LocalDate.of(2025, 2, 27).atStartOfDay(ZoneId.of("UTC"))));
		assertThat(actual).isEqualTo("""
				{
				  "property" : "2025-02-27T00:00:00Z[UTC]"
				}""");
	}

	@Test
	void withOffsetDateTimePropertyShouldSerialize()  {
		record Test(@JsonLdProperty("property") OffsetDateTime property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test(LocalDate.of(2025, 2, 27).atStartOfDay().atOffset(ZoneOffset.UTC)));
		assertThat(actual).isEqualTo("""
				{
				  "property" : "2025-02-27T00:00:00Z"
				}""");
	}

	@Test
	void withInstantPropertyShouldSerialize()  {
		record Test(@JsonLdProperty("property") Instant property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(
				new Test(LocalDate.of(2025, 2, 27).atStartOfDay(ZoneId.of("UTC")).toInstant()));
		assertThat(actual).isEqualTo("""
				{
				  "property" : "2025-02-27T00:00:00Z[UTC]"
				}""");
	}

	@Test
	void withLocalDateTimePropertyShouldSerialize()  {
		record Test(@JsonLdProperty("property") LocalDateTime property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test(LocalDate.of(2025, 2, 27).atStartOfDay()));
		assertThat(actual).isEqualTo("""
				{
				  "property" : "2025-02-27T00:00:00Z[UTC]"
				}""");
	}

	@Test
	void withLocalDatePropertyShouldSerialize()  {
		record Test(@JsonLdProperty("property") LocalDate property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test(LocalDate.of(2025, 2, 27)));
		assertThat(actual).isEqualTo("""
				{
				  "property" : "2025-02-27Z"
				}""");
	}

	@Test
	void withInheritanceShouldSerialize()  {
		class ParentTest {
			@JsonLdProperty("parent")
			private final String parentProperty;

			@JsonLdProperty("child")
			private final String childProperty;

			private final String parentOther;

			public ParentTest(String parentProperty, String childProperty, String parentOther) {
				this.parentProperty = parentProperty;
				this.childProperty = childProperty;
				this.parentOther = parentOther;
			}

			public String getParentProperty() {
				return parentProperty;
			}

			public String getChildProperty() {
				return childProperty;
			}

			public String getParentOther() {
				return parentOther;
			}
		}
		class Test extends ParentTest {
			@JsonLdProperty("child")
			private final String property;

			private final String otherProperty;

			public Test(String parentProperty, String parentOther, String property, String otherProperty) {
				super(parentProperty, "should-not-be-serialized", parentOther);
				this.property = property;
				this.otherProperty = otherProperty;
			}

			public String getProperty() {
				return property;
			}

			public String getOtherProperty() {
				return otherProperty;
			}
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test("parentValue", "parentOther", "value", "other"));
		assertThat(actual).isEqualTo("""
				{
				  "parent" : "parentValue",
				  "child" : "value"
				}""");
	}

	@Test
	void withTypedPropertyShouldSerialize()  {
		record Test(@JsonLdProperty(value = "property", type = "xsd:url") String property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test("value"));
		assertThat(actual).isEqualTo("""
				{
				  "property" : {
				    "@type" : "xsd:url",
				    "@value" : "value"
				  }
				}""");
	}

	@Test
	void withJsonLdValueObjectPropertyShouldSerialize()  {
		record Test(@JsonLdProperty(value = "property") JsonLdValueObject<String> property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test(new JsonLdValueObject<>("xsd:url", "value")));
		assertThat(actual).isEqualTo("""
				{
				  "property" : {
				    "@type" : "xsd:url",
				    "@value" : "value"
				  }
				}""");
	}

	@Test
	void withCollectionOfJsonLdValueObjectPropertyShouldSerialize()  {
		record Test(@JsonLdProperty(value = "property") Collection<JsonLdValueObject<String>> property) {
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test(List.of(
				new JsonLdValueObject<>("xsd:url", "value1"),
				new JsonLdValueObject<>("xsd:string", "value2"))
		));
		assertThat(actual).isEqualTo("""
				{
				  "property" : [ {
				    "@type" : "xsd:url",
				    "@value" : "value1"
				  }, {
				    "@type" : "xsd:string",
				    "@value" : "value2"
				  } ]
				}""");
	}

	@Test
	void withGetterConfigurationShouldSerialize()  {
		class ParentTest {
			private final String parentProperty;

			private final String parentOther;

			public ParentTest(String parentProperty, String parentOther) {
				this.parentProperty = parentProperty;
				this.parentOther = parentOther;
			}

			@JsonLdProperty("parent")
			public String getParentProperty() {
				return parentProperty;
			}

			public String getParentOther() {
				return parentOther;
			}
		}
		class Test extends ParentTest {
			private final String property;

			private final String otherProperty;

			public Test(String parentProperty, String parentOther, String property, String otherProperty) {
				super(parentProperty, parentOther);
				this.property = property;
				this.otherProperty = otherProperty;
			}

			@JsonLdProperty("child")
			public String getProperty() {
				return property;
			}

			public String getOtherProperty() {
				return otherProperty;
			}
		}
		final ObjectMapper objectMapper = getObjectMapper(Test.class);

		final String actual = objectMapper.writeValueAsString(new Test("parentValue", "parentOther", "value", "other"));
		assertThat(actual).isEqualTo("""
				{
				  "parent" : "parentValue",
				  "child" : "value"
				}""");
	}

	private static <T> ObjectMapper getObjectMapper(Class<T> testClass) {
		final var module = new SimpleModule();
		module.addSerializer(testClass, new JsonLdSerializer<>(testClass, format -> Optional.of("/test/" + format + "/%s")));

		return JsonMapper.builder()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.addModule(module)
				.build();
	}
}