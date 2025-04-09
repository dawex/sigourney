package com.dawex.sigourney.trustframework.vc.core.jose.signature;

import com.dawex.sigourney.trustframework.vc.core.jose.crypto.EcUtils;
import com.dawex.sigourney.trustframework.vc.core.jose.crypto.RsaUtils;
import com.dawex.sigourney.trustframework.vc.core.jose.exception.SignatureException;
import com.dawex.sigourney.trustframework.vc.core.jose.exception.UnsupportedSignatureAlgorithmException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyType;
import com.nimbusds.jose.jwk.RSAKey;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;

public class JwsUtils {

	JwsUtils() {
		// no instance allowed
	}

	public static JWSAlgorithm getJwsAlgorithm(JWK jwk) {
		if (jwk.getAlgorithm() == null) {
			throw new SignatureException("Missing algorithm in JWK");
		}
		if (jwk.getAlgorithm() instanceof JWSAlgorithm jwsAlgorithm) {
			return jwsAlgorithm;
		}
		return JWSAlgorithm.parse(jwk.getAlgorithm().getName());
	}

	public static JWSSigner getJWSSigner(JWK jwk) throws JOSEException {
		if (Objects.equals(jwk.getKeyType().getValue(), KeyType.EC.getValue())) {
			return new ECDSASigner((ECKey) jwk);
		}
		if (Objects.equals(jwk.getKeyType().getValue(), KeyType.RSA.getValue())) {
			return new RSASSASigner((RSAKey) jwk);
		}
		throw new UnsupportedSignatureAlgorithmException(jwk.getKeyType().toString());
	}

	public static JWSVerifier getJWSVerifier(JWK jwk) throws JOSEException {
		if (Objects.equals(jwk.getKeyType().getValue(), KeyType.EC.getValue())) {
			return new ECDSAVerifier((ECKey) jwk);
		}
		if (Objects.equals(jwk.getKeyType().getValue(), KeyType.RSA.getValue())) {
			return new RSASSAVerifier((RSAKey) jwk);
		}
		throw new UnsupportedSignatureAlgorithmException(jwk.getKeyType().toString());
	}

	public static JWSVerifier getJWSVerifier(X509Certificate certificate) throws JOSEException {
		final PublicKey publicKey = certificate.getPublicKey();
		return switch (publicKey.getAlgorithm()) {
			case EcUtils.KEY_ALGORITHM_EC, EcUtils.KEY_ALGORITHM_ECDSA -> new ECDSAVerifier((ECPublicKey) publicKey);
			case RsaUtils.KEY_ALGORITHM_RSA -> new RSASSAVerifier((RSAPublicKey) publicKey);
			default -> throw new UnsupportedSignatureAlgorithmException(publicKey.getAlgorithm());
		};
	}
}
