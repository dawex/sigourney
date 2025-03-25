package com.dawex.sigourney.trustframework.vc.core.vc.v1;

import com.dawex.sigourney.trustframework.vc.core.Constant;
import com.dawex.sigourney.trustframework.vc.core.jose.crypto.JwkSetUtils;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.JsonLdContextsSerializer;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.JsonLdSerializer;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.JsonLdTypeSerializer;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.SignedObjectJsonLdSerializer;
import com.dawex.sigourney.trustframework.vc.core.utils.ProofSignatureExpectationsHelper;
import com.dawex.sigourney.trustframework.vc.core.vc.signature.model.Proof;
import com.dawex.sigourney.trustframework.vc.core.vc.signature.model.SignedObject;
import com.dawex.sigourney.trustframework.vc.core.vc.v1.model.VerifiablePresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nimbusds.jose.jwk.JWK;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.dawex.sigourney.trustframework.vc.core.utils.TestUtils.assertThatJsonListValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

public class VerifiablePresentationSignatureHelperV1Test {

	private static JwkSetUtils.CreatedKeys keys;

	private static JWK jwk;

	private final VerifiablePresentationSignatureHelperV1 verifiablePresentationSignatureHelper;

	public VerifiablePresentationSignatureHelperV1Test() {
		final ObjectMapper objectMapper = getObjectMapper();
		verifiablePresentationSignatureHelper = new VerifiablePresentationSignatureHelperV1(objectMapper);
	}

	@BeforeAll
	static void init() {
		keys = JwkSetUtils.createKeysWithSelfSignedCertificate(JwkSetUtils.KeyAlgorithm.RSA_2048, null, "Test", 12);
		jwk = keys.jwkSet().getKeys().stream().findFirst().orElseThrow();
	}

	@Nested
	class BuildAndSignVerifiablePresentation {
		@Test
		void shouldBuildVerifiablePresentation() {
			final String actual = verifiablePresentationSignatureHelper.buildAndSignVerifiablePresentation(
					getVerifiableCredentials("001"), getVerifiableCredentials("002"), Constant.DID_ISSUER, jwk);

			assertThat(actual).isNotNull();
			assertThatJsonListValue("$['verifiableCredential']", actual).hasSize(2)
					.satisfies(verifiableCredentials -> {
						assertThat(verifiableCredentials)
								.filteredOn(vc -> Objects.equals(((Map) vc).get("id"), "./test/001/verifiableCredential"))
								.first().asInstanceOf(MAP)
								.satisfies(vc -> {
									assertThatClaimsAreValid(vc, "001");
									assertThatSignatureIsValid(vc);
								});
						assertThat(verifiableCredentials)
								.filteredOn(vc -> Objects.equals(((Map) vc).get("id"), "./test/002/verifiableCredential"))
								.first().asInstanceOf(MAP)
								.satisfies(vc -> {
									assertThatClaimsAreValid(vc, "002");
									assertThat(vc).doesNotContainKey("proof");
								});
					});
		}

		private void assertThatClaimsAreValid(Map<Object, Object> claims, String id) {
			assertThat(claims).extractingByKey("@context").asInstanceOf(LIST).hasSize(4)
					.containsExactlyInAnyOrder(
							Map.of("@base", "https://dawex.com"),
							"https://www.w3.org/2018/credentials/v1",
							"https://w3id.org/security/suites/jws-2020/v1",
							"https://registry.lab.gaia-x.eu/development/api/trusted-shape-registry/v1/shapes/jsonld/trustframework#");

			assertThat(claims).extractingByKeys("type", "id", "issuer", "issuanceDate")
					.containsExactly("VerifiableCredential",
							"./test/" + id + "/verifiableCredential",
							"./test/" + id,
							"2025-02-24T00:00:00Z[UTC]");
		}

		private void assertThatSignatureIsValid(Map<Object, Object> verifiableCredential) {
			assertThat(verifiableCredential).containsKey("proof")
					.extractingByKey("proof").asInstanceOf(MAP)
					.satisfies(proof -> {
						assertThat(proof).containsAllEntriesOf(Map.of(
								"type", "JsonWebSignature2020",
								"proofPurpose", "assertionMethod",
								"verificationMethod", Constant.DID_ISSUER + "#" + jwk.getKeyID()
						));
						assertThat(proof).containsKey("created");
					});
			new ProofSignatureExpectationsHelper(keys.jwkSet(), keys.certificates())
					.assertSignatureIsValid(verifiableCredential);
		}
	}

	private static Collection<Object> getVerifiableCredentials(String id) {
		return List.of(new TestVerifiableCredential("./test/" + id + "/verifiableCredential", "./test/" + id,
				LocalDate.of(2025, 2, 24).atStartOfDay().atZone(ZoneId.of("UTC"))));
	}

	private static ObjectMapper getObjectMapper() {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		objectMapper.registerModule(new JavaTimeModule());

		final SimpleModule module = new SimpleModule();
		module.addSerializer(JsonLdContexts.class, new JsonLdContextsSerializer(() -> "https://dawex.com"));
		module.addSerializer(JsonLdType.class, new JsonLdTypeSerializer());
		module.addSerializer(TestVerifiableCredential.class,
				new JsonLdSerializer<>(TestVerifiableCredential.class, formatName -> Optional.empty()));
		module.addSerializer(Proof.class, new JsonLdSerializer<>(Proof.class, formatName -> Optional.empty()));
		module.addSerializer(SignedObject.class, new SignedObjectJsonLdSerializer(formatName -> Optional.empty()));
		module.addSerializer(VerifiablePresentation.class,
				new JsonLdSerializer<>(VerifiablePresentation.class, formatName -> Optional.empty()));
		objectMapper.registerModule(module);

		return objectMapper;
	}
}
