package com.dawex.sigourney.trustframework.vc.model.v2411.physicalresource;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdEmbeddedContext;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.BaseVerifiableCredentialBuilder;

import java.time.ZonedDateTime;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.GAIAX_DEVELOPMENT;
import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.VERIFIABLE_CREDENTIALS_V2;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.SCHEMA_IRI;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.SCHEMA_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.VCARD_IRI;
import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.VCARD_NS;
import static com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format.PHYSICAL_RESOURCE_VERIFIABLE_CREDENTIAL;

/**
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/PhysicalResource/">Gaia-X Service Characteristics : Physical Resource</a>
 */
@JsonLdContexts(
		addBaseContext = true,
		referencedContexts = {
				VERIFIABLE_CREDENTIALS_V2,
				GAIAX_DEVELOPMENT
		},
		embeddedContexts = {
				@JsonLdEmbeddedContext(term = SCHEMA_NS, iri = SCHEMA_IRI),
				@JsonLdEmbeddedContext(term = VCARD_NS, iri = VCARD_IRI),
		})
@JsonLdType({"VerifiableCredential", "gx:PhysicalResource"})
public class PhysicalResourceVerifiableCredential extends BaseVerifiableCredential<PhysicalResourceCredentialSubject> {

	public PhysicalResourceVerifiableCredential(String id, String issuer, ZonedDateTime validFrom, ZonedDateTime validUntil,
			PhysicalResourceCredentialSubject credentialSubject) {
		super(id, issuer, validFrom, validUntil, credentialSubject);
	}

	public static BaseVerifiableCredentialBuilder<PhysicalResourceVerifiableCredential, PhysicalResourceCredentialSubject> builder() {
		return new BaseVerifiableCredentialBuilder<>() {
			@Override
			public PhysicalResourceVerifiableCredential build() {
				return new PhysicalResourceVerifiableCredential(id, issuer, validFrom, validUntil, credentialSubject);
			}
		};
	}

	@JsonLdProperty(value = "id", formatName = PHYSICAL_RESOURCE_VERIFIABLE_CREDENTIAL)
	@Override
	public String getId() {
		return super.getId();
	}
}
