package com.dawex.sigourney.trustframework.vc.model.v2411.common;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.CredentialSubject;

import static com.dawex.sigourney.trustframework.vc.model.v2411.Namespace.SCHEMA_NS;

/**
 * Root class for Gaia-X entity credential subject
 *
 * @see <a href="https://docs.gaia-x.eu/ontology/development/classes/GaiaXEntity/">Gaia-X Service Characteristics : Gaia-X Entity</a>
 */
public abstract class GaiaxCredentialSubject implements CredentialSubject {

	@JsonLdProperty(value = "name", namespace = SCHEMA_NS)
	protected final String name;

	@JsonLdProperty(value = "description", namespace = SCHEMA_NS)
	protected final String description;

	protected GaiaxCredentialSubject(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
