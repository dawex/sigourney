package com.dawex.sigourney.trustframework.vc.model.v2411.dataresource;

import com.dawex.sigourney.trustframework.vc.model.shared.DefaultFormatProvider;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.ExposedThrough;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.JacksonModuleFactory;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonBooleanValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonListValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonMapValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonStringValue;

class DataResourceVerifiableCredentialTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void init() {
		final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
		formatProvider.setFormat(Format.DATA_RESOURCE_VERIFIABLE_CREDENTIAL, "./dataResource/%s/vc");
		formatProvider.setFormat(Format.DATA_RESOURCE_CREDENTIAL_SUBJECT, "./dataResource/%s/cs");
		formatProvider.setFormat(Format.DATA_RESOURCE_COPYRIGHT_OWNED_BY, "./copyrightOwnedBy/%s");
		formatProvider.setFormat(Format.DATA_RESOURCE_PRODUCED_BY, "./producedBy/%s");

		objectMapper = JsonMapper.builder()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.addModule(JacksonModuleFactory.dataResourceSerializationModule(formatProvider, () -> "https://dawex.com"))
				.build();
	}

	@Test
	void shouldGenerateValidVerifiableCredentialForDataResource() {
		// given
		final var verifiableCredential = getDataResourceVerifiableCredential();
		// when
		final String serializedVc = objectMapper.writeValueAsString(verifiableCredential);
		// then
		assertThatSerializedVcIsValid(serializedVc);
	}

	private com.dawex.sigourney.trustframework.vc.model.v2411.dataresource.DataResourceVerifiableCredential getDataResourceVerifiableCredential() {
		return DataResourceVerifiableCredential.builder()
				.id("62b573deb33e417edcb34-id")
				.issuer("62b573deb33e417ed-issuer")
				.validFrom(LocalDate.of(2025, Month.FEBRUARY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.validUntil(LocalDate.of(2025, Month.MAY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.credentialSubject(DataResourceCredentialSubject.builder()
						.id("62b573deb33e417edcb34-dataResource")
						.name("62b573deb33e417edcb34-name")
						.description("62b573deb33e417edcb34-description")
						.resourcePolicy(List.of("62b573deb33e417edcb34-policy"))
						.licenseAsURI(List.of("62b573deb33e417edcb34-license"))
						.copyrightOwnedBy(List.of(new CopyrightOwnedBy("62b573deb33e417edcb34-copyrightOwnedBy")))
						.producedBy(new ProducedBy("62b573deb33e417edcb34-producedBy"))
						.containsPII(true)
						.exposedThrough(new ExposedThrough())
						.build())
				.build();
	}

	private static void assertThatSerializedVcIsValid(String serializedVc) {
		assertThatJsonListValue("$['@context']", serializedVc).hasSize(3);
		assertThatJsonStringValue("$['@context'][0]", serializedVc).isEqualTo("https://www.w3.org/ns/credentials/v2");
		assertThatJsonStringValue("$['@context'][1]", serializedVc).isEqualTo("https://w3id.org/gaia-x/development#");
		assertThatJsonStringValue("$['@context'][2]['@base']", serializedVc).isEqualTo("https://dawex.com");

		assertThatJsonListValue("$['type']", serializedVc).hasSize(2)
				.contains("VerifiableCredential", "gx:DataResource");
		assertThatJsonStringValue("$['id']", serializedVc)
				.isEqualTo("./dataResource/62b573deb33e417edcb34-id/vc");
		assertThatJsonStringValue("$['issuer']", serializedVc).isEqualTo("62b573deb33e417ed-issuer");
		assertThatJsonStringValue("$['validFrom']", serializedVc).isEqualTo("2025-02-21T15:38:02Z");
		assertThatJsonStringValue("$['validUntil']", serializedVc).isEqualTo("2025-05-21T15:38:02Z");

		assertThatJsonStringValue("$['credentialSubject']['id']", serializedVc)
				.isEqualTo("./dataResource/62b573deb33e417edcb34-dataResource/cs");
		assertThatJsonStringValue("$['credentialSubject']['schema:name']", serializedVc)
				.isEqualTo("62b573deb33e417edcb34-name");
		assertThatJsonStringValue("$['credentialSubject']['schema:description']", serializedVc)
				.isEqualTo("62b573deb33e417edcb34-description");
		assertThatJsonListValue("$['credentialSubject']['gx:resourcePolicy']", serializedVc)
				.hasSize(1)
				.first().isEqualTo("62b573deb33e417edcb34-policy");
		assertThatJsonListValue("$['credentialSubject']['gx:license']", serializedVc).hasSize(1);
		assertThatJsonMapValue("$['credentialSubject']['gx:license'][0]", serializedVc)
				.containsExactlyInAnyOrderEntriesOf(Map.of(
						"@type", "xsd:anyURI",
						"@value", "62b573deb33e417edcb34-license"));
		assertThatJsonBooleanValue("$['credentialSubject']['gx:containsPII']", serializedVc).isTrue();
		assertThatJsonListValue("$['credentialSubject']['gx:copyrightOwnedBy']", serializedVc)
				.hasSize(1)
				.first().asInstanceOf(InstanceOfAssertFactories.MAP)
				.containsExactlyInAnyOrderEntriesOf(Map.of(
						"type", "gx:LegalPerson",
						"id", "./copyrightOwnedBy/62b573deb33e417edcb34-copyrightOwnedBy"));
		assertThatJsonStringValue("$['credentialSubject']['gx:producedBy']['id']", serializedVc)
				.isEqualTo("./producedBy/62b573deb33e417edcb34-producedBy");
		assertThatJsonStringValue("$['credentialSubject']['gx:exposedThrough']['type']", serializedVc)
				.isEqualTo("gx:DataExchangeComponent");
	}
}