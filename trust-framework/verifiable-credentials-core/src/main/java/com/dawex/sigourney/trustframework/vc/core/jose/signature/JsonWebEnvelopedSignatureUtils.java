package com.dawex.sigourney.trustframework.vc.core.jose.signature;

import com.dawex.sigourney.trustframework.vc.core.jose.exception.SignatureException;
import com.nimbusds.jose.HeaderParameterNames;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.jwk.JWK;

import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.Map;

import static com.dawex.sigourney.trustframework.vc.core.jose.signature.JwsUtils.getJWSSigner;
import static com.dawex.sigourney.trustframework.vc.core.jose.signature.JwsUtils.getJWSVerifier;
import static com.dawex.sigourney.trustframework.vc.core.jose.signature.JwsUtils.getJwsAlgorithm;

/**
 * Utility class for generating and validating a signature for a payload.
 * It provides an enveloping proof mechanism, where the proof (signature) wraps a serialization of the document.
 */
public class JsonWebEnvelopedSignatureUtils {

	private JsonWebEnvelopedSignatureUtils() {
		// no instance allowed
	}

	/**
	 * Sign the payload using the provided JSON Web Key
	 */
	public static String signWithJWS(String payload, String contentType, String issuer, JWK jwk) {
		try {
			final JWSObject jwsObject = new JWSObject(
					new JWSHeader.Builder(getJwsAlgorithm(jwk))
							.type(new JOSEObjectType(contentType + "+jwt"))
							.contentType(contentType)
							.keyID(issuer + "#" + jwk.getKeyID())
							.customParam(HeaderParameterNames.ISSUER, issuer)
							.build(),
					new Payload(payload));

			final JWSSigner signer = getJWSSigner(jwk);
			jwsObject.sign(signer);
			return jwsObject.serialize();
		} catch (JOSEException e) {
			throw new SignatureException(e);
		}
	}

	/**
	 * Verify the JWS signature using the provided JSON Web Key, and return the payload if it is valid
	 */
	public static Map<String, Object> verifyJWS(String jws, JWK jwk) {
		try {
			final JWSObject jwsObject = JWSObject.parse(jws);
			final JWSVerifier verifier = getJWSVerifier(jwk);
			if (!jwsObject.verify(verifier)) {
				throw new SignatureException("Signature verification failed");
			}
			return jwsObject.getPayload().toJSONObject();
		} catch (JOSEException | ParseException e) {
			throw new SignatureException(e);
		}
	}

	/**
	 * Verify the JWS signature using the provided X.509 certificate, and return the payload if it is valid
	 */
	public static Map<String, Object> verifyJWS(String jws, X509Certificate certificate) {
		try {
			final JWSObject jwsObject = JWSObject.parse(jws);
			final JWSVerifier verifier = getJWSVerifier(certificate);
			if (!jwsObject.verify(verifier)) {
				throw new SignatureException("Signature verification failed");
			}
			return jwsObject.getPayload().toJSONObject();
		} catch (JOSEException | ParseException e) {
			throw new SignatureException(e);
		}
	}
}
