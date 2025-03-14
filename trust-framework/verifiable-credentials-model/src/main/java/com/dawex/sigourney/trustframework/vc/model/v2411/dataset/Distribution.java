package com.dawex.sigourney.trustframework.vc.model.v2411.dataset;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2411.Namespace;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.XsdDataType.XSD_DATE;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.XsdDataType.XSD_DATETIME;

@JsonLdType({"dcat:Distribution"})
public class Distribution {
	@JsonLdProperty(value = "title", namespace = Namespace.DC_TERMS_NS, mandatory = true)
	private final String title;

	@JsonLdProperty(value = "format", namespace = Namespace.DC_TERMS_NS, mandatory = true)
	private final String format;

	@JsonLdProperty(value = "byteSize", namespace = Namespace.DCAT_NS)
	private final String byteSize;

	@JsonLdProperty(value = "hash", namespace = Namespace.GAIAX_NS)
	private final String hash;

	@JsonLdProperty(value = "hashAlgorithm", namespace = Namespace.GAIAX_NS)
	private final String hashAlgorithm;

	@JsonLdProperty(value = "location", namespace = Namespace.GAIAX_NS)
	private final Collection<String> locations;

	@JsonLdProperty(value = "issued", namespace = Namespace.DC_TERMS_NS, type = XSD_DATE)
	private final LocalDate issued;

	@JsonLdProperty(value = "expirationDateTime", namespace = Namespace.GAIAX_NS, type = XSD_DATETIME)
	private final ZonedDateTime expirationDateTime;

	public Distribution(String title, String format, String byteSize, String hash, String hashAlgorithm, Collection<String> locations,
			LocalDate issued, ZonedDateTime expirationDateTime) {
		this.title = title;
		this.format = format;
		this.byteSize = byteSize;
		this.hash = hash;
		this.hashAlgorithm = hashAlgorithm;
		this.locations = locations;
		this.issued = issued;
		this.expirationDateTime = expirationDateTime;
	}

	public static DistributionBuilder builder() {
		return new DistributionBuilder();
	}

	public String getTitle() {
		return title;
	}

	public String getFormat() {
		return format;
	}

	public String getByteSize() {
		return byteSize;
	}

	public String getHash() {
		return hash;
	}

	public String getHashAlgorithm() {
		return hashAlgorithm;
	}

	public Collection<String> getLocations() {
		return locations;
	}

	public LocalDate getIssued() {
		return issued;
	}

	public ZonedDateTime getExpirationDateTime() {
		return expirationDateTime;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Distribution that = (Distribution) o;
		return Objects.equals(title, that.title) && Objects.equals(format, that.format) &&
				Objects.equals(byteSize, that.byteSize) && Objects.equals(hash, that.hash) &&
				Objects.equals(hashAlgorithm, that.hashAlgorithm) && Objects.equals(locations, that.locations) &&
				Objects.equals(issued, that.issued) && Objects.equals(expirationDateTime, that.expirationDateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, format, byteSize, hash, hashAlgorithm, locations, issued, expirationDateTime);
	}

	@Override
	public String toString() {
		return "Distribution{" +
				"title='" + title + '\'' +
				", format='" + format + '\'' +
				", byteSize=" + byteSize +
				", hash='" + hash + '\'' +
				", hashAlgorithm='" + hashAlgorithm + '\'' +
				", locations=" + locations +
				", issued=" + issued +
				", expirationDateTime=" + expirationDateTime +
				'}';
	}

	public static class DistributionBuilder {
		private String title;

		private String format;

		private String byteSize;

		private String hash;

		private String hashAlgorithm;

		private Collection<String> locations;

		private LocalDate issued;

		private ZonedDateTime expirationDateTime;

		DistributionBuilder() {
		}

		public DistributionBuilder title(String title) {
			this.title = title;
			return this;
		}

		public DistributionBuilder format(String format) {
			this.format = format;
			return this;
		}

		public DistributionBuilder byteSize(String byteSize) {
			this.byteSize = byteSize;
			return this;
		}

		public DistributionBuilder hash(String hash) {
			this.hash = hash;
			return this;
		}

		public DistributionBuilder hashAlgorithm(String hashAlgorithm) {
			this.hashAlgorithm = hashAlgorithm;
			return this;
		}

		public DistributionBuilder locations(Collection<String> locations) {
			this.locations = locations;
			return this;
		}

		public DistributionBuilder issued(LocalDate issued) {
			this.issued = issued;
			return this;
		}

		public DistributionBuilder expirationDateTime(ZonedDateTime expirationDateTime) {
			this.expirationDateTime = expirationDateTime;
			return this;
		}

		public Distribution build() {
			return new Distribution(title, format, byteSize, hash, hashAlgorithm, locations, issued, expirationDateTime);
		}

		@Override
		public String toString() {
			return "DistributionBuilder{" +
					"title='" + title + '\'' +
					", format='" + format + '\'' +
					", byteSize=" + byteSize +
					", hash='" + hash + '\'' +
					", hashAlgorithm='" + hashAlgorithm + '\'' +
					", locations=" + locations +
					", issued=" + issued +
					", expirationDateTime=" + expirationDateTime +
					'}';
		}
	}
}
