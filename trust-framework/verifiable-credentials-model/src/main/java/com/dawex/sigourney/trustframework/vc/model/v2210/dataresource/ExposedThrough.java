package com.dawex.sigourney.trustframework.vc.model.v2210.dataresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format;

import java.util.Objects;

public class ExposedThrough {
	@JsonLdProperty(value = "id", formatName = Format.DATA_RESOURCE_EXPOSED_THROUGH)
	private final String id;

	public ExposedThrough(String id) {
		this.id = id;
	}

	public static ExposedThroughBuilder builder() {
		return new ExposedThroughBuilder();
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
		ExposedThrough that = (ExposedThrough) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "ExposedThrough{" +
				"id='" + id + '\'' +
				'}';
	}

	public static class ExposedThroughBuilder {
		private String id;

		ExposedThroughBuilder() {
		}

		public ExposedThroughBuilder id(String id) {
			this.id = id;
			return this;
		}

		public ExposedThrough build() {
			return new ExposedThrough(id);
		}

		@Override
		public String toString() {
			return "ExposedThroughBuilder{" +
					"id='" + id + '\'' +
					'}';
		}
	}
}
