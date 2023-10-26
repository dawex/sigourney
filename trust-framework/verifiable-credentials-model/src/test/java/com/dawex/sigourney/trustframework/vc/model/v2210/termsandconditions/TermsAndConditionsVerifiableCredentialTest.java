package com.dawex.sigourney.trustframework.vc.model.v2210.termsandconditions;

import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.FormatProvider;
import com.dawex.sigourney.trustframework.vc.model.shared.DefaultFormatProvider;
import com.dawex.sigourney.trustframework.vc.model.utils.TestUtils;
import com.dawex.sigourney.trustframework.vc.model.v2210.AbstractVerifiableCredentialTest;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.JacksonModuleFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

class TermsAndConditionsVerifiableCredentialTest extends AbstractVerifiableCredentialTest {

	@Test
	void shouldGenerateValidVerifiableCredentialForTermsAndconditions() throws JsonProcessingException {
		// given
		final var verifiableCredential = getTermsAndConditionsVerifiableCredential();

		// when
		final String serializedVc = serializeVc(verifiableCredential);

		// then
		assertThatProofIsValid(serializedVc);

		TestUtils.assertThatJsonListValue("$['@context']", serializedVc).hasSize(4);
		TestUtils.assertThatJsonStringValue("$['@context'][0]['@base']", serializedVc).isEqualTo("https://dawex.com");
		TestUtils.assertThatJsonStringValue("$['@context'][1]", serializedVc).isEqualTo("https://www.w3.org/2018/credentials/v1");
		TestUtils.assertThatJsonStringValue("$['@context'][2]", serializedVc).isEqualTo("https://w3id.org/security/suites/jws-2020/v1");
		TestUtils.assertThatJsonStringValue("$['@context'][3]", serializedVc).isEqualTo(
				"https://registry.lab.gaia-x.eu/development/api/trusted-shape-registry/v1/shapes/jsonld/trustframework#");

		TestUtils.assertThatJsonStringValue("$['type']", serializedVc).isEqualTo("VerifiableCredential");
		TestUtils.assertThatJsonStringValue("$['id']", serializedVc)
				.isEqualTo("./termsAndConditions/62b573deb33e417edcb34-id");
		TestUtils.assertThatJsonStringValue("$['issuer']", serializedVc).isEqualTo("./organisations/62b573deb33e417ed-issuer");
		TestUtils.assertThatJsonStringValue("$['issuanceDate']", serializedVc).isEqualTo("2022-07-28T15:16:01Z");

		TestUtils.assertThatJsonStringValue("$['credentialSubject']['id']", serializedVc)
				.isEqualTo("./termsAndConditions/62b573deb33e417edcb34-terms");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['type']", serializedVc)
				.isEqualTo("gx:GaiaXTermsAndConditions");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:termsAndConditions']", serializedVc)
				.isEqualTo("Terms and conditions");
	}

	private TermsAndConditionsVerifiableCredential getTermsAndConditionsVerifiableCredential() {
		return TermsAndConditionsVerifiableCredential.builder()
				.id("62b573deb33e417edcb34-id")
				.issuer("62b573deb33e417ed-issuer")
				.issuanceDate(LocalDate.of(2022, Month.JULY, 28).atTime(15, 16, 1).atZone(ZoneOffset.UTC))
				.termsAndConditionsCredentialSubject(TermsAndConditionsCredentialSubject.builder()
						.id("62b573deb33e417edcb34-terms")
						.termsAndConditions("Terms and conditions")
						.build())
				.build();
	}

	@Override
	protected ObjectMapper getObjectMapper() {
		final var objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.registerModule(
				JacksonModuleFactory.termsAndConditionsSerializationModule(getFormatProvider(), () -> "https://dawex.com"));
		return objectMapper;
	}

	private static FormatProvider getFormatProvider() {
		final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
		formatProvider.setFormat(Format.TERMS_AND_CONDITIONS_ISSUER, "./organisations/%s");
		formatProvider.setFormat(Format.TERMS_AND_CONDITIONS_CREDENTIAL_SUBJECT, "./termsAndConditions/%s");
		formatProvider.setFormat(Format.TERMS_AND_CONDITIONS_VERIFIABLE_CREDENTIAL, "./termsAndConditions/%s");
		return formatProvider;
	}
}