package com.dawex.sigourney.trustframework.vc.core.vc.v2;

import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.CredentialSubject;

import java.time.ZonedDateTime;

public final class TestVerifiableCredential extends BaseVerifiableCredential<CredentialSubject> {
	public TestVerifiableCredential(String id, String issuer, ZonedDateTime validFrom) {
		super(id, issuer, validFrom, null, null);
	}
}
