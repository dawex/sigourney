package com.dawex.sigourney.trustframework.vc.model.v2411.common;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;

import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.XsdDataType.XSD_URI;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.GAIAX_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.TERMS_AND_CONDITIONS_URL;

@JsonLdType("gx:TermsAndConditions")
public class TermsAndConditions {
	@JsonLdProperty(value = "url", namespace = GAIAX_NS, type = XSD_URI, formatName = TERMS_AND_CONDITIONS_URL, mandatory = true)
	private final String url;

	@JsonLdProperty(value = "hash", namespace = GAIAX_NS, mandatory = true)
	private final String hash;

	public TermsAndConditions(String url, String hash) {
		this.url = url;
		this.hash = hash;
	}

	public static TermsAndConditionsBuilder builder() {
		return new TermsAndConditionsBuilder();
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
		TermsAndConditions that = (TermsAndConditions) o;
		return Objects.equals(url, that.url) && Objects.equals(hash, that.hash);
	}

	@Override
	public int hashCode() {
		return Objects.hash(url, hash);
	}

	@Override
	public String toString() {
		return "TermsAndConditions{" +
				"url='" + url + '\'' +
				", hash='" + hash + '\'' +
				'}';
	}

	public static class TermsAndConditionsBuilder {
		private String url;

		private String hash;

		TermsAndConditionsBuilder() {
		}

		public TermsAndConditionsBuilder url(String url) {
			this.url = url;
			return this;
		}

		public TermsAndConditionsBuilder hash(String hash) {
			this.hash = hash;
			return this;
		}

		public TermsAndConditions build() {
			return new TermsAndConditions(url, hash);
		}

		@Override
		public String toString() {
			return "TermsAndConditionsBuilder{" +
					"url='" + url + '\'' +
					", hash='" + hash + '\'' +
					'}';
		}
	}
}
