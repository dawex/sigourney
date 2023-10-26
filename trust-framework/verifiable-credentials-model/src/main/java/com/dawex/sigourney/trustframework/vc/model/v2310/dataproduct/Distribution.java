package com.dawex.sigourney.trustframework.vc.model.v2310.dataproduct;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2310.serialization.Format;

import java.util.List;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2310.Namespace.DCAT_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2310.Namespace.DC_TERMS_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2310.Namespace.GAIAX_TRUST_FRAMEWORK_NS;

public class Distribution {
	@JsonLdProperty(value = "title", namespace = DC_TERMS_NS, mandatory = true)
	private final String title;

	@JsonLdProperty(value = "exposedThrough", namespace = GAIAX_TRUST_FRAMEWORK_NS, formatName = Format.DATA_PRODUCT_EXPOSED_THROUGH, mandatory = true)
	private final List<String> exposedThrough;

	@JsonLdProperty(value = "format", namespace = DC_TERMS_NS, mandatory = true)
	private final String format;

	@JsonLdProperty(value = "byteSize", namespace = DCAT_NS)
	private final Long byteSize;

	@JsonLdProperty(value = "hash", namespace = GAIAX_TRUST_FRAMEWORK_NS)
	private final String hash;

	@JsonLdProperty(value = "hashAlgorithm", namespace = GAIAX_TRUST_FRAMEWORK_NS)
	private final String hashAlgorithm;

	@JsonLdProperty(value = "location", namespace = GAIAX_TRUST_FRAMEWORK_NS)
	private final Location location;

	public Distribution(String title, List<String> exposedThrough, String format, Long byteSize, String hash, String hashAlgorithm,
			Location location) {
		this.title = title;
		this.exposedThrough = exposedThrough;
		this.format = format;
		this.byteSize = byteSize;
		this.hash = hash;
		this.hashAlgorithm = hashAlgorithm;
		this.location = location;
	}

	public static DistributionBuilder builder() {
		return new DistributionBuilder();
	}

	public String getTitle() {
		return title;
	}

	public List<String> getExposedThrough() {
		return exposedThrough;
	}

	public String getFormat() {
		return format;
	}

	public Long getByteSize() {
		return byteSize;
	}

	public String getHash() {
		return hash;
	}

	public String getHashAlgorithm() {
		return hashAlgorithm;
	}

	public Location getLocation() {
		return location;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Distribution that = (Distribution) o;
		return Objects.equals(title, that.title) && Objects.equals(exposedThrough, that.exposedThrough) &&
				Objects.equals(format, that.format) && Objects.equals(byteSize, that.byteSize) &&
				Objects.equals(hash, that.hash) && Objects.equals(hashAlgorithm, that.hashAlgorithm) &&
				Objects.equals(location, that.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, exposedThrough, format, byteSize, hash, hashAlgorithm, location);
	}

	@Override
	public String toString() {
		return "Distribution{" +
				"title='" + title + '\'' +
				", exposedThrough=" + exposedThrough +
				", format='" + format + '\'' +
				", byteSize=" + byteSize +
				", hash='" + hash + '\'' +
				", hashAlgorithm='" + hashAlgorithm + '\'' +
				", location=" + location +
				'}';
	}

	public static class DistributionBuilder {
		private String title;

		private List<String> exposedThrough;

		private String format;

		private Long byteSize;

		private String hash;

		private String hashAlgorithm;

		private Location location;

		DistributionBuilder() {
		}

		public DistributionBuilder title(String title) {
			this.title = title;
			return this;
		}

		public DistributionBuilder exposedThrough(List<String> exposedThrough) {
			this.exposedThrough = exposedThrough;
			return this;
		}

		public DistributionBuilder format(String format) {
			this.format = format;
			return this;
		}

		public DistributionBuilder byteSize(Long byteSize) {
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

		public DistributionBuilder location(Location location) {
			this.location = location;
			return this;
		}

		public Distribution build() {
			return new Distribution(title, exposedThrough, format, byteSize, hash, hashAlgorithm, location);
		}

		@Override
		public String toString() {
			return "DistributionBuilder{" +
					"title='" + title + '\'' +
					", exposedThrough=" + exposedThrough +
					", format='" + format + '\'' +
					", byteSize=" + byteSize +
					", hash='" + hash + '\'' +
					", hashAlgorithm='" + hashAlgorithm + '\'' +
					", location=" + location +
					'}';
		}
	}
}
