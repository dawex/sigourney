package com.dawex.sigourney.trustframework.vc.core.jose.crypto;

import com.dawex.sigourney.trustframework.vc.core.jose.exception.KeyCreationException;
import com.dawex.sigourney.trustframework.vc.core.jose.exception.KeyParsingException;
import com.dawex.sigourney.trustframework.vc.core.jose.exception.MissingCertificateException;
import com.dawex.sigourney.trustframework.vc.core.jose.exception.UnsupportedAlgorithmException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.util.IOUtils;
import com.nimbusds.jose.util.X509CertChainUtils;
import com.nimbusds.jose.util.X509CertUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Utility class for handling JwkSets
 */
public class JwkSetUtils {

	private JwkSetUtils() {
		// no instance allowed
	}

	/**
	 * Parses the specified JSON object representing a JSON Web Key (JWK) set.
	 *
	 * @throws KeyParsingException If the data map couldn't be parsed to a valid JSON Web Key (JWK) set.
	 */
	public static JWKSet parseJwkSet(Map<String, Object> data) {
		try {
			return JWKSet.parse(data);
		} catch (ParseException e) {
			throw new KeyParsingException(e);
		}
	}

	/**
	 * Creates a new Jwk Set, and generate a self-signed X.509 certificate.
	 *
	 * @param keyAlgorithm         algorithm used to generate the public and private keys
	 * @param certBaseUrl          X509 certificate URL, formatted with the keyId (%s will be replaced by the keyId if present); can be null
	 * @param certIssuerCommonName Common name that appears in the X509 certificate
	 * @param certValidityInMonths validity of the generated X509 certificate, in months
	 * @throws KeyCreationException If the keys cannot be created
	 */
	public static CreatedKeys createKeysWithSelfSignedCertificate(KeyAlgorithm keyAlgorithm, String certBaseUrl,
			String certIssuerCommonName, int certValidityInMonths) {
		try {
			final KeyPair keyPair = switch (keyAlgorithm) {
				case P_256 -> EcUtils.generateKeyPair(Curve.P_256);
				case P_384 -> EcUtils.generateKeyPair(Curve.P_384);
				case P_521 -> EcUtils.generateKeyPair(Curve.P_521);
				case RSA_2048 -> RsaUtils.generateKeyPair(2048);
				case RSA_3072 -> RsaUtils.generateKeyPair(3072);
				case RSA_4096 -> RsaUtils.generateKeyPair(4096);
			};
			final X509Certificate cert = getSelfSignedX509Certificate(keyPair, certIssuerCommonName, certValidityInMonths);
			final JWK jwk = toJWK(certBaseUrl, cert, keyPair.getPrivate());
			return new CreatedKeys(new JWKSet(jwk), List.of(X509CertUtils.toPEMString(cert)));

		} catch (OperatorCreationException | CertificateException | JOSEException e) {
			throw new KeyCreationException("The key pair and/or the X.509 certificate cannot be created", e);
		}
	}

	/**
	 * Creates a new Jwk Set, and generate a self-signed X.509 certificate using RSA with a 2048 key size.
	 */
	public static CreatedKeys createKeysWithSelfSignedCertificate(String certBaseUrl, String certIssuerCommonName,
			int certValidityInMonths) {
		return createKeysWithSelfSignedCertificate(KeyAlgorithm.RSA_2048, certBaseUrl, certIssuerCommonName, certValidityInMonths);
	}

	/**
	 * Create a JWK Set from the private key contained in the pem file, and generate a self-signed X.509 certificate.
	 *
	 * @param privateKeyInputStream private key in PEM format
	 */
	public static CreatedKeys importKeysWithSelfSignedCertificate(InputStream privateKeyInputStream, String certBaseUrl,
			String certIssuerCommonName, int certValidityInMonths) {
		try (InputStreamReader pemReader = new InputStreamReader(privateKeyInputStream)) {
			final PrivateKey privateKey = parsePrivateKey(pemReader);
			final PublicKey publicKey = generatePublicKey(privateKey);
			final X509Certificate cert = getSelfSignedX509Certificate(new KeyPair(publicKey, privateKey),
					certIssuerCommonName, certValidityInMonths);
			final JWK jwk = toJWK(certBaseUrl, cert, privateKey);
			return new CreatedKeys(new JWKSet(jwk), List.of(X509CertUtils.toPEMString(cert)));

		} catch (IOException | OperatorCreationException | GeneralSecurityException | JOSEException e) {
			throw new KeyCreationException("The key pair cannot be imported and/or the X.509 certificate cannot be created", e);
		}
	}

