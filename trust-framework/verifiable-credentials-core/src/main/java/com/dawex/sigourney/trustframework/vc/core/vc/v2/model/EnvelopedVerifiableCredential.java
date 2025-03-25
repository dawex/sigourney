package com.dawex.sigourney.trustframework.vc.core.vc.v2.model;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;

import java.util.Objects;
import java.util.Optional;

@JsonLdType("EnvelopedVerifiableCredential")
public class EnvelopedVerifiableCredential implements VerifiableCredential {
	@JsonLdProperty(value = "id")
	private final String id;

	public EnvelopedVerifiableCredential(String id) {
		this.id = id;
	}

	public static EnvelopedVerifiableCredentialBuilder builder() {
		return new EnvelopedVerifiableCredentialBuilder();
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		EnvelopedVerifiableCredential that = (EnvelopedVerifiableCredential) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public String toString() {
		return "EnvelopedVerifiableCredential{" +
				"id='" + id + '\'' +
				'}';
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public static class EnvelopedVerifiableCredentialBuilder {
		private String mediaType;

		private String securedCredential;

		EnvelopedVerifiableCredentialBuilder() {
		}

		public EnvelopedVerifiableCredentialBuilder mediaType(String mediaType) {
			this.mediaType = mediaType;
			return this;
		}

		public EnvelopedVerifiableCredentialBuilder securedCredential(String securedCredential) {
			this.securedCredential = securedCredential;
			return this;
		}

		public EnvelopedVerifiableCredential build() {
			final String id = Optional.ofNullable(this.mediaType)
					.map(m -> "data:%s,%s".formatted(m, securedCredential))
					.orElse(securedCredential);
			return new EnvelopedVerifiableCredential(id);
		}

		@Override
		public String toString() {
			return "EnvelopedVerifiableCredentialBuilder{" +
					"mediaType='" + mediaType + '\'' +
					"securedCredential='" + securedCredential + '\'' +
					'}';
		}
	}
}
