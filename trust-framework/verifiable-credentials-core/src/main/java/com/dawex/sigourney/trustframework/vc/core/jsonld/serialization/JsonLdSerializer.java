package com.dawex.sigourney.trustframework.vc.core.jsonld.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.CompositeValue;
import com.dawex.sigourney.trustframework.vc.core.jsonld.JsonLdValueObject;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.jsonld.exception.JsonLdSerializationException;
import jakarta.annotation.Nullable;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonLdSerializer<T> extends ValueSerializer<T> {

	private static final String FIELD_CONTEXT = "@context";

	private static final String FIELD_TYPE = "type";

	private static final String FIELD_NAME_FORMAT = "%s:%s";

	private final Class<T> serializableObjectClass;

	private final FormatProvider formatProvider;

	public JsonLdSerializer(Class<T> serializableObjectClass) {
		this.serializableObjectClass = serializableObjectClass;
		this.formatProvider = null;
	}

	public JsonLdSerializer(Class<T> serializableObjectClass, FormatProvider formatProvider) {
		this.serializableObjectClass = serializableObjectClass;
		this.formatProvider = formatProvider;
	}

	@Override
	public void serialize(T value, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
		jsonGenerator.writeStartObject();
		writeContext(serializableObjectClass, jsonGenerator);
		writeType(serializableObjectClass, jsonGenerator);
		writeJsonLdProperties(value, serializableObjectClass, jsonGenerator);
		jsonGenerator.writeEndObject();
	}

	protected void writeContext(Class<?> targetClass, JsonGenerator jsonGenerator) {
		final Optional<JsonLdContexts> jsonLdContextsOpt = findAnnotation(targetClass, JsonLdContexts.class);
		if (jsonLdContextsOpt.isPresent()) {
			final JsonLdContexts jsonLdContexts = jsonLdContextsOpt.get();
			// write context
			jsonGenerator.writePOJOProperty(FIELD_CONTEXT, jsonLdContexts);
		}
	}

	protected void writeType(Class<?> targetClass, JsonGenerator jsonGenerator) {
		final Optional<JsonLdType> jsonLdTypeOpt = findAnnotation(targetClass, JsonLdType.class);
		if (jsonLdTypeOpt.isPresent()) {
			final JsonLdType jsonLdType = jsonLdTypeOpt.get();
			// write type
			jsonGenerator.writePOJOProperty(FIELD_TYPE, jsonLdType);
		}
	}

	private <A extends Annotation> Optional<A> findAnnotation(Class<?> targetClass, Class<A> annotationClass) {
		if (targetClass == null || targetClass == Object.class) {
			return Optional.empty();
		}
		return Optional.ofNullable(targetClass.getAnnotation(annotationClass))
				// get annotation from parent class
				.or(() -> findAnnotation(targetClass.getSuperclass(), annotationClass))
				// get annotation from interfaces
				.or(() -> Arrays.stream(targetClass.getInterfaces())
						.map(itf -> this.findAnnotation(itf, annotationClass))
						.filter(Optional::isPresent)
						.map(Optional::get)
						.findFirst());
	}

	protected void writeJsonLdProperties(Object value, Class<?> targetClass, JsonGenerator jsonGenerator) {
		getJsonLdFields(value, targetClass).forEach(jsonLdField -> {
			try {
				if (jsonLdField.value() == null) {
					jsonGenerator.writeNullProperty(jsonLdField.property());
				} else {
					jsonGenerator.writePOJOProperty(jsonLdField.property(), jsonLdField.value());
				}
			} catch (JacksonException e) {
				throw new JsonLdSerializationException(e);
			}
		});
	}

	private Collection<JsonLdField> getJsonLdFields(Object value, Class<?> targetClass) {
		if (targetClass == null || targetClass == Object.class) {
			return Set.of();
		}
		// get parent class fields
		final var jsonLdFields = new ArrayList<>(getJsonLdFields(value, targetClass.getSuperclass()));

		// fields and methods annotated with @JsonLdProperty
		// only build from getter for record, so @JsonLdProperty are not processed twice
		(targetClass.isRecord()
				? getFieldContextsFromDeclaredGetters(targetClass)
				: Stream.concat(getFieldContextsFromDeclaredFields(targetClass), getFieldContextsFromDeclaredGetters(targetClass)))

				.filter(FieldContext::isValid)
				.map(context -> getJsonLdField(value, context.jsonLdProperty, context.readMethod()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(field -> {
					// overwrite parent value if any
					jsonLdFields.removeIf(existing -> Objects.equals(existing.property(), field.property()));
					jsonLdFields.add(field);
				});

		return jsonLdFields;
	}

	private static Stream<FieldContext> getFieldContextsFromDeclaredFields(Class<?> targetClass) {
		return Arrays.stream(targetClass.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(JsonLdProperty.class))
				.map(field -> new FieldContext(field.getAnnotation(JsonLdProperty.class), getReadMethod(targetClass, field)));
	}

	private static Stream<FieldContext> getFieldContextsFromDeclaredGetters(Class<?> targetClass) {
		return Arrays.stream(targetClass.getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(JsonLdProperty.class))
				.map(method -> new FieldContext(method.getAnnotation(JsonLdProperty.class), method));
	}

	private static Method getReadMethod(Class<?> targetClass, Field field) {
		try {
			return new PropertyDescriptor(field.getName(), targetClass, field.getName(), null).getReadMethod();
		} catch (IntrospectionException e) {
			throw new JsonLdSerializationException(e);
		}
	}

	private Optional<JsonLdField> getJsonLdField(Object obj, JsonLdProperty jsonLdProperty, Method getterMethod) {
		try {
			final Object value = getterMethod.invoke(obj);
			if (value == null) {
				if (jsonLdProperty.mandatory()) {
					if (Collection.class.isAssignableFrom(getterMethod.getReturnType())) {
						return Optional.of(new JsonLdField(getJsonFieldName(jsonLdProperty), List.of()));
					} else {
						return Optional.of(new JsonLdField(getJsonFieldName(jsonLdProperty), null));
					}
				} else {
					return Optional.empty();
				}
			}
			return Optional.of(new JsonLdField(getJsonFieldName(jsonLdProperty), getJsonFieldValue(jsonLdProperty, value)));

		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new JsonLdSerializationException(e);
		}
	}

	private String getJsonFieldName(JsonLdProperty jsonLdProperty) {
		return jsonLdProperty.namespace().isEmpty()
				? jsonLdProperty.value()
				: FIELD_NAME_FORMAT.formatted(jsonLdProperty.namespace(), jsonLdProperty.value());
	}

	private Object getJsonFieldValue(JsonLdProperty jsonLdProperty, Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Collection<?> valueColl) {
			return valueColl.stream().map(v -> getJsonFieldValue(jsonLdProperty, v)).toList();
		}

		final Object jsonFieldValue;
		if (value instanceof JsonLdValueObject<?>(String type, Object value1)) {
			return new JsonLdValueObject<>(type, getFormattedFieldValue(jsonLdProperty.formatName(), value1));
		} else {
			jsonFieldValue = getFormattedFieldValue(jsonLdProperty.formatName(), value);
			// wrap with type if any
			if (jsonLdProperty.type() != null && !jsonLdProperty.type().isEmpty()) {
				return new JsonLdValueObject<>(jsonLdProperty.type(), jsonFieldValue);
			}
			return jsonFieldValue;
		}
	}

	private Object getFormattedFieldValue(String formatName, Object value) {
		final Object jsonFieldValue;
		if (value instanceof String valueString) {
			jsonFieldValue = getFormat(formatName)
					.map(format -> format.formatted(valueString))
					.orElse(valueString);
		} else if (value instanceof CompositeValue compositeValue) {
			jsonFieldValue = getFormat(formatName)
					.map(format -> (Object) format.formatted(compositeValue.getValues()))
					.orElse(Arrays.stream(compositeValue.getValues()).map(String::valueOf).collect(Collectors.joining(",")));
		} else if (value instanceof Enum<?>) {
			jsonFieldValue = value.toString();
		} else if (value instanceof ZonedDateTime valueDateTime) {
			jsonFieldValue = valueDateTime.truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
		} else if (value instanceof OffsetDateTime valueDateTime) {
			jsonFieldValue = valueDateTime.truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
		} else if (value instanceof Instant valueInstant) {
			jsonFieldValue = valueInstant.atZone(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS)
					.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
		} else if (value instanceof LocalDateTime valueDateTime) {
			jsonFieldValue = valueDateTime.atZone(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS)
					.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
		} else if (value instanceof LocalDate valueDate) {
			jsonFieldValue = valueDate.atStartOfDay().atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_DATE);
		} else {
			jsonFieldValue = value;
		}
		return jsonFieldValue;
	}

	private Optional<String> getFormat(String formatName) {
		if (formatProvider == null || formatName == null || formatName.isEmpty()) {
			return Optional.empty();
		}
		return formatProvider.getFormat(formatName);
	}

	/**
	 * Helper record representing a pair of property and value to be written in the final Json-LD document.
	 */
	private record JsonLdField(String property, @Nullable Object value) {
	}

	/**
	 * Helper record used for serializing JSON-LD properties
	 */
	private record FieldContext(
			JsonLdProperty jsonLdProperty,
			Method readMethod
	) {
		public boolean isValid() {
			return readMethod != null;
		}
	}
}
