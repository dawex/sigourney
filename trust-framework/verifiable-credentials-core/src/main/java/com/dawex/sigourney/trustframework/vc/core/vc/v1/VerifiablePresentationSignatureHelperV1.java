package com.dawex.sigourney.trustframework.vc.core.vc.v1;

import com.dawex.sigourney.trustframework.vc.core.jose.exception.SignatureException;
import com.dawex.sigourney.trustframework.vc.core.vc.signature.ProofGenerator;
import com.dawex.sigourney.trustframework.vc.core.vc.signature.model.SignedObject;
import com.dawex.sigourney.trustframework.vc.core.vc.v1.model.VerifiablePresentation;
import com.nimbusds.jose.jwk.JWK;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;

public class VerifiablePresentationSignatureHelperV1 {

	private final ObjectMapper objectMapper;

	public VerifiablePresentationSignatureHelperV1(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public String buildAndSignVerifiablePresentation(Collection<Object> verifiableCredentials,
			Collection<Object> securedVerifiableCredentials,
			String issuer,
			JWK jwk) {
		final var allCredentials = new ArrayList<>();
		if (securedVerifiableCredentials != null) {
			allCredentials.addAll(securedVerifiableCredentials);
		}
		if (verifiableCredentials != null) {
			verifiableCredentials.stream()
					.map(vc -> signVerifiableCredential(vc, issuer, jwk))
					.forEach(allCredentials::add);
		}
		return jsonSerialize(new VerifiablePresentation(allCredentials));
	}

	public <T> SignedObject<T> signVerifiableCredential(T verifiableCredential, String issuer, JWK jwk) {
		final var proof = ProofGenerator.generateProof(
				jsonSerialize(verifiableCredential),
				issuer + "#" + jwk.getKeyID(),
				jwk);
		return new SignedObject<>(verifiableCredential, proof);
	}

	private String jsonSerialize(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JacksonException e) {
			throw new SignatureException(e);
		}
	}
}
