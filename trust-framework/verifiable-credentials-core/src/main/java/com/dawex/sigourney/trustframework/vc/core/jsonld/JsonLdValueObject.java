package com.dawex.sigourney.trustframework.vc.core.jsonld;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Helper record used for serializing typed values
 *
 * @see <a href="https://www.w3.org/TR/json-ld11/#value-objects">JSON-LD 1.1 : Value Objects</a>
 * @see <a href="https://www.w3.org/TR/xmlschema-2/#built-in-primitive-datatypes">XML Schema Part 2: Datatypes Second Edition</a>
 */
public record JsonLdValueObject<T>(
		@JsonProperty("@type") String type,
		@JsonProperty("@value") T value) {
}