package com.dawex.sigourney.trustframework.vc.model.v2210.dataresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;

import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format.DATA_RESOURCE_PRODUCED_BY;

public class ProducedBy {
	@JsonLdProperty(value = "id", formatName = DATA_RESOURCE_PRODUCED_BY)
	private final String id;

	public ProducedBy(String id) {
		this.id = id;
	}

	public static ProducedByBuilder builder() {
		return new ProducedByBuilder();
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
		ProducedBy that = (ProducedBy) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "ProducedBy{" +
				"id='" + id + '\'' +
				'}';
	}

	public static class ProducedByBuilder {
		private String id;

		ProducedByBuilder() {
		}

		public ProducedByBuilder id(String id) {
			this.id = id;
			return this;
		}

		public ProducedBy build() {
			return new ProducedBy(id);
		}

		@Override
		public String toString() {
			return "ProducedByBuilder{" +
					"id='" + id + '\'' +
					'}';
		}
	}
}
