package com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;

import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format.DATA_PRODUCT_PROVIDED_BY;

public class ProvidedBy {
	@JsonLdProperty(value = "id", formatName = DATA_PRODUCT_PROVIDED_BY)
	private final String id;

	public ProvidedBy(String id) {
		this.id = id;
	}

	public static ProvidedByBuilder builder() {
		return new ProvidedByBuilder();
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProvidedBy that = (ProvidedBy) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "ProvidedBy{" +
				"id='" + id + '\'' +
				'}';
	}

	public static class ProvidedByBuilder {
		private String id;

		ProvidedByBuilder() {
		}

		public ProvidedByBuilder id(String id) {
			this.id = id;
			return this;
		}

		public ProvidedBy build() {
			return new ProvidedBy(id);
		}

		@Override
		public String toString() {
			return "ProvidedByBuilder{" +
					"id='" + id + '\'' +
					'}';
		}
	}
}
