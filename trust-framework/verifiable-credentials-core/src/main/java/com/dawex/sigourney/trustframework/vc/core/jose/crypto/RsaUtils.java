package com.dawex.sigourney.trustframework.vc.core.jose.crypto;

import com.dawex.sigourney.trustframework.vc.core.jose.exception.KeyCreationException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.RSAKey;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.net.URI;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Optional;
import java.util.UUID;

/**
 * Utility methods for working with RSA cryptography
 */
public class RsaUtils {

	public static final String KEY_ALGORITHM_RSA = "RSA";

	private static final String RSA_PKCS1_SHA256 = "SHA256withRSA";

	private static final String RSA_PKCS1_SHA384 = "SHA384withRSA";

	private static final String RSA_PKCS1_SHA512 = "SHA512withRSA";

	private RsaUtils() {
		// no instance allowed
	}

	/**
	 * Generates a pair of RSA private and public keys with the specified key size.
	 * Please note that a minimum size of 2048 is recommended.
	 */
	public static KeyPair generateKeyPair(int keySize) {
		final KeyPair keyPair;
		try {
			final var keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA);
			keyPairGenerator.initialize(keySize);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (InvalidParameterException | NoSuchAlgorithmException e) {
			throw new KeyCreationException("The RSA key pair cannot be generated", e);
		}
		return keyPair;
	}

	/**
	 * Generates a public key based on the privateKey argument
	 */
	public static PublicKey generatePublicKey(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
		final RSAPrivateCrtKeySpec rsaPrivateKey = keyFactory.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);

		final RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPublicExponent());
		return keyFactory.generatePublic(publicKeySpec);
	}

	/**
	 * Build a JWK from the certificate and the privateKey arguments, that contains both private and public keys.
	 * The following headers are set, in addition to those extracted from the certificate :
	 * <ul>
	 *     <li>alg: the JWS algorithm, deduced from the certificate</li>
	 *     <li>kid: randomly generated UUID</li>
	 *     <li>x5u: URL of the certificate (x5c is also filled, with the base64 encoded certificate)</li>
	 * </ul>
	 */
	public static RSAKey toJWK(String certBaseUrl, X509Certificate cert, PrivateKey privateKey) throws JOSEException {
		final String kid = UUID.randomUUID().toString();
		return new RSAKey.Builder(RSAKey.parse(cert))
				.keyID(kid)
				.privateKey(privateKey)
				// The "alg" (algorithm) parameter identifies the algorithm intended for use with the key.
				.algorithm(getJwsAlgorithm((RSAPublicKey) cert.getPublicKey()))
				.x509CertURL(Optional.ofNullable(certBaseUrl).map(u -> URI.create(u.formatted(kid))).orElse(null))
				.build();
	}

	/**
	 * Create a ContentSigner with a signature algorithm matching the private key
	 */
	public static ContentSigner getContentSigner(RSAPrivateKey privateKey) throws OperatorCreationException {
		final int keySize = privateKey.getModulus().bitLength();
		if (keySize >= 4096) {
			return new JcaContentSignerBuilder(RSA_PKCS1_SHA512).build(privateKey);
		} else if (keySize >= 3072) {
			return new JcaContentSignerBuilder(RSA_PKCS1_SHA384).build(privateKey);
		} else {
			return new JcaContentSignerBuilder(RSA_PKCS1_SHA256).build(privateKey);
		}
	}

	private static JWSAlgorithm getJwsAlgorithm(RSAPublicKey publicKey) {
		final int keySize = publicKey.getModulus().bitLength();
		if (keySize >= 4096) {
			return JWSAlgorithm.PS512;
		} else if (keySize >= 3072) {
			return JWSAlgorithm.PS384;
		} else {
			return JWSAlgorithm.PS256;
		}
	}
}
