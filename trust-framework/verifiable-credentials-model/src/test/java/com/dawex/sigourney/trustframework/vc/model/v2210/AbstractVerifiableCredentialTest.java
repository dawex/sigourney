package com.dawex.sigourney.trustframework.vc.model.v2210;

import com.dawex.sigourney.trustframework.vc.core.ProofGenerator;
import com.dawex.sigourney.trustframework.vc.core.SignedObject;
import com.dawex.sigourney.trustframework.vc.core.jose.JwkSetUtils;
import com.dawex.sigourney.trustframework.vc.model.utils.ProofSignatureExpectationsHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonStringValue;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractVerifiableCredentialTest {

	private static final JwkSetUtils.CreatedKeys CREATED_KEYS = JwkSetUtils.createKeysWithSelfSignedCertificate(null, "Test", 12);

	private final static com.nimbusds.jose.jwk.JWK JWK = CREATED_KEYS.jwkSet().getKeys().stream().findFirst().orElseThrow();

	private static final String PROOF_VERIFICATION_METHOD = "did:web:dawex.com:api:credentials#ded0da80-ef24-41ea-8824-34d082fb5dfb";

	private final ObjectMapper objectMapper = getObjectMapper();

	protected abstract ObjectMapper getObjectMapper();

	protected String serializeVc(Object verifiableCredential) throws JsonProcessingException {
		final var proof = ProofGenerator.generateProof(
				objectMapper.writeValueAsString(verifiableCredential),
				PROOF_VERIFICATION_METHOD,
				JWK);
		final var signedVc = new SignedObject<>(verifiableCredential, proof);
		return objectMapper.writeValueAsString(signedVc);
	}

	protected void assertThatProofIsValid(String serializedVc) {
		assertThatJsonStringValue("$['proof']['type']", serializedVc).isEqualTo("JsonWebSignature2020");
		assertThat(JsonPath.compile("$['proof']['created']")).isNotNull();
		assertThatJsonStringValue("$['proof']['proofPurpose']", serializedVc).isEqualTo("assertionMethod");
		assertThatJsonStringValue("$['proof']['verificationMethod']", serializedVc).isEqualTo(PROOF_VERIFICATION_METHOD);
		new ProofSignatureExpectationsHelper(CREATED_KEYS.jwkSet(), CREATED_KEYS.certificates()).assertSignatureIsValid(serializedVc);
	}
}