	/**
	 * Create a JWK Set from the private key contained in the pem file, and the provided X.509 certificate.
	 * Certificate must be valid, et match the provided private key.
	 *
	 * @param privateKeyInputStream  private key in PEM format
	 * @param certificateInputStream certificate in PEM format
	 */
	public static CreatedKeys importKeysAndCertificate(InputStream privateKeyInputStream, InputStream certificateInputStream,
			String certBaseUrl) {
		try (InputStreamReader pemReader = new InputStreamReader(privateKeyInputStream)) {
			final PrivateKey privateKey = parsePrivateKey(pemReader);
			final List<X509Certificate> certificates = getX509Certificates(certificateInputStream);
			final JWK jwk = toJWK(certBaseUrl, certificates.get(0), privateKey);
			return new CreatedKeys(new JWKSet(jwk), certificates.stream().map(X509CertUtils::toPEMString).toList());

		} catch (GeneralSecurityException | JOSEException | IOException | MissingCertificateException e) {
			throw new KeyCreationException("The key pair and/or the X.509 certificate cannot be imported", e);
		}
	}

	/**
	 * Generate a self-signed certificate from the specified keyPair
	 */
	private static X509Certificate getSelfSignedX509Certificate(KeyPair keyPair, String commonName, int validityInMonths)
			throws OperatorCreationException, CertificateException {
		// arbitrary X500Name
		final X500Name name = new X500Name("CN=%s".formatted(commonName));
		// certificate serial number https://www.rfc-editor.org/rfc/rfc3280#section-4.1.2.2
		final BigInteger serial = BigInteger.valueOf(new SecureRandom().nextLong(0, Long.MAX_VALUE));
		// certificate validity arbitrary set to 1 year
		final OffsetDateTime now = LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC);
		final Date notBefore = Date.from(now.toInstant());
		final Date notAfter = Date.from(now.plusMonths(validityInMonths).toInstant());

		final X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
				name, serial, notBefore, notAfter, name, keyPair.getPublic());
		final ContentSigner contentSigner = getContentSigner(keyPair.getPrivate());
		final JcaX509CertificateConverter converter = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider());

		return converter.getCertificate(certBuilder.build(contentSigner));
	}

	private static PrivateKey parsePrivateKey(Reader pemReader) throws IOException {
		final Object readObject;
		try (final PEMParser pemParser = new PEMParser(pemReader)) {
			readObject = pemParser.readObject();
		}
		// get private key
		final PrivateKeyInfo privateKeyInfo;
		if (readObject instanceof PEMKeyPair pemKeyPair) {
			privateKeyInfo = pemKeyPair.getPrivateKeyInfo();
		} else {
			privateKeyInfo = PrivateKeyInfo.getInstance(readObject);
		}
		return new JcaPEMKeyConverter().getPrivateKey(privateKeyInfo);
	}

	/**
	 * Parse X.509 certificates from the InputStream
	 */
	private static List<X509Certificate> getX509Certificates(InputStream certificateInputStream)
			throws IOException, CertificateException, MissingCertificateException {
		final String pemEncodedCert = IOUtils.readInputStreamToString(certificateInputStream);
		final List<X509Certificate> certificates = X509CertChainUtils.parse(pemEncodedCert);
		if (certificates.isEmpty()) {
			throw new MissingCertificateException();
		}
		for (X509Certificate certificate : certificates) {
			certificate.checkValidity();
		}
		return certificates;
	}

	private static PublicKey generatePublicKey(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return switch (privateKey.getAlgorithm()) {
			case EcUtils.KEY_ALGORITHM_EC, EcUtils.KEY_ALGORITHM_ECDSA -> EcUtils.generatePublicKey(privateKey);
			case RsaUtils.KEY_ALGORITHM_RSA -> RsaUtils.generatePublicKey(privateKey);
			default -> throw new UnsupportedAlgorithmException(privateKey.getAlgorithm());
		};
	}

	private static JWK toJWK(String certBaseUrl, X509Certificate cert, PrivateKey privateKey) throws JOSEException {
		return switch (privateKey.getAlgorithm()) {
			case EcUtils.KEY_ALGORITHM_EC, EcUtils.KEY_ALGORITHM_ECDSA -> EcUtils.toJWK(certBaseUrl, cert, privateKey);
			case RsaUtils.KEY_ALGORITHM_RSA -> RsaUtils.toJWK(certBaseUrl, cert, privateKey);
			default -> throw new UnsupportedAlgorithmException(privateKey.getAlgorithm());
		};
	}

	private static ContentSigner getContentSigner(PrivateKey privateKey) throws OperatorCreationException {
		return switch (privateKey.getAlgorithm()) {
			case EcUtils.KEY_ALGORITHM_EC, EcUtils.KEY_ALGORITHM_ECDSA -> EcUtils.getContentSigner((ECPrivateKey) privateKey);
			case RsaUtils.KEY_ALGORITHM_RSA -> RsaUtils.getContentSigner((RSAPrivateKey) privateKey);
			default -> throw new UnsupportedAlgorithmException(privateKey.getAlgorithm());
		};
	}

	public record CreatedKeys(JWKSet jwkSet, List<String> certificates) {
	}

	public enum KeyAlgorithm {
		P_256, // secp256r1
		P_384, // secp384r1
		P_521, // secp521r1
		RSA_2048, // RSA 2048 bits
		RSA_3072, // RSA 3072 bits
		RSA_4096 // RSA 4096 bits
	}
}
