package com.dawex.sigourney.trustframework.vc.model.v2411.issuer;

import com.dawex.sigourney.trustframework.vc.model.shared.DefaultFormatProvider;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.GaiaxTermsAndConditions;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.JacksonModuleFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonListValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonStringValue;

class IssuerVerifiableCredentialTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void init() {
		final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
		formatProvider.setFormat(Format.ISSUER_VERIFIABLE_CREDENTIAL, "./issuer/%s/vc");
		formatProvider.setFormat(Format.ISSUER_CREDENTIAL_SUBJECT, "./issuer/%s/cs");

		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.registerModule(
				JacksonModuleFactory.issuerSerializationModule(formatProvider, () -> "https://dawex.com"));
	}

	@Test
	void shouldGenerateValidVerifiableCredentialForIssuer() throws JsonProcessingException {
		// given
		final var verifiableCredential = getIssuerVerifiableCredential();
		// when
		final String serializedVc = objectMapper.writeValueAsString(verifiableCredential);
		// then
		assertThatSerializedVcIsValid(serializedVc);
	}

	private IssuerVerifiableCredential getIssuerVerifiableCredential() {
		return IssuerVerifiableCredential.builder()
				.id("62b573deb33e417edcb34-id")
				.issuer("62b573deb33e417ed-issuer")
				.validFrom(LocalDate.of(2025, Month.FEBRUARY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.validUntil(LocalDate.of(2025, Month.MAY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.credentialSubject(IssuerCredentialSubject.builder()
						.id("62b573deb33e417edcb34-issuer")
						.gaiaxTermsAndConditions(GaiaxTermsAndConditions.V2411)
						.build())
				.build();
	}

	private static void assertThatSerializedVcIsValid(String serializedVc) {
		assertThatJsonListValue("$['@context']", serializedVc).hasSize(3);
		assertThatJsonStringValue("$['@context'][0]", serializedVc).isEqualTo("https://www.w3.org/ns/credentials/v2");
		assertThatJsonStringValue("$['@context'][1]", serializedVc).isEqualTo("https://w3id.org/gaia-x/development#");
		assertThatJsonStringValue("$['@context'][2]['@base']", serializedVc).isEqualTo("https://dawex.com");

		assertThatJsonListValue("$['type']", serializedVc).hasSize(2)
				.contains("VerifiableCredential", "gx:Issuer");
		assertThatJsonStringValue("$['id']", serializedVc)
				.isEqualTo("./issuer/62b573deb33e417edcb34-id/vc");
		assertThatJsonStringValue("$['issuer']", serializedVc).isEqualTo("62b573deb33e417ed-issuer");
		assertThatJsonStringValue("$['validFrom']", serializedVc).isEqualTo("2025-02-21T15:38:02Z");
		assertThatJsonStringValue("$['validUntil']", serializedVc).isEqualTo("2025-05-21T15:38:02Z");

		assertThatJsonStringValue("$['credentialSubject']['id']", serializedVc)
				.isEqualTo("./issuer/62b573deb33e417edcb34-issuer/cs");
		assertThatJsonStringValue("$['credentialSubject']['gx:gaiaxTermsAndConditions']", serializedVc)
				.isEqualTo("4bd7554097444c960292b4726c2efa1373485e8a5565d94d41195214c5e0ceb3");
	}
}