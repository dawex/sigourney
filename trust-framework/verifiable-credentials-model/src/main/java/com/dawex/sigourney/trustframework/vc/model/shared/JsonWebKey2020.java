package com.dawex.sigourney.trustframework.vc.model.shared;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;

import java.util.Map;
import java.util.Objects;

import static com.dawex.sigourney.trustframework.vc.core.jsonld.ExternalContext.SECURITY_JWS_2020;

@JsonLdContexts(referencedContexts = SECURITY_JWS_2020)
@JsonLdType("JsonWebKey2020")
public class JsonWebKey2020 implements VerificationMethod {

	@JsonLdProperty(value = "id")
	private final String id;

	@JsonLdProperty(value = "controller")
	private final String controller;

	@JsonLdProperty(value = "publicKeyJwk")
	private final Map<String, Object> publicKeyJwk;

	public JsonWebKey2020(String id, String controller, Map<String, Object> publicKeyJwk) {
		this.id = id;
		this.controller = controller;
		this.publicKeyJwk = publicKeyJwk;
	}

	public String getId() {
		return id;
	}

	public String getController() {
		return controller;
	}

	public Map<String, Object> getPublicKeyJwk() {
		return publicKeyJwk;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		JsonWebKey2020 that = (JsonWebKey2020) o;
		return Objects.equals(id, that.id) && Objects.equals(controller, that.controller) &&
				Objects.equals(publicKeyJwk, that.publicKeyJwk);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, controller, publicKeyJwk);
	}

	@Override
	public String toString() {
		return "JsonWebKey2020{" +
				"id='" + id + '\'' +
				", controller='" + controller + '\'' +
				", publicKeyJwk=" + publicKeyJwk +
				'}';
	}

	public static JsonWebKey2020Builder builder() {
		return new JsonWebKey2020Builder();
	}

	public static class JsonWebKey2020Builder {

		private String id;

		private String controller;

		private Map<String, Object> publicKeyJwk;

		JsonWebKey2020Builder() {
		}

		public JsonWebKey2020Builder id(String id) {
			this.id = id;
			return this;
		}

		public JsonWebKey2020Builder controller(String controller) {
			this.controller = controller;
			return this;
		}

		public JsonWebKey2020Builder publicKeyJwk(Map<String, Object> publicKeyJwk) {
			this.publicKeyJwk = publicKeyJwk;
			return this;
		}

		public JsonWebKey2020 build() {
			return new JsonWebKey2020(id, controller, publicKeyJwk);
		}

		@Override
		public String toString() {
			return "JsonWebKey2020Builder{" +
					"id='" + id + '\'' +
					", controller='" + controller + '\'' +
					", publicKeyJwk=" + publicKeyJwk +
					'}';
		}
	}
}
