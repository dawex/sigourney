package com.dawex.sigourney.trustframework.vc.core.vc.v2;

import com.dawex.sigourney.trustframework.vc.core.Constant;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.JsonLdContextsSerializer;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.JsonLdSerializer;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.JsonLdTypeSerializer;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.EnvelopedVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.VerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.VerifiablePresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nimbusds.jose.HeaderParameterNames;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.dawex.sigourney.trustframework.vc.core.vc.v2.VerifiablePresentationSignatureHelper.CONTENT_TYPE_VC_LD_JSON;
import static com.dawex.sigourney.trustframework.vc.core.vc.v2.VerifiablePresentationSignatureHelper.CONTENT_TYPE_VP_LD_JSON;
import static com.dawex.sigourney.trustframework.vc.core.vc.v2.VerifiablePresentationSignatureHelper.MEDIA_TYPE_VC_JWT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

public class VerifiablePresentationSignatureHelperTest {

	private static final String VC_ID_PREFIX = "data:%s,".formatted(MEDIA_TYPE_VC_JWT);

	private final VerifiablePresentationSignatureHelper verifiablePresentationSignatureHelper;

	public VerifiablePresentationSignatureHelperTest() {
		final ObjectMapper objectMapper = getObjectMapper();
		verifiablePresentationSignatureHelper = new VerifiablePresentationSignatureHelper(objectMapper);
	}

	@Nested
	class BuildAndSignVerifiablePresentation {
		@Test
		void shouldBuildVerifiablePresentation() throws ParseException, JOSEException {
			final var securedVerifiableCredentials = getVerifiableCredentials("002").stream()
					.map(vc -> verifiablePresentationSignatureHelper.signVerifiableCredential(vc, Constant.DID_ISSUER, Constant.JWK))
					.toList();
			final String actual = verifiablePresentationSignatureHelper.buildAndSignVerifiablePresentation(
					getVerifiableCredentials("001"), securedVerifiableCredentials, Constant.DID_ISSUER, Constant.JWK);

			assertThat(actual).isNotNull();

			final JWSObject vpJwsObject = JWSObject.parse(actual);
			assertThatSignatureIsValid(vpJwsObject, CONTENT_TYPE_VP_LD_JSON);

			assertThat(vpJwsObject.getPayload().toJSONObject())

					.extractingByKey("verifiableCredential").asInstanceOf(LIST).hasSize(2)
					// all vcs are valid EnvelopedVerifiableCredential
					.allSatisfy(obj -> {
						final var securedVc = (Map<String, Object>) obj;
						assertThat(securedVc).extractingByKey("type").isEqualTo("EnvelopedVerifiableCredential");
						assertThat(securedVc).extractingByKey("id").asString().startsWith(VC_ID_PREFIX);
					})

					.extracting(obj -> {
						final var securedVc = (Map<String, Object>) obj;
						return JWSObject.parse(((String) securedVc.get("id")).substring(VC_ID_PREFIX.length()));
					})
					// all vcs signature are valid
					.allSatisfy(vcJwsObject -> assertThatSignatureIsValid(vcJwsObject, CONTENT_TYPE_VC_LD_JSON))

					.extracting(jwsObject -> jwsObject.getPayload().toJSONObject())
					// all vcs claims are valid
					.satisfies(verifiableCredentials -> {
						assertThat(verifiableCredentials)
								.filteredOn(vc -> Objects.equals(vc.get("id"), "./test/001/verifiableCredential"))
								.first()
								.satisfies(vc -> assertThatClaimsAreValid("001", vc));
						assertThat(verifiableCredentials)
								.filteredOn(vc -> Objects.equals(vc.get("id"), "./test/002/verifiableCredential"))
								.first()
								.satisfies(vc -> assertThatClaimsAreValid("002", vc));
					});
		}

		private void assertThatClaimsAreValid(String id, Map<String, Object> claims) {
			assertThat(claims).extractingByKey("@context").isNotNull()
					.asInstanceOf(LIST).hasSize(3)
					.containsExactlyInAnyOrder(
							"https://www.w3.org/ns/credentials/v2",
							"https://w3id.org/gaia-x/development#",
							Map.of("@base", "https://dawex.com")
					);

			assertThat(claims).extractingByKeys("type", "id", "issuer", "validFrom")
					.containsExactly("VerifiableCredential",
							"./test/" + id + "/verifiableCredential",
							"./test/" + id,
							"2025-02-24T00:00:00Z[UTC]");
		}

		private void assertThatSignatureIsValid(JWSObject jwsObject, String expectedContentType) throws JOSEException {
			assertThat(jwsObject.verify(new RSASSAVerifier((RSAKey) Constant.JWK)))
					.as("Check JWS signature validity")
					.isTrue();

			final JWSHeader header = jwsObject.getHeader();
			assertThat(header.getType()).isNotNull().extracting(JOSEObjectType::getType).isEqualTo(expectedContentType + "+jwt");
			assertThat(header.getContentType()).isEqualTo(expectedContentType);
			assertThat(header.getKeyID()).isNotNull();
			assertThat(header.getCustomParam(HeaderParameterNames.ISSUER)).isNotNull();
		}
	}

	private static Collection<VerifiableCredential> getVerifiableCredentials(String id) {
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
		module.addSerializer(VerifiablePresentation.class,
				new JsonLdSerializer<>(VerifiablePresentation.class, formatName -> Optional.empty()));
		module.addSerializer(EnvelopedVerifiableCredential.class,
				new JsonLdSerializer<>(EnvelopedVerifiableCredential.class, formatName -> Optional.empty()));
		objectMapper.registerModule(module);

		return objectMapper;
	}
}
