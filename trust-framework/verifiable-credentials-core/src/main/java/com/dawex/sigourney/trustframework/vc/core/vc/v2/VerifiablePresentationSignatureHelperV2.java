package com.dawex.sigourney.trustframework.vc.core.vc.v2;

import com.dawex.sigourney.trustframework.vc.core.jose.exception.SignatureException;
import com.dawex.sigourney.trustframework.vc.core.jose.signature.JsonWebEnvelopedSignatureUtils;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.EnvelopedVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.VerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.VerifiablePresentation;
import com.nimbusds.jose.jwk.JWK;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;

public class VerifiablePresentationSignatureHelperV2 {

	public static final String CONTENT_TYPE_VP_LD_JSON = "vp+ld+json";

	public static final String CONTENT_TYPE_VC_LD_JSON = "vc+ld+json";

	public static final String MEDIA_TYPE_VC_JWT = "application/vc+jwt";

	private final ObjectMapper objectMapper;

	public VerifiablePresentationSignatureHelperV2(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public String buildAndSignVerifiablePresentation(Collection<VerifiableCredential> verifiableCredentials,
			Collection<EnvelopedVerifiableCredential> securedVerifiableCredentials,
			String issuer,
			JWK jwk) {
		final var allCredentials = new ArrayList<EnvelopedVerifiableCredential>();
		if (securedVerifiableCredentials != null) {
			allCredentials.addAll(securedVerifiableCredentials);
		}
		if (verifiableCredentials != null) {
			verifiableCredentials.stream()
					.map(vc -> signVerifiableCredential(vc, issuer, jwk))
					.forEach(allCredentials::add);
		}
		return JsonWebEnvelopedSignatureUtils.signWithJWS(jsonSerialize(new VerifiablePresentation(allCredentials)),
				CONTENT_TYPE_VP_LD_JSON, issuer, jwk);
	}

	public EnvelopedVerifiableCredential signVerifiableCredential(VerifiableCredential vc, String issuer, JWK jwk) {
		final String securedCredential = JsonWebEnvelopedSignatureUtils.signWithJWS(
				jsonSerialize(vc), CONTENT_TYPE_VC_LD_JSON, issuer, jwk);
		return EnvelopedVerifiableCredential.builder()
				.mediaType(MEDIA_TYPE_VC_JWT)
				.securedCredential(securedCredential)
				.build();
	}

	private String jsonSerialize(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JacksonException e) {
			throw new SignatureException(e);
		}
	}
}
