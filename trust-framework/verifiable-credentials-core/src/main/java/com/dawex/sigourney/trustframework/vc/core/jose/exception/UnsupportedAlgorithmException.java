package com.dawex.sigourney.trustframework.vc.core.jose.exception;

import java.io.Serial;

/**
 * This exception is thrown when creating / importing a key or a certificate with an unsupported algorithm
 */
public class UnsupportedAlgorithmException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -3386993085134186826L;

	public UnsupportedAlgorithmException(String algorithm) {
		super("Unsupported algorithm [%s]".formatted(algorithm));
	}

	public UnsupportedAlgorithmException(String algorithm, Throwable cause) {
		super("Unsupported algorithm [%s]".formatted(algorithm), cause);
	}
}
