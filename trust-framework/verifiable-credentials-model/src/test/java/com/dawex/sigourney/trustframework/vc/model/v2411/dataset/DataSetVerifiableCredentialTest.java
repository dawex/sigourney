package com.dawex.sigourney.trustframework.vc.model.v2411.dataset;

import com.dawex.sigourney.trustframework.vc.model.shared.DefaultFormatProvider;
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

import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonListValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonMapValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonStringValue;

class DataSetVerifiableCredentialTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void init() {
		final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
		formatProvider.setFormat(Format.DATA_SET_VERIFIABLE_CREDENTIAL, "./dataSet/%s/vc");
		formatProvider.setFormat(Format.DATA_SET_CREDENTIAL_SUBJECT, "./dataSet/%s/cs");
		formatProvider.setFormat(Format.DATA_SET_EXPOSED_THROUGH, "./exposedThrough/%s");

		objectMapper = JsonMapper.builder()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.addModule(JacksonModuleFactory.dataSetSerializationModule(formatProvider, () -> "https://dawex.com"))
				.build();
	}

	@Test
	void shouldGenerateValidVerifiableCredentialForDataSet() {
		// given
		final var verifiableCredential = getDataSetVerifiableCredential();
		// when
		final String serializedVc = objectMapper.writeValueAsString(verifiableCredential);
		// then
		assertThatSerializedVcIsValid(serializedVc);
	}

	private DataSetVerifiableCredential getDataSetVerifiableCredential() {
		return DataSetVerifiableCredential.builder()
				.id("62b573deb33e417edcb34-id")
				.issuer("62b573deb33e417ed-issuer")
				.validFrom(LocalDate.of(2025, Month.FEBRUARY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.validUntil(LocalDate.of(2025, Month.MAY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.credentialSubject(DataSetCredentialSubject.builder()
						.id("62b573deb33e417edcb34-dataSet")
						.name("62b573deb33e417edcb34-name")
						.description("62b573deb33e417edcb34-description")
						.identifier("62b573deb33e417edcb34-identifier")
						.title("62b573deb33e417edcb34-title")
						.distributions(List.of(
								Distribution.builder()
										.title("62b573deb33e417edcb34-distribution-title")
										.format("62b573deb33e417edcb34-distribution-format")
										.byteSize("1024")
										.hash("azerty")
										.hashAlgorithm("sha256")
										.locations(List.of("62b573deb33e417edcb34-distribution-location"))
										.issued(LocalDate.of(2025, 3, 14))
										.expirationDateTime(LocalDate.of(2025, 3, 14).atStartOfDay(ZoneOffset.UTC))
										.build()
						))
						.issued(LocalDate.of(2025, 3, 14))
						.expirationDateTime(LocalDate.of(2025, 3, 14).atStartOfDay(ZoneOffset.UTC))
						.exposedThrough("62b573deb33e417edcb34-exposedThrough")
						.build())
				.build();
	}

	private static void assertThatSerializedVcIsValid(String serializedVc) {
		assertThatJsonListValue("$['@context']", serializedVc).hasSize(3);
		assertThatJsonStringValue("$['@context'][0]", serializedVc).isEqualTo("https://www.w3.org/ns/credentials/v2");
		assertThatJsonStringValue("$['@context'][1]", serializedVc).isEqualTo("https://w3id.org/gaia-x/development#");
		assertThatJsonStringValue("$['@context'][2]['@base']", serializedVc).isEqualTo("https://dawex.com");

		assertThatJsonListValue("$['type']", serializedVc).hasSize(2)
				.contains("VerifiableCredential", "gx:DataSet");
		assertThatJsonStringValue("$['id']", serializedVc)
				.isEqualTo("./dataSet/62b573deb33e417edcb34-id/vc");
		assertThatJsonStringValue("$['issuer']", serializedVc).isEqualTo("62b573deb33e417ed-issuer");
		assertThatJsonStringValue("$['validFrom']", serializedVc).isEqualTo("2025-02-21T15:38:02Z");
		assertThatJsonStringValue("$['validUntil']", serializedVc).isEqualTo("2025-05-21T15:38:02Z");

		assertThatJsonStringValue("$['credentialSubject']['id']", serializedVc)
				.isEqualTo("./dataSet/62b573deb33e417edcb34-dataSet/cs");
		assertThatJsonStringValue("$['credentialSubject']['dcterms:identifier']", serializedVc)
				.isEqualTo("62b573deb33e417edcb34-identifier");
		assertThatJsonStringValue("$['credentialSubject']['schema:name']", serializedVc)
				.isEqualTo("62b573deb33e417edcb34-name");
		assertThatJsonStringValue("$['credentialSubject']['schema:description']", serializedVc)
				.isEqualTo("62b573deb33e417edcb34-description");
		assertThatJsonStringValue("$['credentialSubject']['dcterms:title']", serializedVc)
				.isEqualTo("62b573deb33e417edcb34-title");
		assertThatJsonMapValue("$['credentialSubject']['dcterms:issued']", serializedVc).hasSize(2)
				.containsExactlyInAnyOrderEntriesOf(Map.of(
						"@type", "xsd:date",
						"@value", "2025-03-14Z"));
		assertThatJsonMapValue("$['credentialSubject']['gx:expirationDateTime']", serializedVc).hasSize(2)
				.containsExactlyInAnyOrderEntriesOf(Map.of(
						"@type", "xsd:dateTime",
						"@value", "2025-03-14T00:00:00Z"));
		assertThatJsonStringValue("$['credentialSubject']['gx:exposedThrough']", serializedVc)
				.isEqualTo("./exposedThrough/62b573deb33e417edcb34-exposedThrough");

		assertThatJsonListValue("$['credentialSubject']['dcterms:distribution']", serializedVc).hasSize(1)
				.first().asInstanceOf(InstanceOfAssertFactories.MAP)
				.containsExactlyInAnyOrderEntriesOf(Map.of(
						"type", "dcat:Distribution",
						"dcterms:title", "62b573deb33e417edcb34-distribution-title",
						"dcterms:format", "62b573deb33e417edcb34-distribution-format",
						"dcat:byteSize", "1024",
						"gx:hash", "azerty",
						"gx:hashAlgorithm", "sha256",
						"gx:location", List.of("62b573deb33e417edcb34-distribution-location"),
						"dcterms:issued", Map.of("@type", "xsd:date", "@value", "2025-03-14Z"),
						"gx:expirationDateTime", Map.of("@type", "xsd:dateTime", "@value", "2025-03-14T00:00:00Z")
				));
	}
}