package com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.CredentialSubject;

import java.util.Collection;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.XsdDataType.XSD_URI;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.GAIAX_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.LEGAL_DOCUMENT_CREDENTIAL_SUBJECT;

public class LegalDocumentCredentialSubject implements CredentialSubject {
	@JsonLdProperty(value = "id", formatName = LEGAL_DOCUMENT_CREDENTIAL_SUBJECT)
	private final String id;

	@JsonLdProperty(value = "url", namespace = GAIAX_NS, type = XSD_URI, mandatory = true)
	private final String url;

	@JsonLdProperty(value = "mimeTypes", namespace = GAIAX_NS)
	private final Collection<String> mimeTypes;

	@JsonLdProperty(value = "involvedParties", namespace = GAIAX_NS)
	private final Collection<InvolvedParty> involvedParties;

	public LegalDocumentCredentialSubject(String id, String url, Collection<String> mimeTypes, Collection<InvolvedParty> involvedParties) {
		this.id = id;
		this.url = url;
		this.mimeTypes = mimeTypes;
		this.involvedParties = involvedParties;
	}

	public static LegalDocumentBuilder<? extends LegalDocumentCredentialSubject> builder() {
		return new LegalDocumentBuilder<>() {
			@Override
			public LegalDocumentCredentialSubject build() {
				return new LegalDocumentCredentialSubject(id, url, mimeTypes, involvedParties);
			}
		};
	}

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public Collection<String> getMimeTypes() {
		return mimeTypes;
	}

	public Collection<InvolvedParty> getInvolvedParties() {
		return involvedParties;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LegalDocumentCredentialSubject that = (LegalDocumentCredentialSubject) o;
		return Objects.equals(id, that.id) && Objects.equals(url, that.url) &&
				Objects.equals(mimeTypes, that.mimeTypes) && Objects.equals(involvedParties, that.involvedParties);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, url, mimeTypes, involvedParties);
	}

	@Override
	public String toString() {
		return "LegalDocumentCredentialSubject{" +
				"id='" + id + '\'' +
				", url='" + url + '\'' +
				", mimeTypes=" + mimeTypes +
				", involvedParties=" + involvedParties +
				'}';
	}

	public abstract static class LegalDocumentBuilder<T extends LegalDocumentCredentialSubject> {
		protected String id;

		protected String url;

		protected Collection<String> mimeTypes;

		protected Collection<InvolvedParty> involvedParties;

		LegalDocumentBuilder() {
		}

		public LegalDocumentBuilder<T> id(String id) {
			this.id = id;
			return this;
		}

		public LegalDocumentBuilder<T> url(String url) {
			this.url = url;
			return this;
		}

		public LegalDocumentBuilder<T> mimeTypes(Collection<String> mimeTypes) {
			this.mimeTypes = mimeTypes;
			return this;
		}

		public LegalDocumentBuilder<T> involvedParties(Collection<InvolvedParty> involvedParties) {
			this.involvedParties = involvedParties;
			return this;
		}

		public abstract T build();

		@Override
		public String toString() {
			return "LegalDocumentBuilder{" +
					"id='" + id + '\'' +
					", url='" + url + '\'' +
					", mimeTypes=" + mimeTypes +
					", involvedParties=" + involvedParties +
					'}';
		}
	}
}
