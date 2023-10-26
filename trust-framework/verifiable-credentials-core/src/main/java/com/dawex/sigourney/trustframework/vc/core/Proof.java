package com.dawex.sigourney.trustframework.vc.core;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;

import java.time.ZonedDateTime;

/**
 * @see <a href="https://w3id.org/security#proof">Proof Schema</a>
 */
public record Proof(@JsonLdProperty(value = "type") String type,
                    @JsonLdProperty(value = "created") ZonedDateTime created,
                    @JsonLdProperty(value = "proofPurpose") String proofPurpose,
                    @JsonLdProperty(value = "verificationMethod") String verificationMethod,
                    @JsonLdProperty(value = "jws") String jws) {

	public static ProofBuilder builder() {
		return new ProofBuilder();
	}

	public static class ProofBuilder {
		private String type;

		private ZonedDateTime created;

		private String proofPurpose;

		private String verificationMethod;

		private String jws;

		ProofBuilder() {
		}

		public ProofBuilder type(String type) {
			this.type = type;
			return this;
		}

		public ProofBuilder created(ZonedDateTime created) {
			this.created = created;
			return this;
		}

		public ProofBuilder proofPurpose(String proofPurpose) {
			this.proofPurpose = proofPurpose;
			return this;
		}

		public ProofBuilder verificationMethod(String verificationMethod) {
			this.verificationMethod = verificationMethod;
			return this;
		}

		public ProofBuilder jws(String jws) {
			this.jws = jws;
			return this;
		}

		public Proof build() {
			return new Proof(type, created, proofPurpose, verificationMethod, jws);
		}

		@Override
		public String toString() {
			return "ProofBuilder{" +
					"type='" + type + '\'' +
					", created=" + created +
					", proofPurpose='" + proofPurpose + '\'' +
					", verificationMethod='" + verificationMethod + '\'' +
					", jws='" + jws + '\'' +
					'}';
		}
	}
}
