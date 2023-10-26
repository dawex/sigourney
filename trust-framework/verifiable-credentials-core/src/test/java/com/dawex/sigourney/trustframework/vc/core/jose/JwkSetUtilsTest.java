package com.dawex.sigourney.trustframework.vc.core.jose;

import com.dawex.sigourney.trustframework.vc.core.Constant;
import com.dawex.sigourney.trustframework.vc.core.jose.exception.KeyCreationException;
import com.dawex.sigourney.trustframework.vc.core.jose.exception.MissingCertificateException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.X509CertUtils;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

	@Test
	void shouldParseJwkSet() throws ParseException {
		final JWKSet jwkSet = JWKSet.parse(Constant.JWK_SET);
		final Map<String, Object> jwkSetAsMap = jwkSet.toJSONObject(false);
		final String expectedJwkSet = jwkSet.toString(false);

		final JWKSet actual = JwkSetUtils.parseJwkSet(jwkSetAsMap);

		assertThat(actual.toString(false))
				.isEqualTo(expectedJwkSet);
	}

	@Test
	void shouldCreateKeysWithSelfSignedCertificate() throws GeneralSecurityException, JOSEException {
		final JwkSetUtils.CreatedKeys keys = JwkSetUtils.createKeysWithSelfSignedCertificate(
				CERT_BASE_URL, CERT_COMMON_NAME, CERT_VALIDITY_IN_MONTHS);

		assertThatCreatedKeysAreValid(keys);
	}

	@Test
	void shouldImportKeysWithSelfSignedCertificate() throws JOSEException, IOException, GeneralSecurityException {
		final JwkSetUtils.CreatedKeys referenceKeys = JwkSetUtils.createKeysWithSelfSignedCertificate(
				CERT_BASE_URL, CERT_COMMON_NAME, CERT_VALIDITY_IN_MONTHS);

		final String privateKeyPem = getPrivateKeyPem(referenceKeys);
		final JwkSetUtils.CreatedKeys keys;
		try (final var inputStream = new ByteArrayInputStream(privateKeyPem.getBytes(StandardCharsets.UTF_8))) {
			keys = JwkSetUtils.importKeysWithSelfSignedCertificate(
					inputStream, CERT_BASE_URL, CERT_COMMON_NAME, CERT_VALIDITY_IN_MONTHS);
		}

		assertThatCreatedKeysAreValid(keys);
	}

	@Test
	void shouldImportKeysAndCertificate() throws JOSEException, IOException, GeneralSecurityException {
		final JwkSetUtils.CreatedKeys referenceKeys = JwkSetUtils.createKeysWithSelfSignedCertificate(
				CERT_BASE_URL, CERT_COMMON_NAME, CERT_VALIDITY_IN_MONTHS);

		final String privateKeyPem = getPrivateKeyPem(referenceKeys);
		final String certificatePem = referenceKeys.certificates().get(0);

		final JwkSetUtils.CreatedKeys keys;
		try (final var privateKeyInputStream = new ByteArrayInputStream(privateKeyPem.getBytes(StandardCharsets.UTF_8));
				final var certificateInputStream = new ByteArrayInputStream(certificatePem.getBytes(StandardCharsets.UTF_8))) {
			keys = JwkSetUtils.importKeysAndCertificate(privateKeyInputStream, certificateInputStream, CERT_BASE_URL);
		}

		assertThatCreatedKeysAreValid(keys);
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
		final RSAKey rsaKey = referenceKeys.jwkSet().getKeys().get(0).toRSAKey();
		final PemObject pemObject = new PemObject("PRIVATE KEY", rsaKey.toPrivateKey().getEncoded());

		final StringWriter stringWriter = new StringWriter();
		try (final PemWriter pemWriter = new PemWriter(stringWriter)) {
			pemWriter.writeObject(pemObject);
		}
		final String pemString = stringWriter.toString();
		assertThat(pemString).isNotEmpty();

		return pemString;
	}

	private static void assertThatCreatedKeysAreValid(JwkSetUtils.CreatedKeys keys) throws GeneralSecurityException, JOSEException {
		final List<JWK> jwks = keys.jwkSet().getKeys();
		assertThat(jwks).hasSize(1);

		final JWK jwk = jwks.get(0);
		assertThat(jwk.isPrivate()).isTrue();

		final RSAKey rsaKey = jwk.toRSAKey();
		assertThat(rsaKey.getKeyID()).isNotEmpty();
		assertThat(rsaKey.isPrivate()).isTrue();
		assertThat(rsaKey.getX509CertURL()).hasToString(CERT_BASE_URL.formatted(rsaKey.getKeyID()));

		final List<String> certificates = keys.certificates();
		assertThat(certificates).hasSize(1);

		final X509Certificate certificate = X509CertUtils.parse(certificates.get(0));
		assertThat(certificate).isNotNull();
		// Checks that the certificate is currently valid.
		assertThatNoException().isThrownBy(certificate::checkValidity);

		// Verifies that this certificate was signed using the private key that corresponds to the specified public key.
		assertThatNoException().isThrownBy(() -> certificate.verify(rsaKey.toPublicKey()));

		// Checks that messages encrypted with the private key can be decrypted with the public key
		assertThatCanEncryptAndDecrypt(rsaKey);
	}

	private static void assertThatCanEncryptAndDecrypt(RSAKey rsaKey) throws GeneralSecurityException, JOSEException {
		final String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, rsaKey.toPrivateKey());
		byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

		cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, rsaKey.toPublicKey());
		byte[] result = cipher.doFinal(encryptedBytes);

		assertThat(new String(result, StandardCharsets.UTF_8)).isEqualTo(message);
	}
}