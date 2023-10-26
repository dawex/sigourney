package com.dawex.sigourney.trustframework.vc.model.shared;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class DidUtilsTest {
	@ParameterizedTest
	@CsvSource({
			"https://w3c-ccg.github.io,did:web:w3c-ccg.github.io",
			"https://w3c-ccg.github.io/.well-known/did.json,did:web:w3c-ccg.github.io",
			"https://w3c-ccg.github.io/user/alice,did:web:w3c-ccg.github.io:user:alice",
			"https://w3c-ccg.github.io/user/alice/did.json,did:web:w3c-ccg.github.io:user:alice",
			"https://example.com:3000/user/alice,did:web:example.com%3A3000:user:alice",
			"https://example.com:3000/user/alice/did.json,did:web:example.com%3A3000:user:alice",
			"https://w3c-ccg.github.io/user/alice#key1,did:web:w3c-ccg.github.io:user:alice#key1",
			"https://w3c-ccg.github.io/user/alice/did.json#key1,did:web:w3c-ccg.github.io:user:alice#key1",
			"http://w3c-ccg.github.io,did:web:w3c-ccg.github.io"
	})
	void shouldConvertToDidWeb(String input, String expected) {
		assertThat(DidUtils.toDidWeb(input)).isEqualTo(expected);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {
			"some value",
			"ftp://w3c-ccg.github.io"
	})
	void shouldNotConvertToDidWeb(String input) {
		assertThat(DidUtils.toDidWeb(input)).isNull();
	}
}