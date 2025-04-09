package com.dawex.sigourney.trustframework.vc.core.vc.signature;

import com.dawex.sigourney.trustframework.vc.core.Constant;
import com.dawex.sigourney.trustframework.vc.core.jose.signature.JsonWebEmbeddedSignatureUtils;
import com.dawex.sigourney.trustframework.vc.core.jose.crypto.JwkSetUtils;
import com.nimbusds.jose.jwk.JWK;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProofGeneratorTest {

	private static JWK jwk;

	@BeforeAll
	static void init() {
		jwk = JwkSetUtils.createKeysWithSelfSignedCertificate(JwkSetUtils.KeyAlgorithm.RSA_2048,
						null, "Test", 12)
				.jwkSet().getKeys().stream().findFirst().orElseThrow();
	}

	@Test
	void shouldGenerateProof() {
		final var actual = ProofGenerator.generateProof(Constant.JSON_LD, "verificationMethod", jwk);

		assertThat(actual).isNotNull();
		assertThat(actual.type()).isEqualTo("JsonWebSignature2020");
		assertThat(actual.created()).isNotNull();
		assertThat(actual.proofPurpose()).isEqualTo("assertionMethod");
		assertThat(actual.verificationMethod()).isEqualTo("verificationMethod");
		assertThat(actual.jws()).matches(jws -> JsonWebEmbeddedSignatureUtils.isSignatureValid(jws, Constant.JSON_LD, jwk.toPublicJWK()));
	}
}