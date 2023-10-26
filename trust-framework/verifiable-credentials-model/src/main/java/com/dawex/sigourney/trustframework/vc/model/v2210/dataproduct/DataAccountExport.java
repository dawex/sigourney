package com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.model.v2210.Namespace;

import java.util.Objects;

public class DataAccountExport {
	@JsonLdProperty(value = "requestType", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final String requestType;

	@JsonLdProperty(value = "accessType", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final String accessType;

	@JsonLdProperty(value = "formatType", namespace = Namespace.GAIAX_TRUST_FRAMEWORK_NS, mandatory = true)
	private final String formatType;

	public DataAccountExport(String requestType, String accessType, String formatType) {
		this.requestType = requestType;
		this.accessType = accessType;
		this.formatType = formatType;
	}

	public static DataAccountExportBuilder builder() {
		return new DataAccountExportBuilder();
	}

	public String getRequestType() {
		return requestType;
	}

	public String getAccessType() {
		return accessType;
	}

	public String getFormatType() {
		return formatType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DataAccountExport that = (DataAccountExport) o;
		return Objects.equals(requestType, that.requestType) && Objects.equals(accessType, that.accessType) &&
				Objects.equals(formatType, that.formatType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(requestType, accessType, formatType);
	}

	@Override
	public String toString() {
		return "DataAccountExport{" +
				"requestType='" + requestType + '\'' +
				", accessType='" + accessType + '\'' +
				", formatType='" + formatType + '\'' +
				'}';
	}

	public static class DataAccountExportBuilder {
		private String requestType;

		private String accessType;

		private String formatType;

		DataAccountExportBuilder() {
		}

		public DataAccountExportBuilder requestType(String requestType) {
			this.requestType = requestType;
			return this;
		}

		public DataAccountExportBuilder accessType(String accessType) {
			this.accessType = accessType;
			return this;
		}

		public DataAccountExportBuilder formatType(String formatType) {
			this.formatType = formatType;
			return this;
		}

		public DataAccountExport build() {
			return new DataAccountExport(requestType, accessType, formatType);
		}

		@Override
		public String toString() {
			return "DataAccountExportBuilder{" +
					"requestType='" + requestType + '\'' +
					", accessType='" + accessType + '\'' +
					", formatType='" + formatType + '\'' +
					'}';
		}
	}
}
