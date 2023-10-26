package com.dawex.sigourney.trustframework.vc.model.shared;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;

import java.util.List;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.DID;

@JsonLdContexts(referencedContexts = DID)
public class Did {

	@JsonLdProperty(value = "id")
	private final String id;

	@JsonLdProperty(value = "verificationMethod")
	private final List<VerificationMethod> verificationMethod;

	public Did(String id, List<VerificationMethod> verificationMethod) {
		this.id = id;
		this.verificationMethod = verificationMethod;
	}

	public String getId() {
		return id;
	}

	public List<VerificationMethod> getVerificationMethod() {
		return verificationMethod;
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
		return Objects.equals(id, did.id) && Objects.equals(verificationMethod, did.verificationMethod);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, verificationMethod);
	}

	@Override
	public String toString() {
		return "Did{" +
				"id='" + id + '\'' +
				", verificationMethod=" + verificationMethod +
				'}';
	}

	public static class DidBuilder {

		private String id;

		private List<VerificationMethod> verificationMethod;

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

		public Did build() {
			return new Did(id, verificationMethod);
		}

		@Override
		public String toString() {
			return "DidBuilder{" +
					"id='" + id + '\'' +
					", verificationMethod=" + verificationMethod +
					'}';
		}
	}
}
