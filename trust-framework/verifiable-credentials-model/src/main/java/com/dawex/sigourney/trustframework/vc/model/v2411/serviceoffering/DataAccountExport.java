package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;

import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.GAIAX_NS;

@JsonLdType("gx:DataAccountExport")
public class DataAccountExport {
	@JsonLdProperty(value = "requestType", namespace = GAIAX_NS, mandatory = true)
	private final RequestType requestType;

	@JsonLdProperty(value = "accessType", namespace = GAIAX_NS, mandatory = true)
	private final AccessType accessType;

	@JsonLdProperty(value = "formatType", namespace = GAIAX_NS, mandatory = true)
	private final MimeType formatType;

	public DataAccountExport(RequestType requestType, AccessType accessType, MimeType formatType) {
		this.requestType = requestType;
		this.accessType = accessType;
		this.formatType = formatType;
	}

	public static DataAccountExportBuilder builder() {
		return new DataAccountExportBuilder();
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public AccessType getAccessType() {
		return accessType;
	}

	public MimeType getFormatType() {
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
		private RequestType requestType;

		private AccessType accessType;

		private MimeType formatType;

		DataAccountExportBuilder() {
		}

		public DataAccountExportBuilder requestType(RequestType requestType) {
			this.requestType = requestType;
			return this;
		}

		public DataAccountExportBuilder accessType(AccessType accessType) {
			this.accessType = accessType;
			return this;
		}

		public DataAccountExportBuilder formatType(MimeType formatType) {
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
