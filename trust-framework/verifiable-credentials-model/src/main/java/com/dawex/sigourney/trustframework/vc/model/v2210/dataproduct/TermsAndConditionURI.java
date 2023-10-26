package com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2210.Namespace;

import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format.DATA_PRODUCT_TERMS_AND_CONDITIONS_URI;

public class TermsAndConditionURI {
	@JsonLdProperty(value = "URL", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, formatName = DATA_PRODUCT_TERMS_AND_CONDITIONS_URI, mandatory = true)
	private final String url;

	@JsonLdProperty(value = "hash", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final String hash;

	public TermsAndConditionURI(String url, String hash) {
		this.url = url;
		this.hash = hash;
	}

	public static TermsAndConditionURIBuilder builder() {
		return new TermsAndConditionURIBuilder();
	}

	public String getUrl() {
		return url;
	}

	public String getHash() {
		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TermsAndConditionURI that = (TermsAndConditionURI) o;
		return Objects.equals(url, that.url) && Objects.equals(hash, that.hash);
	}

	@Override
	public int hashCode() {
		return Objects.hash(url, hash);
	}

	@Override
	public String toString() {
		return "TermsAndConditionURI{" +
				"url='" + url + '\'' +
				", hash='" + hash + '\'' +
				'}';
	}

	public static class TermsAndConditionURIBuilder {
		private String url;

		private String hash;

		TermsAndConditionURIBuilder() {
		}

		public TermsAndConditionURIBuilder url(String url) {
			this.url = url;
			return this;
		}

		public TermsAndConditionURIBuilder hash(String hash) {
			this.hash = hash;
			return this;
		}

		public TermsAndConditionURI build() {
			return new TermsAndConditionURI(url, hash);
		}

		@Override
		public String toString() {
			return "TermsAndConditionURIBuilder{" +
					"url='" + url + '\'' +
					", hash='" + hash + '\'' +
					'}';
		}
	}
}
