package com.dawex.sigourney.trustframework.vc.core.jose.signature;

import com.dawex.sigourney.trustframework.vc.core.Constant;
import com.dawex.sigourney.trustframework.vc.core.jose.crypto.JwkSetUtils;
import com.nimbusds.jose.jwk.JWK;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonWebEmbeddedSignatureUtilsTest {

	@ParameterizedTest
	@MethodSource("getJwkAndCertificate")
	void shouldGenerateAndValidateSignatureUsingJwk(JWK jwk, X509Certificate certificate) {
		assertThat(JsonWebEmbeddedSignatureUtils.generateSignature(Constant.JSON_LD, jwk))
				.isNotNull()
				.satisfies(signature -> {
					assertThat(JsonWebEmbeddedSignatureUtils.isSignatureValid(signature, Constant.JSON_LD, jwk.toPublicJWK())).isTrue();
					assertThat(JsonWebEmbeddedSignatureUtils.isSignatureValid(signature, Constant.JSON_LD, certificate)).isTrue();
				});
	}

	@Test
	void withInvalidSignatureShouldReturnFalse() {
		final JwkSetUtils.CreatedKeys keys = JwkSetUtils.createKeysWithSelfSignedCertificate(JwkSetUtils.KeyAlgorithm.RSA_2048,
				null, "Test", 12);
		final JWK publicJWK = keys.jwkSet().getKeys().get(0).toPublicJWK();
		final X509Certificate certificate = publicJWK.getParsedX509CertChain().get(0);

		final String invalidSignature = "eyJhbGciOiJQUzI1NiIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..invalid-signature";

		assertThat(JsonWebEmbeddedSignatureUtils.isSignatureValid(invalidSignature, Constant.JSON_LD, publicJWK)).isFalse();
		assertThat(JsonWebEmbeddedSignatureUtils.isSignatureValid(invalidSignature, Constant.JSON_LD, certificate)).isFalse();
	}

	public static List<Arguments> getJwkAndCertificate() {
		final List<Arguments> arguments = new ArrayList<>();
		for (JwkSetUtils.KeyAlgorithm keyAlgorithm : JwkSetUtils.KeyAlgorithm.values()) {
			final JwkSetUtils.CreatedKeys keys = JwkSetUtils.createKeysWithSelfSignedCertificate(keyAlgorithm, null, "Test", 12);
			final JWK jwk = keys.jwkSet().getKeys().stream().findFirst().orElseThrow();
			final X509Certificate certificate = jwk.getParsedX509CertChain().get(0);
			arguments.add(Arguments.of(jwk, certificate));
		}
		return arguments;
	}
}
