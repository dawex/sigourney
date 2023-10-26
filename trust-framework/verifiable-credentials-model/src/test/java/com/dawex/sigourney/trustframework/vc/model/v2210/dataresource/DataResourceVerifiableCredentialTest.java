package com.dawex.sigourney.trustframework.vc.model.v2210.dataresource;

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
import java.util.List;

class DataResourceVerifiableCredentialTest extends AbstractVerifiableCredentialTest {

	private DataResourceVerifiableCredential service;

	@Test
	void shouldGenerateValidVerifiableCredentialForDataResource() throws JsonProcessingException {
		// given
		final var verifiableCredential = getDataResourceVerifiableCredential();

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
				.isEqualTo("./dataResource/62b573deb33e417edcb34-id");
		TestUtils.assertThatJsonStringValue("$['issuer']", serializedVc).isEqualTo("./organisations/62b573deb33e417ed-issuer");
		TestUtils.assertThatJsonStringValue("$['issuanceDate']", serializedVc).isEqualTo("2022-07-28T15:16:01Z");

		TestUtils.assertThatJsonStringValue("$['credentialSubject']['id']", serializedVc)
				.isEqualTo("./dataResource/62b573deb33e417edcb34-dataResource");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['type']", serializedVc)
				.isEqualTo("gx:DataResource");

		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:name']", serializedVc)
				.isEqualTo("62b573deb33e417edcb34-name");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:description']", serializedVc)
				.isEqualTo("62b573deb33e417edcb34-description");
		TestUtils.assertThatJsonListValue("$['credentialSubject']['gx:policy']", serializedVc)
				.hasSize(1)
				.first().isEqualTo("62b573deb33e417edcb34-policy");
		TestUtils.assertThatJsonListValue("$['credentialSubject']['gx:license']", serializedVc)
				.hasSize(1)
				.first().isEqualTo("62b573deb33e417edcb34-license");
		TestUtils.assertThatJsonListValue("$['credentialSubject']['gx:copyrightOwnedBy']", serializedVc)
				.hasSize(1)
				.first().isEqualTo("./api/secure/participant/organisations/62b573deb33e417edcb34-copyrightOwnedBy/verifiableCredential");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:producedBy']['id']", serializedVc)
				.isEqualTo("./organisations/62b573deb33e417edcb34-producedBy");
	}

	private DataResourceVerifiableCredential getDataResourceVerifiableCredential() {
		return DataResourceVerifiableCredential.builder()
				.id("62b573deb33e417edcb34-id")
				.issuer("62b573deb33e417ed-issuer")
				.issuanceDate(LocalDate.of(2022, Month.JULY, 28).atTime(15, 16, 1).atZone(ZoneOffset.UTC))
				.dataResourceCredentialSubject(DataResourceCredentialSubject.builder()
						.id("62b573deb33e417edcb34-dataResource")
						.name("62b573deb33e417edcb34-name")
						.description("62b573deb33e417edcb34-description")
						.policy(List.of("62b573deb33e417edcb34-policy"))
						.licenses(List.of("62b573deb33e417edcb34-license"))
						.copyrightOwnedBy(List.of("62b573deb33e417edcb34-copyrightOwnedBy"))
						.producedBy(ProducedBy.builder().id("62b573deb33e417edcb34-producedBy").build())
						.containsPII(true)
						.exposedThrough(ExposedThrough.builder().id("62b573deb33e417edcb34-exposedThrough").build())
						.distribution(Distribution.builder()
								.title("tangerine.csv")
								.mediaType("text/csv")
								.byteSize(139855L)
								.fileHash("37f5d519788d497dcaaa345ba0cb9629fb13ffe23011eb7751796153985a86fb")
								.algorithm("SHA-256")
								.location(Location.builder()
										.dataCenterLocation("Europe (Ireland)")
										.build())
								.build())
						.build())
				.build();
	}

	@Override
	protected ObjectMapper getObjectMapper() {
		final var objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.registerModule(
				JacksonModuleFactory.dataResourcesSerializationModule(getFormatProvider(), () -> "https://dawex.com"));
		return objectMapper;
	}

	private static FormatProvider getFormatProvider() {
		final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
		formatProvider.setFormat(Format.DATA_RESOURCE_CREDENTIAL_SUBJECT, "./dataResource/%s");
		formatProvider.setFormat(Format.DATA_RESOURCE_COPYRIGHT_OWNED_BY, "./api/secure/participant/organisations/%s/verifiableCredential");
		formatProvider.setFormat(Format.DATA_RESOURCE_EXPOSED_THROUGH, "./dataOfferings/%s");
		formatProvider.setFormat(Format.DATA_RESOURCE_ISSUER, "./organisations/%s");
		formatProvider.setFormat(Format.DATA_RESOURCE_PRODUCED_BY, "./organisations/%s");
		formatProvider.setFormat(Format.DATA_RESOURCE_VERIFIABLE_CREDENTIAL, "./dataResource/%s");
		return formatProvider;
	}
}