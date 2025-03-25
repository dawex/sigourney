package com.dawex.sigourney.trustframework.vc.core.jose.crypto;

import com.dawex.sigourney.trustframework.vc.core.jose.exception.KeyCreationException;
import com.dawex.sigourney.trustframework.vc.core.jose.exception.MissingCertificateException;
import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.AsymmetricJWK;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyType;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.X509CertUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

class JwkSetUtilsTest {

	private static final String CERT_BASE_URL = "https://127.0.0.1:9000/api/certificates/%s";

	private static final String CERT_COMMON_NAME = "Dawex";

	private static final int CERT_VALIDITY_IN_MONTHS = 12;

	public static final String CERTIFICATE_EXPIRED = """
			-----BEGIN CERTIFICATE-----
			MIIDazCCAlOgAwIBAgIUfn4TAnBhlymatyYN6ONOQQDU8vcwDQYJKoZIhvcNAQEL
			BQAwRTELMAkGA1UEBhMCQVUxEzARBgNVBAgMClNvbWUtU3RhdGUxITAfBgNVBAoM
			GEludGVybmV0IFdpZGdpdHMgUHR5IEx0ZDAeFw0yMjA4MzAxMzMwNDhaFw0yMzA4
			MzAxMzMwNDhaMEUxCzAJBgNVBAYTAkFVMRMwEQYDVQQIDApTb21lLVN0YXRlMSEw
			HwYDVQQKDBhJbnRlcm5ldCBXaWRnaXRzIFB0eSBMdGQwggEiMA0GCSqGSIb3DQEB
			AQUAA4IBDwAwggEKAoIBAQC49X9N2ufwm63WwjvlfvbA/d3rQL9QJR1JrQdAKqod
			QRHt3R067gxcRXmiTxoldXW4LV7QwcG5ZJFmLPaect1nXKJsbveK8O7Oy0wta/hz
			d+/leWjdXm/uDEX7clgesnmf8h9NLLP4XX2pkeCmxToCD7n2cuZICpQCMnt6PrF9
			TdjYKtTtLim+PqX3XyY3gqHlJyVwPMH6PRVKT9fIV1QYpeNZBSHr4piExGGzfjrD
			usCcwU9AWziX4dvGy2hK5e4XMJwq8Wfwkdec8IjaHZHJ3oJKcRSot3gPmah1RF/i
			65hKBjPcxqH1/Uq4BswgHC+D97rWAGwlZ++Se6NdeBd1AgMBAAGjUzBRMB0GA1Ud
			DgQWBBSC0ywFI25kioEedarFua0FawiuGzAfBgNVHSMEGDAWgBSC0ywFI25kioEe
			darFua0FawiuGzAPBgNVHRMBAf8EBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQCs
			uJTxl3pZMTYDS0QNfZVYnIFSDxWmMuohI2iQ3bXbugQJINMdEoTdqDGtIQDlwmCw
			yV3fynlT/nIKStI6yExWHdytR0i7JZGSotVBZ98hMTQy3avactHqObc6yckNpW67
			bEiZ3UfyFcegpH/BxGfZDsmWqmkY2e4Nnsh+QQ+6x/lcuSsd8zbYws27a9ZR1M6U
			IAinafx3CJO+bXfhrhDB+fSZMFKM676EgEzB/udoJg55jY45f4qQ4cJmltBFFzsQ
			T0y2bYtBayafzPkfODwYY31f3BPLjdJI+JO4YwHNTHOXDtIn20KC47VyHEJAtea8
			uJkrJk4rr7FAbGkNQWC+
			-----END CERTIFICATE-----
			""";

