package com.dawex.sigourney.trustframework.vc.model.v2411.physicalresource;

import com.dawex.sigourney.trustframework.vc.model.shared.DefaultFormatProvider;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.Address;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.JacksonModuleFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonListValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonStringValue;

class PhysicalResourceVerifiableCredentialTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void init() {
		final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
		formatProvider.setFormat(Format.PHYSICAL_RESOURCE_VERIFIABLE_CREDENTIAL, "./resource/%s/vc");
		formatProvider.setFormat(Format.PHYSICAL_RESOURCE_CREDENTIAL_SUBJECT, "./resource/%s/cs");
		formatProvider.setFormat(Format.PHYSICAL_RESOURCE_MAINTAINED_BY, "./maintainedBy/%s");

		objectMapper = JsonMapper.builder()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.addModule(JacksonModuleFactory.physicalResourceSerializationModule(formatProvider, () -> "https://dawex.com"))
				.build();
	}

	@Test
	void shouldGenerateValidVerifiableCredentialForPhysicalResource() {
		// given
		final var verifiableCredential = getPhysicalResourceVerifiableCredential();
		// when
		final String serializedVc = objectMapper.writeValueAsString(verifiableCredential);
		// then
		assertThatSerializedVcIsValid(serializedVc);
	}

	private PhysicalResourceVerifiableCredential getPhysicalResourceVerifiableCredential() {
		return PhysicalResourceVerifiableCredential.builder()
				.id("62b573deb33e417edcb34-id")
				.issuer("62b573deb33e417ed-issuer")
				.validFrom(LocalDate.of(2025, Month.FEBRUARY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.validUntil(LocalDate.of(2025, Month.MAY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.credentialSubject(PhysicalResourceCredentialSubject.builder()
						.id("62b573deb33e417edcb34-issuer")
						.maintainedBy(new MaintainedBy("62b573deb33e417edcb34-maintainer"))
						.location(Address.builder()
								.streetAddress("7 rue Grenette")
								.postalCode("74000")
								.region("Savoie")
								.locality("Annecy")
								.countryCode("FRA")
								.countryName("France")
								.build())
						.build())
				.build();
	}

	private static void assertThatSerializedVcIsValid(String serializedVc) {
		assertThatJsonListValue("$['@context']", serializedVc).hasSize(3);
		assertThatJsonStringValue("$['@context'][0]", serializedVc).isEqualTo("https://www.w3.org/ns/credentials/v2");
		assertThatJsonStringValue("$['@context'][1]", serializedVc).isEqualTo("https://w3id.org/gaia-x/development#");
		assertThatJsonStringValue("$['@context'][2]['@base']", serializedVc).isEqualTo("https://dawex.com");

		assertThatJsonListValue("$['type']", serializedVc).hasSize(2)
				.contains("VerifiableCredential", "gx:PhysicalResource");
		assertThatJsonStringValue("$['id']", serializedVc)
				.isEqualTo("./resource/62b573deb33e417edcb34-id/vc");
		assertThatJsonStringValue("$['issuer']", serializedVc).isEqualTo("62b573deb33e417ed-issuer");
		assertThatJsonStringValue("$['validFrom']", serializedVc).isEqualTo("2025-02-21T15:38:02Z");
		assertThatJsonStringValue("$['validUntil']", serializedVc).isEqualTo("2025-05-21T15:38:02Z");

		assertThatJsonStringValue("$['credentialSubject']['id']", serializedVc)
				.isEqualTo("./resource/62b573deb33e417edcb34-issuer/cs");
		assertThatJsonStringValue("$['credentialSubject']['gx:maintainedBy']['type']", serializedVc)
				.isEqualTo("gx:LegalPerson");
		assertThatJsonStringValue("$['credentialSubject']['gx:maintainedBy']['id']", serializedVc)
				.isEqualTo("./maintainedBy/62b573deb33e417edcb34-maintainer");
		assertThatJsonStringValue("$['credentialSubject']['gx:location']['type']", serializedVc)
				.isEqualTo("gx:Address");
		assertThatJsonStringValue("$['credentialSubject']['gx:location']['vcard:street-address']", serializedVc)
				.isEqualTo("7 rue Grenette");
		assertThatJsonStringValue("$['credentialSubject']['gx:location']['vcard:postal-code']", serializedVc)
				.isEqualTo("74000");
		assertThatJsonStringValue("$['credentialSubject']['gx:location']['gx:region']", serializedVc)
				.isEqualTo("Savoie");
		assertThatJsonStringValue("$['credentialSubject']['gx:location']['vcard:locality']", serializedVc)
				.isEqualTo("Annecy");
		assertThatJsonStringValue("$['credentialSubject']['gx:location']['gx:countryCode']", serializedVc)
				.isEqualTo("FRA");
		assertThatJsonStringValue("$['credentialSubject']['gx:location']['gx:countryName']", serializedVc)
				.isEqualTo("France");
	}
}