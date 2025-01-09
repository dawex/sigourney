package com.dawex.sigourney.trustframework.vc.core.vc.v2.model;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS_V2;

@JsonLdContexts(referencedContexts = {VERIFIABLE_CREDENTIALS_V2})
public interface VerifiableCredential {
	String getId();
}
