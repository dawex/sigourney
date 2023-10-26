package com.dawex.sigourney.trustframework.vc.model.v2210.dataresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2210.Namespace;

import java.util.Objects;

public class Location {
	@JsonLdProperty(value = "dataCenterLocation", namespace = Namespace.DAWEX_NS)
	private final String dataCenterLocation;

	public Location(String dataCenterLocation) {
		this.dataCenterLocation = dataCenterLocation;
	}

	public static LocationBuilder builder() {
		return new LocationBuilder();
	}

	public String getDataCenterLocation() {
		return dataCenterLocation;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		var that = (Location) obj;
		return Objects.equals(this.dataCenterLocation, that.dataCenterLocation);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataCenterLocation);
	}

	@Override
	public String toString() {
		return "Location[" +
				"dataCenterLocation=" + dataCenterLocation + ']';
	}

	public static class LocationBuilder {
		private String dataCenterLocation;

		LocationBuilder() {
		}

		public LocationBuilder dataCenterLocation(String dataCenterLocation) {
			this.dataCenterLocation = dataCenterLocation;
			return this;
		}

		public Location build() {
			return new Location(dataCenterLocation);
		}

		@Override
		public String toString() {
			return "LocationBuilder{" +
					"dataCenterLocation='" + dataCenterLocation + '\'' +
					'}';
		}
	}
}
