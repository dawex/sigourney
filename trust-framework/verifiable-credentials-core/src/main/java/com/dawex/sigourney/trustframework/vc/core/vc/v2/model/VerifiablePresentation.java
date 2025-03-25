package com.dawex.sigourney.trustframework.vc.core.vc.v2.model;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;

import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS_V2;

@JsonLdContexts(referencedContexts = {VERIFIABLE_CREDENTIALS_V2})
@JsonLdType("VerifiablePresentation")
public class VerifiablePresentation {

	@JsonLdProperty(value = "verifiableCredential")
	private final Collection<? extends VerifiableCredential> verifiableCredential;

	public VerifiablePresentation(Collection<? extends VerifiableCredential> verifiableCredential) {
		this.verifiableCredential = verifiableCredential;
	}

	public Collection<? extends VerifiableCredential> getVerifiableCredential() {
		return verifiableCredential;
	}

	public static VerifiablePresentationBuilder builder() {
		return new VerifiablePresentationBuilder();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		VerifiablePresentation that = (VerifiablePresentation) o;
		return Objects.equals(verifiableCredential, that.verifiableCredential);
	}

	@Override
	public int hashCode() {
		return Objects.hash(verifiableCredential);
	}

	@Override
	public String toString() {
		return "VerifiablePresentation{" +
				"verifiableCredential=" + verifiableCredential +
				'}';
	}

	public static class VerifiablePresentationBuilder {
		private Collection<? extends VerifiableCredential> verifiableCredential;

		VerifiablePresentationBuilder() {
		}

		public VerifiablePresentationBuilder verifiableCredential(Collection<? extends VerifiableCredential> verifiableCredential) {
			this.verifiableCredential = verifiableCredential;
			return this;
		}

		public VerifiablePresentation build() {
			return new VerifiablePresentation(this.verifiableCredential);
		}

		@Override
		public String toString() {
			return "VerifiablePresentationBuilder{" +
					"verifiableCredential=" + verifiableCredential +
					'}';
		}
	}
}
