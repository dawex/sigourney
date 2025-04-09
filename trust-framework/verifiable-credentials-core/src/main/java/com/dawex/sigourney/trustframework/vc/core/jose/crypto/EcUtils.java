package com.dawex.sigourney.trustframework.vc.core.jose.crypto;

import com.dawex.sigourney.trustframework.vc.core.jose.exception.KeyCreationException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.net.URI;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.UUID;

/**
 * Utility methods for working with elliptic curve cryptography
 */
public class EcUtils {

	public static final String KEY_ALGORITHM_EC = "EC";

	public static final String KEY_ALGORITHM_ECDSA = "ECDSA";

	private static final String ECDSA_SECP256R1_SHA256 = "SHA256withECDSA";

	private static final String ECDSA_SECP384R1_SHA384 = "SHA384withECDSA";

	private static final String ECDSA_SECP521R1_SHA512 = "SHA512withECDSA";

	private EcUtils() {
		// no instance allowed
	}

	/**
	 * Generates a pair of EC private and public keys based on the cryptographic curve argument
	 */
	public static KeyPair generateKeyPair(Curve curve) {
		final KeyPair keyPair;
		try {
			final var keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM_EC);
			keyPairGenerator.initialize(curve.toECParameterSpec());
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (InvalidParameterException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			throw new KeyCreationException("The EC key pair cannot be generated", e);
		}
		return keyPair;
	}

	/**
	 * Generates a public key based on the privateKey argument
	 */
	public static PublicKey generatePublicKey(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		final KeyFactory keyFactory = KeyFactory.getInstance(EcUtils.KEY_ALGORITHM_EC);
		final ECPrivateKeySpec keySpec = keyFactory.getKeySpec(privateKey, ECPrivateKeySpec.class);

		final ECPoint publicPoint = keySpec.getParams().getG().multiply(keySpec.getD());
		final ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(publicPoint, keySpec.getParams());
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
	public static ECKey toJWK(String certBaseUrl, X509Certificate cert, PrivateKey privateKey) throws JOSEException {
		final String kid = UUID.randomUUID().toString();
		return new ECKey.Builder(ECKey.parse(cert))
				.keyID(kid)
				.privateKey(privateKey)
				// The "alg" (algorithm) parameter identifies the algorithm intended for use with the key.
				.algorithm(getJwsAlgorithm((ECPublicKey) cert.getPublicKey()))
				.x509CertURL(Optional.ofNullable(certBaseUrl).map(u -> URI.create(u.formatted(kid))).orElse(null))
				.build();
	}

	/**
	 * Create a ContentSigner with a signature algorithm matching the private key
	 */
	public static ContentSigner getContentSigner(ECPrivateKey privateKey) throws OperatorCreationException {
		final ECParameterSpec params = privateKey.getParams();
		final int fieldSize = params.getCurve().getField().getFieldSize();

		return switch (fieldSize) {
			case 256 -> new JcaContentSignerBuilder(ECDSA_SECP256R1_SHA256).build(privateKey);
			case 384 -> new JcaContentSignerBuilder(ECDSA_SECP384R1_SHA384).build(privateKey);
			case 521 -> new JcaContentSignerBuilder(ECDSA_SECP521R1_SHA512).build(privateKey);
			default -> new JcaContentSignerBuilder(ECDSA_SECP256R1_SHA256).build(privateKey);
		};
	}

	private static JWSAlgorithm getJwsAlgorithm(ECPublicKey privateKey) {
		final ECParameterSpec params = privateKey.getParams();
		final int fieldSize = params.getCurve().getField().getFieldSize();

		return switch (fieldSize) {
			case 256 -> JWSAlgorithm.ES256;
			case 384 -> JWSAlgorithm.ES384;
			case 521 -> JWSAlgorithm.ES512;
			default -> null;
		};
	}
}
