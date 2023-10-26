package com.dawex.sigourney.trustframework.vc.model.shared;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;

import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS;

@JsonLdContexts(referencedContexts = {VERIFIABLE_CREDENTIALS})
@JsonLdType("VerifiablePresentation")
public class VerifiablePresentation {

	@JsonLdProperty(value = "verifiableCredential")
	private final Collection<Object> verifiableCredential;

	public VerifiablePresentation(Collection<Object> verifiableCredential) {
		this.verifiableCredential = verifiableCredential;
	}

	public Collection<Object> getVerifiableCredential() {
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
		private Collection<Object> verifiableCredential;

		VerifiablePresentationBuilder() {
		}

		public VerifiablePresentationBuilder verifiableCredential(Collection<Object> verifiableCredential) {
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
