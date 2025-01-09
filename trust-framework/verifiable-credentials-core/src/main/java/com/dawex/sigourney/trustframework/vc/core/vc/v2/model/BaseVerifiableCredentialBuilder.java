package com.dawex.sigourney.trustframework.vc.core.vc.v2.model;

import java.time.ZonedDateTime;

public abstract class BaseVerifiableCredentialBuilder<V extends BaseVerifiableCredential<S>, S extends CredentialSubject> {

	protected String id;

	protected String issuer;

	protected ZonedDateTime validFrom;

	protected ZonedDateTime validUntil;

	protected S credentialSubject;

	public abstract V build();

	public BaseVerifiableCredentialBuilder<V, S> id(String id) {
		this.id = id;
		return this;
	}

	public BaseVerifiableCredentialBuilder<V, S> issuer(String issuer) {
		this.issuer = issuer;
		return this;
	}

	public BaseVerifiableCredentialBuilder<V, S> validFrom(ZonedDateTime validFrom) {
		this.validFrom = validFrom;
		return this;
	}

	public BaseVerifiableCredentialBuilder<V, S> validUntil(ZonedDateTime validUntil) {
		this.validUntil = validUntil;
		return this;
	}

	public BaseVerifiableCredentialBuilder<V, S> credentialSubject(S credentialSubject) {
		this.credentialSubject = credentialSubject;
		return this;
	}

	public String toString() {
		return "BaseVerifiableCredentialBuilder{" +
				"id='" + id + '\'' +
				", issuer='" + issuer + '\'' +
				", validFrom=" + validFrom +
				", validUntil=" + validUntil +
				", credentialSubject=" + credentialSubject +
				'}';
	}
}