	@BeforeAll
	static void init() {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Test
	void shouldParseJwkSet() {
		final JWKSet jwkSet = JwkSetUtils.createKeysWithSelfSignedCertificate(JwkSetUtils.KeyAlgorithm.RSA_2048, null, "Test", 12).jwkSet();
		final Map<String, Object> jwkSetAsMap = jwkSet.toJSONObject(false);
		final String expectedJwkSet = jwkSet.toString(false);

		final JWKSet actual = JwkSetUtils.parseJwkSet(jwkSetAsMap);

		assertThat(actual.toString(false))
				.isEqualTo(expectedJwkSet);
	}

	@ParameterizedTest
	@EnumSource(JwkSetUtils.KeyAlgorithm.class)
	void shouldCreateKeysWithSelfSignedCertificate(JwkSetUtils.KeyAlgorithm keyAlgorithm) throws JOSEException, ParseException {
		final JwkSetUtils.CreatedKeys keys = JwkSetUtils.createKeysWithSelfSignedCertificate(
				keyAlgorithm, CERT_BASE_URL, CERT_COMMON_NAME, CERT_VALIDITY_IN_MONTHS);

		assertThatCreatedKeysAreValid(keys, keyAlgorithm);
	}

	@ParameterizedTest
	@EnumSource(JwkSetUtils.KeyAlgorithm.class)
	void shouldImportKeysWithSelfSignedCertificate(JwkSetUtils.KeyAlgorithm keyAlgorithm)
			throws JOSEException, IOException, ParseException {
		final JwkSetUtils.CreatedKeys referenceKeys = JwkSetUtils.createKeysWithSelfSignedCertificate(
				keyAlgorithm, CERT_BASE_URL, CERT_COMMON_NAME, CERT_VALIDITY_IN_MONTHS);

		final String privateKeyPem = getPrivateKeyPem(referenceKeys);
		final JwkSetUtils.CreatedKeys keys;
		try (final var inputStream = new ByteArrayInputStream(privateKeyPem.getBytes(StandardCharsets.UTF_8))) {
			keys = JwkSetUtils.importKeysWithSelfSignedCertificate(
					inputStream, CERT_BASE_URL, CERT_COMMON_NAME, CERT_VALIDITY_IN_MONTHS);
		}

		assertThatCreatedKeysAreValid(keys, keyAlgorithm);
	}

	@ParameterizedTest
	@EnumSource(JwkSetUtils.KeyAlgorithm.class)
	void shouldImportKeysAndCertificate(JwkSetUtils.KeyAlgorithm keyAlgorithm) throws JOSEException, IOException, ParseException {
		final JwkSetUtils.CreatedKeys referenceKeys = JwkSetUtils.createKeysWithSelfSignedCertificate(
				keyAlgorithm, CERT_BASE_URL, CERT_COMMON_NAME, CERT_VALIDITY_IN_MONTHS);

		final String privateKeyPem = getPrivateKeyPem(referenceKeys);
		final String certificatePem = referenceKeys.certificates().get(0);

		final JwkSetUtils.CreatedKeys keys;
		try (final var privateKeyInputStream = new ByteArrayInputStream(privateKeyPem.getBytes(StandardCharsets.UTF_8));
				final var certificateInputStream = new ByteArrayInputStream(certificatePem.getBytes(StandardCharsets.UTF_8))) {
			keys = JwkSetUtils.importKeysAndCertificate(privateKeyInputStream, certificateInputStream, CERT_BASE_URL);
		}

		assertThatCreatedKeysAreValid(keys, keyAlgorithm);
	}

	@Test
	void withInvalidCertificateShouldNotImportKeysAndCertificate() throws JOSEException, IOException {
		final JwkSetUtils.CreatedKeys referenceKeys = JwkSetUtils.createKeysWithSelfSignedCertificate(
				CERT_BASE_URL, CERT_COMMON_NAME, CERT_VALIDITY_IN_MONTHS);

		final String privateKeyPem = getPrivateKeyPem(referenceKeys);

		try (final var privateKeyInputStream = new ByteArrayInputStream(privateKeyPem.getBytes(StandardCharsets.UTF_8));
				final var certificateInputStream = new ByteArrayInputStream(CERTIFICATE_EXPIRED.getBytes(StandardCharsets.UTF_8))) {
			assertThatThrownBy(() -> JwkSetUtils.importKeysAndCertificate(privateKeyInputStream, certificateInputStream, CERT_BASE_URL))
					.isInstanceOf(KeyCreationException.class)
					.hasCauseInstanceOf(CertificateException.class);
		}
	}

	@Test
	void withMissingCertificateShouldNotImportKeysAndCertificate() throws JOSEException, IOException {
		final JwkSetUtils.CreatedKeys referenceKeys = JwkSetUtils.createKeysWithSelfSignedCertificate(
				CERT_BASE_URL, CERT_COMMON_NAME, CERT_VALIDITY_IN_MONTHS);

		final String privateKeyPem = getPrivateKeyPem(referenceKeys);

		try (final var privateKeyInputStream = new ByteArrayInputStream(privateKeyPem.getBytes(StandardCharsets.UTF_8));
				final var certificateInputStream = new ByteArrayInputStream(privateKeyPem.getBytes(StandardCharsets.UTF_8))) {
			assertThatThrownBy(() -> JwkSetUtils.importKeysAndCertificate(privateKeyInputStream, certificateInputStream, CERT_BASE_URL))
					.isInstanceOf(KeyCreationException.class)
					.hasCauseInstanceOf(MissingCertificateException.class);
		}
	}

	private static String getPrivateKeyPem(JwkSetUtils.CreatedKeys referenceKeys) throws JOSEException, IOException {
		final JWK jwk = referenceKeys.jwkSet().getKeys().get(0);
		final PemObject pemObject = new PemObject("PRIVATE KEY", ((AsymmetricJWK) jwk).toPrivateKey().getEncoded());

		final StringWriter stringWriter = new StringWriter();
		try (final PemWriter pemWriter = new PemWriter(stringWriter)) {
			pemWriter.writeObject(pemObject);
		}
		final String pemString = stringWriter.toString();
		assertThat(pemString).isNotEmpty();

		return pemString;
	}

	private static void assertThatCreatedKeysAreValid(JwkSetUtils.CreatedKeys keys, JwkSetUtils.KeyAlgorithm keyAlgorithm)
			throws JOSEException, ParseException {
		final List<JWK> jwks = keys.jwkSet().getKeys();
		assertThat(jwks).hasSize(1);

		final JWK jwk = jwks.get(0);
		assertThat(jwk.getKeyType()).isEqualTo(getExpectedKeyType(keyAlgorithm));
		assertThat(jwk.getAlgorithm()).isNotNull()
				.extracting(Algorithm::getName).isEqualTo(getExpectedAlgorithmName(keyAlgorithm));
		assertThat(jwk.size()).isEqualTo(getExpectedKeySize(keyAlgorithm));
		assertThat(jwk.getKeyID()).isNotEmpty();
		assertThat(jwk.isPrivate()).isTrue();
		assertThat(jwk.getX509CertURL()).hasToString(CERT_BASE_URL.formatted(jwk.getKeyID()));

		final List<String> certificates = keys.certificates();
		assertThat(certificates).hasSize(1);

		final X509Certificate certificate = X509CertUtils.parse(certificates.get(0));
		assertThat(certificate).isNotNull();
		// Checks that the certificate is currently valid.
		assertThatNoException().isThrownBy(certificate::checkValidity);

		// Verifies that this certificate was signed using the private key that corresponds to the specified public key.
		assertThat(jwk).isInstanceOf(AsymmetricJWK.class);
		assertThatNoException().isThrownBy(() -> certificate.verify(((AsymmetricJWK) jwk).toPublicKey()));

		// Checks that messages encrypted with the private key can be decrypted with the public key
		assertThatCanSignAndValidate(jwk);
	}

	private static KeyType getExpectedKeyType(JwkSetUtils.KeyAlgorithm keyAlgorithm) {
		return switch (keyAlgorithm) {
			case P_256, P_384, P_521 -> KeyType.EC;
			case RSA_2048, RSA_3072, RSA_4096 -> KeyType.RSA;
		};
	}

	private static String getExpectedAlgorithmName(JwkSetUtils.KeyAlgorithm keyAlgorithm) {
		return (switch (keyAlgorithm) {
			case P_256 -> JWSAlgorithm.ES256;
			case P_384 -> JWSAlgorithm.ES384;
			case P_521 -> JWSAlgorithm.ES512;
			case RSA_2048 -> JWSAlgorithm.PS256;
			case RSA_3072 -> JWSAlgorithm.PS384;
			case RSA_4096 -> JWSAlgorithm.PS512;
		}).getName();
	}

	private static Integer getExpectedKeySize(JwkSetUtils.KeyAlgorithm keyAlgorithm) {
		return switch (keyAlgorithm) {
			case P_256 -> 256;
			case P_384 -> 384;
			case P_521 -> 521;
			case RSA_2048 -> 2048;
			case RSA_3072 -> 3072;
			case RSA_4096 -> 4096;
		};
	}

	private static void assertThatCanSignAndValidate(JWK jwk) throws JOSEException, ParseException {
		if (jwk.getKeyType() == KeyType.EC) {
			assertThatCanSignAndValidate((ECKey) jwk);
		} else if (jwk.getKeyType() == KeyType.RSA) {
			assertThatCanSignAndValidate((RSAKey) jwk);
		} else {
			fail("Tests for KeyType [%s] not implemented".formatted(jwk.getKeyType()));
		}
	}

	private static void assertThatCanSignAndValidate(ECKey ecKey) throws JOSEException, ParseException {
		final String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
		final JWSObject jwsObject = new JWSObject(
				new JWSHeader.Builder((JWSAlgorithm) ecKey.getAlgorithm()).keyID(ecKey.getKeyID()).build(),
				new Payload(message.getBytes(StandardCharsets.UTF_8))
		);

		// Sign JWS
		final JWSSigner signer = new ECDSASigner(ecKey);
		jwsObject.sign(signer);
		final String serialized = jwsObject.serialize();

		// Validate JWS
		final JWSObject parsedJws = JWSObject.parse(serialized);
		final JWSVerifier verifier = new ECDSAVerifier(ecKey.toPublicJWK());

		assertThat(parsedJws.verify(verifier)).isTrue();
		assertThat(parsedJws.getPayload()).hasToString(message);
	}

	private static void assertThatCanSignAndValidate(RSAKey rsaKey) throws JOSEException, ParseException {
		final String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
		final JWSObject jwsObject = new JWSObject(
				new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(),
				new Payload(message.getBytes(StandardCharsets.UTF_8))
		);

		// Sign JWS
		final JWSSigner signer = new RSASSASigner(rsaKey);
		jwsObject.sign(signer);
		final String serialized = jwsObject.serialize();

		// Validate JWS
		final JWSObject parsedJws = JWSObject.parse(serialized);
		final JWSVerifier verifier = new RSASSAVerifier(rsaKey.toPublicJWK());

		assertThat(parsedJws.verify(verifier)).isTrue();
		assertThat(parsedJws.getPayload()).hasToString(message);
	}
}