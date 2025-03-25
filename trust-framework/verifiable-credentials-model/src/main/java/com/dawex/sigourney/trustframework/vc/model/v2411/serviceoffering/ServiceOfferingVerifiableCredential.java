package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdEmbeddedContext;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.GAIAX_DEVELOPMENT;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS_V2;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.DCAT_IRI;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.DCAT_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.DC_TERMS_IRI;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.DC_TERMS_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.SCHEMA_IRI;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.SCHEMA_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.VCARD_IRI;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.VCARD_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.SERVICE_OFFERING_VERIFIABLE_CREDENTIAL;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/ServiceOffering/">Gaia-X Service Characteristics : ServiceOffering</a>
 */
@JsonLdContexts(
		addBaseContext = true,
		referencedContexts = {
				VERIFIABLE_CREDENTIALS_V2,
				GAIAX_DEVELOPMENT
		},
		embeddedContexts = {
				@JsonLdEmbeddedContext(term = DCAT_NS, iri = DCAT_IRI),
				@JsonLdEmbeddedContext(term = DC_TERMS_NS, iri = DC_TERMS_IRI),
				@JsonLdEmbeddedContext(term = SCHEMA_NS, iri = SCHEMA_IRI),
				@JsonLdEmbeddedContext(term = VCARD_NS, iri = VCARD_IRI),
		})
@JsonLdType({"VerifiableCredential", "gx:ServiceOffering"})
public class ServiceOfferingVerifiableCredential extends BaseVerifiableCredential<ServiceOfferingCredentialSubject> {

	public ServiceOfferingVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			ServiceOfferingCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<ServiceOfferingVerifiableCredential, ServiceOfferingCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public ServiceOfferingVerifiableCredential build() {
				return new ServiceOfferingVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = SERVICE_OFFERING_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
