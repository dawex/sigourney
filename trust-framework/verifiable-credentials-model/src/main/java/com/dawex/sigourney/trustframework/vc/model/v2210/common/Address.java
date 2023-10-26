package com.dawex.sigourney.trustframework.vc.model.v2210.common;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2210.Namespace;

import java.util.Objects;

/**
 * @see <a href="https://www.w3.org/2006/vcard/ns#Address">Address Schema</a>
 */
public class Address {
	@JsonLdProperty(value = "street-address", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String streetAddress;

	@JsonLdProperty(value = "postal-code", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String postalCode;

	@JsonLdProperty(value = "region", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String region;

	@JsonLdProperty(value = "locality", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String locality;

	@JsonLdProperty(value = "country-name", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String countryName;

	@JsonLdProperty(value = "countrySubdivisionCode", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS)
	private final String countrySubdivisionCode;

	public Address(String streetAddress, String postalCode, String region, String locality, String countryName, String countrySubdivisionCode) {
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.region = region;
		this.locality = locality;
		this.countryName = countryName;
		this.countrySubdivisionCode = countrySubdivisionCode;
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

	public String getCountrySubdivisionCode() {
		return countrySubdivisionCode;
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
				Objects.equals(this.countrySubdivisionCode, that.countrySubdivisionCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(streetAddress, postalCode, region, locality, countryName, countrySubdivisionCode);
	}

	@Override
	public String toString() {
		return "Address[" +
				"streetAddress=" + streetAddress + ", " +
				"postalCode=" + postalCode + ", " +
				"region=" + region + ", " +
				"locality=" + locality + ", " +
				"countryName=" + countryName + ", " +
				"countrySubdivisionCode=" + countrySubdivisionCode + ']';
	}

	public static class AddressBuilder {
		private String streetAddress;

		private String postalCode;

		private String region;

		private String locality;

		private String countryName;

		private String countrySubdivisionCode;

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

		public AddressBuilder countrySubdivisionCode(String countrySubdivisionCode) {
			this.countrySubdivisionCode = countrySubdivisionCode;
			return this;
		}

		public Address build() {
			return new Address(streetAddress, postalCode, region, locality, countryName, countrySubdivisionCode);
		}

		@Override
		public String toString() {
			return "AddressBuilder{" +
					"streetAddress='" + streetAddress + '\'' +
					", postalCode='" + postalCode + '\'' +
					", region='" + region + '\'' +
					", locality='" + locality + '\'' +
					", countryName='" + countryName + '\'' +
					", countrySubdivisionCode='" + countrySubdivisionCode + '\'' +
					'}';
		}
	}
}
