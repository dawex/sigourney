package com.dawex.sigourney.trustframework.vc.model.v2411.common;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2411.Namespace;

import java.util.Objects;

@JsonLdType("gx:Address")
public class Address {
	@JsonLdProperty(value = "street-address", namespace = Namespace.VCARD_NS)
	private final String streetAddress;

	@JsonLdProperty(value = "postal-code", namespace = Namespace.VCARD_NS)
	private final String postalCode;

	@JsonLdProperty(value = "region", namespace = Namespace.GAIAX_NS)
	private final String region;

	@JsonLdProperty(value = "locality", namespace = Namespace.VCARD_NS)
	private final String locality;

	@JsonLdProperty(value = "countryCode", namespace = Namespace.GAIAX_NS)
	private final String countryCode;

	@JsonLdProperty(value = "countryName", namespace = Namespace.GAIAX_NS)
	private final String countryName;

	public Address(String streetAddress, String postalCode, String region, String locality, String countryName, String countryCode) {
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.region = region;
		this.locality = locality;
		this.countryName = countryName;
		this.countryCode = countryCode;
	}

	public static AddressBuilder builder() {
		return new AddressBuilder();
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getRegion() {
		return region;
	}

	public String getLocality() {
		return locality;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		var that = (Address) obj;
		return Objects.equals(this.streetAddress, that.streetAddress) &&
				Objects.equals(this.postalCode, that.postalCode) &&
				Objects.equals(this.region, that.region) &&
				Objects.equals(this.locality, that.locality) &&
				Objects.equals(this.countryName, that.countryName) &&
				Objects.equals(this.countryCode, that.countryCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(streetAddress, postalCode, region, locality, countryName, countryCode);
	}

	@Override
	public String toString() {
		return "Address[" +
				"streetAddress=" + streetAddress + ", " +
				"postalCode=" + postalCode + ", " +
				"region=" + region + ", " +
				"locality=" + locality + ", " +
				"countryName=" + countryName + ", " +
				"countryCode=" + countryCode + ']';
	}

	public static class AddressBuilder {
		private String streetAddress;

		private String postalCode;

		private String region;

		private String locality;

		private String countryName;

		private String countryCode;

		AddressBuilder() {
		}

		public AddressBuilder streetAddress(String streetAddress) {
			this.streetAddress = streetAddress;
			return this;
		}

		public AddressBuilder postalCode(String postalCode) {
			this.postalCode = postalCode;
			return this;
		}

		public AddressBuilder region(String region) {
			this.region = region;
			return this;
		}

		public AddressBuilder locality(String locality) {
			this.locality = locality;
			return this;
		}

		public AddressBuilder countryName(String countryName) {
			this.countryName = countryName;
			return this;
		}

		public AddressBuilder countryCode(String countryCode) {
			this.countryCode = countryCode;
			return this;
		}

		public Address build() {
			return new Address(streetAddress, postalCode, region, locality, countryName, countryCode);
		}

		@Override
		public String toString() {
			return "AddressBuilder{" +
					"streetAddress='" + streetAddress + '\'' +
					", postalCode='" + postalCode + '\'' +
					", region='" + region + '\'' +
					", locality='" + locality + '\'' +
					", countryName='" + countryName + '\'' +
					", countryCode='" + countryCode + '\'' +
					'}';
		}
	}
}
