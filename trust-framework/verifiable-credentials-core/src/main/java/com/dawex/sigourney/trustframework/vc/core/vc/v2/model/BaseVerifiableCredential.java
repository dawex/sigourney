package com.dawex.sigourney.trustframework.vc.core.vc.v2.model;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;

import java.time.ZonedDateTime;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.GAIAX_DEVELOPMENT;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS_V2;

@JsonLdContexts(
		addBaseContext = true,
		referencedContexts = {
				VERIFIABLE_CREDENTIALS_V2,
				GAIAX_DEVELOPMENT
		})
@JsonLdType("VerifiableCredential")
public class BaseVerifiableCredential<T extends CredentialSubject> implements VerifiableCredential {
	@JsonLdProperty("id")
	private final String id;

	@JsonLdProperty(value = "issuer", mandatory = true)
	private final String issuer;

	@JsonLdProperty(value = "validFrom", mandatory = true)
	private final ZonedDateTime validFrom;

	@JsonLdProperty("validUntil")
	private final ZonedDateTime validUntil;

	@JsonLdProperty(value = "credentialSubject", mandatory = true)
	private final T credentialSubject;

	public BaseVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil, T credentialSubject) {
		this.id = id;
		this.issuer = issuer;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.credentialSubject = credentialSubject;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getIssuer() {
		return issuer;
	}

	public ZonedDateTime getValidFrom() {
		return validFrom;
	}

	public ZonedDateTime getValidUntil() {
		return validUntil;
	}

	public T getCredentialSubject() {
		return credentialSubject;
	}
}
