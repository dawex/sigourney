package com.dawex.sigourney.trustframework.vc.core.jose.exception;

import java.io.Serial;

/**
 * This exception is thrown when signing / verifying a JWS and the key or the certificate uses an unsupported key type
 */
public class UnsupportedSignatureAlgorithmException extends SignatureException {

	@Serial
	private static final long serialVersionUID = -1021770519509011148L;

	public UnsupportedSignatureAlgorithmException(String algorithm) {
		super("Unsupported algorithm [%s]".formatted(algorithm));
	}

	public UnsupportedSignatureAlgorithmException(String algorithm, Throwable cause) {
		super("Unsupported algorithm [%s]".formatted(algorithm), cause);
	}
}
