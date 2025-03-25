package com.dawex.sigourney.trustframework.vc.core.vc.v1;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;

import java.time.ZonedDateTime;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.GAIAX_TRUST_FRAMEWORK_V1;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.SECURITY_JWS_2020;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS_V1;

@JsonLdContexts(
		addBaseContext = true,
		referencedContexts = {
				VERIFIABLE_CREDENTIALS_V1,
				SECURITY_JWS_2020,
				GAIAX_TRUST_FRAMEWORK_V1
		})
@JsonLdType("VerifiableCredential")
public final class TestVerifiableCredential {
	@JsonLdProperty(value = "id")
	private final String id;

	@JsonLdProperty(value = "issuer")
	private final String issuer;

	@JsonLdProperty(value = "issuanceDate")
	private final ZonedDateTime issuanceDate;

	TestVerifiableCredential(String id, String issuer, ZonedDateTime issuanceDate) {
		this.id = id;
		this.issuer = issuer;
		this.issuanceDate = issuanceDate;
	}

	public String getIssuer() {
		return issuer;
	}

	public String getId() {
		return id;
	}

	public ZonedDateTime getIssuanceDate() {
		return issuanceDate;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TestVerifiableCredential that = (TestVerifiableCredential) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
