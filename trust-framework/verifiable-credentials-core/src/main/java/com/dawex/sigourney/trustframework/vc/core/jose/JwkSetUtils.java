package com.dawex.sigourney.trustframework.vc.core.jose;

import com.dawex.sigourney.trustframework.vc.core.jose.exception.KeyCreationException;
import com.dawex.sigourney.trustframework.vc.core.jose.exception.KeyParsingException;
import com.dawex.sigourney.trustframework.vc.core.jose.exception.MissingCertificateException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
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
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Utility class for handling JwkSets
 */
public class JwkSetUtils {

	/**
	 * JWT are signed with RS256 algorithm, that is an RSA signature with SHA-256.
	 * It's a popular algorithm, and the default one in NimbusReactiveJwtDecoder (used by the resources servers).
	 * The minimum recommended size for RSA key is 2048 bits.
	 */
	private static final int RSA_KEY_SIZE = 2048;

	private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

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
	 * @param certBaseUrl          X509 certificate URL, formatted with the keyId (%s will be replaced by the keyId if present); can be null
	 * @param certIssuerCommonName Common name that appears in the X509 certificate
	 * @param certValidityInMonths validity of the generated X509 certificate, in months
	 * @throws KeyCreationException If the keys cannot be created
	 */
	public static CreatedKeys createKeysWithSelfSignedCertificate(String certBaseUrl, String certIssuerCommonName,
			int certValidityInMonths) {
		try {
			final KeyPair keyPair = generateRsaKey();
			final X509Certificate cert = getSelfSignedX509Certificate(keyPair, certIssuerCommonName, certValidityInMonths);
			final RSAKey rsaKey = buildRsaKey(certBaseUrl, cert, (RSAPrivateKey) keyPair.getPrivate());
			return new CreatedKeys(new JWKSet(rsaKey), List.of(X509CertUtils.toPEMString(cert)));

		} catch (OperatorCreationException | CertificateException | JOSEException e) {
			throw new KeyCreationException("The key pair and/or the X.509 certificate cannot be created", e);
		}
	}

	/**
	 * Create a JWK Set from the private key contained in the pem file, and generate a self-signed X.509 certificate.
	 *
	 * @param privateKeyInputStream private key in PEM format
	 */
	public static CreatedKeys importKeysWithSelfSignedCertificate(InputStream privateKeyInputStream, String certBaseUrl,
			String certIssuerCommonName, int certValidityInMonths) {
		try (InputStreamReader pemReader = new InputStreamReader(privateKeyInputStream)) {
			final KeyPair keyPair = parseRsaPrivateKey(pemReader);
			final X509Certificate cert = getSelfSignedX509Certificate(keyPair, certIssuerCommonName, certValidityInMonths);
			final RSAKey rsaKey = buildRsaKey(certBaseUrl, cert, (RSAPrivateKey) keyPair.getPrivate());
			return new CreatedKeys(new JWKSet(rsaKey), List.of(X509CertUtils.toPEMString(cert)));

		} catch (JOSEException | IOException | OperatorCreationException | GeneralSecurityException e) {
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
			final KeyPair keyPair = parseRsaPrivateKey(pemReader);
			final List<X509Certificate> certificates = getX509Certificates(certificateInputStream);
			final RSAKey rsaKey = buildRsaKey(certBaseUrl, certificates.get(0), (RSAPrivateKey) keyPair.getPrivate());
			return new CreatedKeys(new JWKSet(rsaKey), certificates.stream().map(X509CertUtils::toPEMString).toList());

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
		final ContentSigner contentSigner = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM).build(keyPair.getPrivate());
		final JcaX509CertificateConverter converter = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider());

		return converter.getCertificate(certBuilder.build(contentSigner));
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

	private static KeyPair generateRsaKey() {
		final KeyPair keyPair;
		try {
			final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(RSA_KEY_SIZE);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (InvalidParameterException | NoSuchAlgorithmException e) {
			throw new KeyCreationException("The RSA key pair cannot be generated", e);
		}
		return keyPair;
	}

	private static KeyPair parseRsaPrivateKey(Reader pemReader) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException {
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
		final RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey) new JcaPEMKeyConverter().getPrivateKey(privateKeyInfo);

		// generate public key from private key
		final RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privateKey.getModulus(), privateKey.getPublicExponent());
		final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		final PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		return new KeyPair(publicKey, privateKey);
	}

	private static RSAKey buildRsaKey(String certBaseUrl, X509Certificate cert, RSAPrivateKey privateKey) throws JOSEException {
		final String kid = UUID.randomUUID().toString();
		return new RSAKey.Builder(RSAKey.parse(cert))
				.keyID(kid)
				.privateKey(privateKey)
				// The "alg" (algorithm) parameter identifies the algorithm intended for use with the key.
				.algorithm(JsonWebSignatureUtils.JWS_ALGORITHM)
				.x509CertURL(Optional.ofNullable(certBaseUrl).map(u -> URI.create(u.formatted(kid))).orElse(null))
				.build();
	}

	public record CreatedKeys(JWKSet jwkSet, List<String> certificates) {
	}
}
