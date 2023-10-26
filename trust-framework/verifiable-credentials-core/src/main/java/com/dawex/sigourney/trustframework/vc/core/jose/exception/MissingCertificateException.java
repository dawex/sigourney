package com.dawex.sigourney.trustframework.vc.core.jose.exception;

import java.io.Serial;

/**
 * This exception is thrown when importing a certificate from a file that does not contain any certificate.
 */
public class MissingCertificateException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -371195961054002080L;

	public MissingCertificateException() {
	}

	public MissingCertificateException(String message) {
		super(message);
	}

	public MissingCertificateException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingCertificateException(Throwable cause) {
		super(cause);
	}
}
