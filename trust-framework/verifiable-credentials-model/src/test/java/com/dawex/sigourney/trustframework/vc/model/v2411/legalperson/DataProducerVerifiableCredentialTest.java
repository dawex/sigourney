package com.dawex.sigourney.trustframework.vc.model.v2411.legalperson;

import com.dawex.sigourney.trustframework.vc.model.shared.DefaultFormatProvider;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.Address;
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

class DataProducerVerifiableCredentialTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void init() {
		final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
		formatProvider.setFormat(Format.DATA_PRODUCER_VERIFIABLE_CREDENTIAL, "./dataProducers/%s/vc");
		formatProvider.setFormat(Format.DATA_PRODUCER_CREDENTIAL_SUBJECT, "./dataProducers/%s/cs");

		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		objectMapper.registerModules(JacksonModuleFactory.legalPersonSerializationModule(formatProvider, () -> "https://dawex.com"));
	}

	@Test
	void shouldJsonSerializeVerifiableCredentialForDataProducer() throws JsonProcessingException {
		// given
		final var verifiableCredential = getDataProducerVerifiableCredential();
		// when
		final String serializedVc = objectMapper.writeValueAsString(verifiableCredential);
		// then
		assertThatClaimsAreValid(serializedVc);
	}

	private static DataProducerVerifiableCredential getDataProducerVerifiableCredential() {
		return DataProducerVerifiableCredential.builder()
				.id("62b573deb33e417edcb34-id")
				.issuer("62b573deb33e417ed-issuer")
				.validFrom(LocalDate.of(2025, Month.FEBRUARY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.validUntil(LocalDate.of(2025, Month.MAY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.credentialSubject(DataProducerCredentialSubject.builder()
						.id("62b573deb33e417e-company")
						.name("Mercat de la Boqueria")
						.registrationNumber(RegistrationNumber.builder()
								.taxId("AB-1234-YZ")
								.build())
						.headquartersAddress(Address.builder()
								.streetAddress("La Rambla, 91")
								.postalCode("08001")
								.region("Cataluña")
								.locality("Barcelona")
								.countryCode("ESP")
								.countryName("Espagne")
								.build())
						.legalAddress(Address.builder()
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

	private static void assertThatClaimsAreValid(String serializedVc) {
		assertThatJsonListValue("$['@context']", serializedVc).hasSize(3);
		assertThatJsonStringValue("$['@context'][0]", serializedVc).isEqualTo("https://www.w3.org/ns/credentials/v2");
		assertThatJsonStringValue("$['@context'][1]", serializedVc).isEqualTo("https://w3id.org/gaia-x/development#");
		assertThatJsonStringValue("$['@context'][2]['@base']", serializedVc).isEqualTo("https://dawex.com");

		assertThatJsonListValue("$['type']", serializedVc).hasSize(3)
				.contains("VerifiableCredential", "gx:LegalPerson", "gx:DataProducer");
		assertThatJsonStringValue("$['id']", serializedVc)
				.isEqualTo("./dataProducers/62b573deb33e417edcb34-id/vc");
		assertThatJsonStringValue("$['issuer']", serializedVc).isEqualTo("62b573deb33e417ed-issuer");
		assertThatJsonStringValue("$['validFrom']", serializedVc).isEqualTo("2025-02-21T15:38:02Z");
		assertThatJsonStringValue("$['validUntil']", serializedVc).isEqualTo("2025-05-21T15:38:02Z");

		assertThatJsonStringValue("$['credentialSubject']['id']", serializedVc)
				.isEqualTo("./dataProducers/62b573deb33e417e-company/cs");
		assertThatJsonStringValue("$['credentialSubject']['schema:name']", serializedVc)
				.isEqualTo("Mercat de la Boqueria");
		assertThatJsonStringValue("$['credentialSubject']['gx:registrationNumber']['type']", serializedVc)
				.isEqualTo("gx:RegistrationNumber");
		assertThatJsonStringValue("$['credentialSubject']['gx:registrationNumber']['gx:taxID']", serializedVc)
				.isEqualTo("AB-1234-YZ");
		assertThatJsonStringValue("$['credentialSubject']['gx:headquartersAddress']['type']", serializedVc)
				.isEqualTo("gx:Address");
		assertThatJsonStringValue("$['credentialSubject']['gx:headquartersAddress']['vcard:street-address']", serializedVc)
				.isEqualTo("La Rambla, 91");
		assertThatJsonStringValue("$['credentialSubject']['gx:headquartersAddress']['vcard:postal-code']", serializedVc)
				.isEqualTo("08001");
		assertThatJsonStringValue("$['credentialSubject']['gx:headquartersAddress']['gx:region']", serializedVc)
				.isEqualTo("Cataluña");
		assertThatJsonStringValue("$['credentialSubject']['gx:headquartersAddress']['vcard:locality']", serializedVc)
				.isEqualTo("Barcelona");
		assertThatJsonStringValue("$['credentialSubject']['gx:headquartersAddress']['gx:countryCode']", serializedVc)
				.isEqualTo("ESP");
		assertThatJsonStringValue("$['credentialSubject']['gx:headquartersAddress']['gx:countryName']", serializedVc)
				.isEqualTo("Espagne");
		assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['type']", serializedVc)
				.isEqualTo("gx:Address");
		assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['vcard:street-address']", serializedVc)
				.isEqualTo("7 rue Grenette");
		assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['vcard:postal-code']", serializedVc)
				.isEqualTo("74000");
		assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['gx:region']", serializedVc)
				.isEqualTo("Savoie");
		assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['vcard:locality']", serializedVc)
				.isEqualTo("Annecy");
		assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['gx:countryCode']", serializedVc)
				.isEqualTo("FRA");
		assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['gx:countryName']", serializedVc)
				.isEqualTo("France");
	}
}
