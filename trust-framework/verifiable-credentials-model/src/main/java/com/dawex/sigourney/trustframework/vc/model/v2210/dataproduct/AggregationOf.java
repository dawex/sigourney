package com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;

import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format.DATA_PRODUCT_AGGREGATION_OF;

public class AggregationOf {
	@JsonLdProperty(value = "id", formatName = DATA_PRODUCT_AGGREGATION_OF)
	private final String id;

	public AggregationOf(String id) {
		this.id = id;
	}

	public static AggregationOfBuilder builder() {
		return new AggregationOfBuilder();
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
		AggregationOf that = (AggregationOf) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "AggregationOf{" +
				"id='" + id + '\'' +
				'}';
	}

	public static class AggregationOfBuilder {
		private String id;

		AggregationOfBuilder() {
		}

		public AggregationOfBuilder id(String id) {
			this.id = id;
			return this;
		}

		public AggregationOf build() {
			return new AggregationOf(id);
		}

		@Override
		public String toString() {
			return "AggregationOfBuilder{" +
					"id='" + id + '\'' +
					'}';
		}
	}
}
