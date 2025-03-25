package com.dawex.sigourney.trustframework.vc.model.shared;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;

import java.util.List;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.DID;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.SECURITY_JWS_2020;

@JsonLdContexts(referencedContexts = {DID, SECURITY_JWS_2020})
public class Did {
	@JsonLdProperty("id")
	private final String id;

	@JsonLdProperty("verificationMethod")
	private final List<VerificationMethod> verificationMethod;

	@JsonLdProperty("assertionMethod")
	private final List<String> assertionMethod;

	public Did(String id, List<VerificationMethod> verificationMethod, List<String> assertionMethod) {
		this.id = id;
		this.verificationMethod = verificationMethod;
		this.assertionMethod = assertionMethod;
	}

	public String getId() {
		return id;
	}

	public List<VerificationMethod> getVerificationMethod() {
		return verificationMethod;
	}

	public List<String> getAssertionMethod() {
		return assertionMethod;
	}

	public static DidBuilder builder() {
		return new DidBuilder();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Did did = (Did) o;
		return Objects.equals(id, did.id) && Objects.equals(verificationMethod, did.verificationMethod) &&
				Objects.equals(assertionMethod, did.assertionMethod);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, verificationMethod, assertionMethod);
	}

	@Override
	public String toString() {
		return "Did{" +
				"id='" + id + '\'' +
				", verificationMethod=" + verificationMethod +
				", assertionMethod=" + assertionMethod +
				'}';
	}

	public static class DidBuilder {

		private String id;

		private List<VerificationMethod> verificationMethod;

		private List<String> assertionMethod;

		DidBuilder() {
		}

		public DidBuilder id(String id) {
			this.id = id;
			return this;
		}

		public DidBuilder verificationMethod(List<VerificationMethod> verificationMethod) {
			this.verificationMethod = verificationMethod;
			return this;
		}

		public DidBuilder assertionMethod(List<String> assertionMethod) {
			this.assertionMethod = assertionMethod;
			return this;
		}

		public Did build() {
			return new Did(id, verificationMethod, assertionMethod);
		}

		@Override
		public String toString() {
			return "DidBuilder{" +
					"id='" + id + '\'' +
					", verificationMethod=" + verificationMethod +
					", assertionMethod=" + assertionMethod +
					'}';
		}
	}
}
