package com.dawex.sigourney.trustframework.vc.core.jose.signature;

import com.dawex.sigourney.trustframework.vc.core.Constant;
import com.dawex.sigourney.trustframework.vc.core.jose.crypto.JwkSetUtils;
import com.dawex.sigourney.trustframework.vc.core.jose.exception.SignatureException;
import com.nimbusds.jose.HeaderParameterNames;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.jwk.JWK;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonWebEnvelopedSignatureUtilsTest {

	@ParameterizedTest
	@MethodSource("getJwkAndCertificate")
	void shouldSignWithJWSAndVerifySignatureUsingJwk(JwkSetUtils.KeyAlgorithm keyAlgorithm, JWK jwk, X509Certificate certificate) {
		assertThat(JsonWebEnvelopedSignatureUtils.signWithJWS(Constant.JSON_LD, "txt", "did:web:dawex:com", jwk))
				.isNotNull()
				.satisfies(securedCredential -> {
					assertThat(JsonWebEnvelopedSignatureUtils.verifyJWS(securedCredential, jwk)).isNotNull();
					assertThat(JsonWebEnvelopedSignatureUtils.verifyJWS(securedCredential, certificate)).isNotNull();
					assertThatJwsHeadersAreValid(securedCredential, keyAlgorithm, jwk);
				});
	}

	@Test
	void withInvalidSignatureShouldReturnFalse() {
		final JwkSetUtils.CreatedKeys keys = JwkSetUtils.createKeysWithSelfSignedCertificate(JwkSetUtils.KeyAlgorithm.RSA_2048,
				null, "Test", 12);
		final JWK publicJWK = keys.jwkSet().getKeys().get(0).toPublicJWK();
		final X509Certificate certificate = publicJWK.getParsedX509CertChain().get(0);

		final String invalidSignature = "eyJpc3MiOiJkaWQ6d2ViOmRhd2V4OmNvbSIsImN0eSI6InR4dCIsInR5cCI6InR4dCtqd3QiLCJhbGciOiJFUzI1NiIsImtpZCI6ImRpZDp3ZWI6ZGF3ZXg6Y29tI2M0NzU5NjFlLWYxMTktNDk0MS04YWQ3LWUzNDMwZjM0NDA3ZSJ9.ewogICAgIkBjb250ZXh0IjogWwogICAgewogICAgICAiQGJhc2UiOiAiaHR0cHM6Ly9kd3gtMTMwNzEucGxhdGZvcm0uZGV2LmNvcnAuZGF3ZXgubmV0IgogICAgfSwKICAgICAgImh0dHBzOi8vd3d3LnczLm9yZy8yMDE4L2NyZWRlbnRpYWxzL3YxIiwKICAgICAgImh0dHBzOi8vdzNpZC5vcmcvc2VjdXJpdHkvc3VpdGVzL2p3cy0yMDIwL3YxIiwKICAgICAgImh0dHBzOi8vcmVnaXN0cnkubGFiLmdhaWEteC5ldS9kZXZlbG9wbWVudC9hcGkvdHJ1c3RlZC1zaGFwZS1yZWdpc3RyeS92MS9zaGFwZXMvanNvbmxkL3RydXN0ZnJhbWV3b3JrIyIKICBdLAogICJ0eXBlIiA6ICJWZXJpZmlhYmxlQ3JlZGVudGlhbCIsCiAgIkBpZCIgOiAiLi9hcGkvc2VjdXJlL3BhcnRpY2lwYW50L29yZ2FuaXNhdGlvbnMvNjJiNTczZGViMzNlNDE3ZWRjYjM0LWlkL3ZlcmlmaWFibGVDcmVkZW50aWFsIiwKICAiaXNzdWVyIiA6ICIuL29yZ2FuaXNhdGlvbnMvNjJiNTczZGViMzNlNDE3ZWQtaXNzdWVyIiwKICAiaXNzdWFuY2VEYXRlIiA6ICIyMDIyLTA3LTI4VDE1OjE2OjAxWiIsCiAgImNyZWRlbnRpYWxTdWJqZWN0IiA6IHsKICAgICJ0eXBlIjogImd4OkxlZ2FsUGFydGljaXBhbnQiLAogICAgImlkIiA6ICIuL29yZ2FuaXNhdGlvbnMvNjJiNTczZGViMzNlNDE3ZS1jb21wYW55IiwKICAgICJneDpuYW1lIiA6ICJNZXJjYXQgZGUgbGEgQm9xdWVyaWEiLAogICAgImd4OnJlZ2lzdHJhdGlvbk51bWJlciIgOiAiQUItMTIzNC1ZWiIsCiAgICAiZ3g6aGVhZHF1YXJ0ZXJBZGRyZXNzIiA6IHsKICAgICAgImd4OnN0cmVldC1hZGRyZXNzIiA6ICJMYSBSYW1ibGEsIDkxIiwKICAgICAgImd4OnBvc3RhbC1jb2RlIiA6ICIwODAwMSIsCiAgICAgICJneDpyZWdpb24iIDogIkNhdGFsdcOxYSIsCiAgICAgICJneDpsb2NhbGl0eSIgOiAiQmFyY2Vsb25hIiwKICAgICAgImd4OmNvdW50cnktbmFtZSIgOiAiRVNQIgogICAgfSwKICAgICJneDpsZWdhbEFkZHJlc3MiIDogewogICAgICAiZ3g6c3RyZWV0LWFkZHJlc3MiIDogIjcgcnVlIEdyZW5ldHRlIiwKICAgICAgImd4OnBvc3RhbC1jb2RlIiA6ICI3NDAwMCIsCiAgICAgICJneDpyZWdpb24iIDogIlNhdm9pZSIsCiAgICAgICJneDpsb2NhbGl0eSIgOiAiQW5uZWN5IiwKICAgICAgImd4OmNvdW50cnktbmFtZSIgOiAiRlJBIgogICAgfQogIH0KfQ.fsdzEpVCsTBcQBG9KKN0N5OMN2REuPl8-aKoMI6MtgHYB6EpPkjgNO3zX9Zll9ww7YKJd8MCowfvy-bXO_1TXQ";

		assertThatThrownBy(() -> JsonWebEnvelopedSignatureUtils.verifyJWS(invalidSignature, publicJWK))
				.isInstanceOf(SignatureException.class);
		assertThatThrownBy(() -> JsonWebEnvelopedSignatureUtils.verifyJWS(invalidSignature, certificate))
				.isInstanceOf(SignatureException.class);
	}

	private void assertThatJwsHeadersAreValid(String securedCredential, JwkSetUtils.KeyAlgorithm keyAlgorithm, JWK jwk)
			throws ParseException {
		final JWSHeader header = JWSObject.parse(securedCredential).getHeader();
		assertThat(header.getAlgorithm()).isEqualTo(getJwsAlgorithm(keyAlgorithm));
		assertThat(header.getType()).isNotNull().extracting(JOSEObjectType::getType).isEqualTo("txt+jwt");
		assertThat(header.getContentType()).isEqualTo("txt");
		assertThat(header.getKeyID()).isEqualTo("did:web:dawex:com#" + jwk.getKeyID());
		assertThat(header.getCustomParam(HeaderParameterNames.ISSUER)).isEqualTo("did:web:dawex:com");
	}

	private JWSAlgorithm getJwsAlgorithm(JwkSetUtils.KeyAlgorithm keyAlgorithm) {
		return (switch (keyAlgorithm) {
			case P_256 -> JWSAlgorithm.ES256;
			case P_384 -> JWSAlgorithm.ES384;
			case P_521 -> JWSAlgorithm.ES512;
			case RSA_2048 -> JWSAlgorithm.PS256;
			case RSA_3072 -> JWSAlgorithm.PS384;
			case RSA_4096 -> JWSAlgorithm.PS512;
		});
	}

	public static List<Arguments> getJwkAndCertificate() {
		final List<Arguments> arguments = new ArrayList<>();
		for (JwkSetUtils.KeyAlgorithm keyAlgorithm : JwkSetUtils.KeyAlgorithm.values()) {
			final JwkSetUtils.CreatedKeys keys = JwkSetUtils.createKeysWithSelfSignedCertificate(keyAlgorithm, null, "Test", 12);
			final JWK jwk = keys.jwkSet().getKeys().stream().findFirst().orElseThrow();
			final X509Certificate certificate = jwk.getParsedX509CertChain().get(0);
			arguments.add(Arguments.of(keyAlgorithm, jwk, certificate));
		}
		return arguments;
	}
}