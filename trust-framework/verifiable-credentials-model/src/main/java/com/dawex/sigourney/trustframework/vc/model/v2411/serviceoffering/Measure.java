package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.model.v2411.Namespace;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.LegalDocument;

import java.util.Collection;
import java.util.Objects;

@JsonLdType("gx:Measure")
public class Measure {
	@JsonLdProperty(value = "description", namespace = Namespace.SCHEMA_NS, mandatory = true)
	private final String description;

	@JsonLdProperty(value = "legalDocuments", namespace = Namespace.GAIAX_NS, mandatory = true)
	private final Collection<LegalDocument> legalDocuments;

	public Measure(String description, Collection<LegalDocument> legalDocuments) {
		this.description = description;
		this.legalDocuments = legalDocuments;
	}

	public static MeasureBuilder builder() {
		return new MeasureBuilder();
	}

	public String getDescription() {
		return description;
	}

	public Collection<LegalDocument> getLegalDocuments() {
		return legalDocuments;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Measure measure = (Measure) o;
		return Objects.equals(description, measure.description) && Objects.equals(legalDocuments, measure.legalDocuments);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, legalDocuments);
	}

	@Override
	public String toString() {
		return "Measure{" +
				"description='" + description + '\'' +
				", legalDocuments=" + legalDocuments +
				'}';
	}

	public static class MeasureBuilder {
		private String description;

		private Collection<LegalDocument> legalDocuments;

		MeasureBuilder() {
		}

		public MeasureBuilder description(String description) {
			this.description = description;
			return this;
		}

		public MeasureBuilder legalDocuments(Collection<LegalDocument> legalDocuments) {
			this.legalDocuments = legalDocuments;
			return this;
		}

		public Measure build() {
			return new Measure(description, legalDocuments);
		}

		@Override
		public String toString() {
			return "MeasureBuilder{" +
					"description='" + description + '\'' +
					", legalDocuments=" + legalDocuments +
					'}';
		}
	}
}
