package com.dawex.sigourney.trustframework.vc.model.v2411.common;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2411.Namespace;

import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.XsdDataType.XSD_URI;

@JsonLdType("gx:ContactInformation")
public class ContactInformation {
	@JsonLdProperty(value = "postalAddress", namespace = Namespace.GAIAX_NS)
	private final Address postalAddress;

	@JsonLdProperty(value = "email", namespace = Namespace.GAIAX_NS)
	private final String email;

	@JsonLdProperty(value = "phoneNumber", namespace = Namespace.GAIAX_NS)
	private final String phoneNumber;

	@JsonLdProperty(value = "url", namespace = Namespace.GAIAX_NS, type = XSD_URI, mandatory = true)
	private final String url;

	public ContactInformation(Address postalAddress, String email, String phoneNumber, String url) {
		this.postalAddress = postalAddress;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.url = url;
	}

	public static ContactInformationBuilder builder() {
		return new ContactInformationBuilder();
	}

	public Address getPostalAddress() {
		return postalAddress;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ContactInformation that = (ContactInformation) o;
		return Objects.equals(postalAddress, that.postalAddress) && Objects.equals(email, that.email) &&
				Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(url, that.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postalAddress, email, phoneNumber, url);
	}

	@Override
	public String toString() {
		return "ContactInformation{" +
				"postalAddress=" + postalAddress +
				", email='" + email + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", url='" + url + '\'' +
				'}';
	}

	public static class ContactInformationBuilder {
		private Address postalAddress;

		private String email;

		private String phoneNumber;

		private String url;

		ContactInformationBuilder() {
		}

		public ContactInformationBuilder postalAddress(Address postalAddress) {
			this.postalAddress = postalAddress;
			return this;
		}

		public ContactInformationBuilder email(String email) {
			this.email = email;
			return this;
		}

		public ContactInformationBuilder phoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public ContactInformationBuilder url(String url) {
			this.url = url;
			return this;
		}

		public ContactInformation build() {
			return new ContactInformation(postalAddress, email, phoneNumber, url);
		}

		@Override
		public String toString() {
			return "ContactInformationBuilder{" +
					"postalAddress=" + postalAddress +
					", email='" + email + '\'' +
					", phoneNumber='" + phoneNumber + '\'' +
					", url='" + url + '\'' +
					'}';
		}
	}
}
