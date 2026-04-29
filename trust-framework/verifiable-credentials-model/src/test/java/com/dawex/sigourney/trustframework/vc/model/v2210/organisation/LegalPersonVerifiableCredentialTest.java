package com.dawex.sigourney.trustframework.vc.model.v2210.organisation;

import com.dawex.sigourney.trustframework.vc.model.shared.DefaultFormatProvider;
import com.dawex.sigourney.trustframework.vc.model.utils.TestUtils;
import com.dawex.sigourney.trustframework.vc.model.v2210.common.Address;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.JacksonModuleFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

class LegalPersonVerifiableCredentialTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void init() {
		final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
		formatProvider.setFormat(Format.ORGANISATION_VERIFIABLE_CREDENTIAL, "./organisations/%s/verifiableCredential");
		formatProvider.setFormat(Format.ORGANISATION_CREDENTIAL_SUBJECT, "./organisations/%s");
		formatProvider.setFormat(Format.ORGANISATION_ISSUER, "./organisations/%s");

		objectMapper = JsonMapper.builder()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.addModule(JacksonModuleFactory.organisationSerializationModule(formatProvider, () -> "https://dawex.com"))
				.build();
	}

	@Test
	void shouldGenerateValidVerifiableCredentialForOrganisation() {
		// given
		final var verifiableCredential = getOrganisationVerifiableCredential();
		// when
		final String serializedVc = objectMapper.writeValueAsString(verifiableCredential);
		// then
		assertThatClaimsAreValid(serializedVc);
	}

	private static void assertThatClaimsAreValid(String serializedVc) {
		TestUtils.assertThatJsonListValue("$['@context']", serializedVc).hasSize(4);
		TestUtils.assertThatJsonStringValue("$['@context'][0]", serializedVc).isEqualTo("https://www.w3.org/2018/credentials/v1");
		TestUtils.assertThatJsonStringValue("$['@context'][1]", serializedVc).isEqualTo("https://w3id.org/security/suites/jws-2020/v1");
		TestUtils.assertThatJsonStringValue("$['@context'][2]", serializedVc).isEqualTo(
				"https://registry.lab.gaia-x.eu/development/api/trusted-shape-registry/v1/shapes/jsonld/trustframework#");
		TestUtils.assertThatJsonStringValue("$['@context'][3]['@base']", serializedVc).isEqualTo("https://dawex.com");

		TestUtils.assertThatJsonStringValue("$['type']", serializedVc).isEqualTo("VerifiableCredential");
		TestUtils.assertThatJsonStringValue("$['id']", serializedVc)
				.isEqualTo("./organisations/62b573deb33e417edcb34-id/verifiableCredential");
		TestUtils.assertThatJsonStringValue("$['issuer']", serializedVc).isEqualTo("./organisations/62b573deb33e417ed-issuer");
		TestUtils.assertThatJsonStringValue("$['issuanceDate']", serializedVc).isEqualTo("2022-07-28T15:16:01Z");

		TestUtils.assertThatJsonStringValue("$['credentialSubject']['id']", serializedVc)
				.isEqualTo("./organisations/62b573deb33e417e-company");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['type']", serializedVc)
				.isEqualTo("gx:LegalParticipant");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:name']", serializedVc)
				.isEqualTo("Mercat de la Boqueria");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:legalRegistrationNumber']['type']", serializedVc)
				.isEqualTo("gx:legalRegistrationNumber");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:legalRegistrationNumber']['gx:taxID']", serializedVc)
				.isEqualTo("AB-1234-YZ");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:headquarterAddress']['gx:street-address']",
						serializedVc)
				.isEqualTo("La Rambla, 91");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:headquarterAddress']['gx:postal-code']",
						serializedVc)
				.isEqualTo("08001");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:headquarterAddress']['gx:region']", serializedVc)
				.isEqualTo("Cataluña");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:headquarterAddress']['gx:locality']",
						serializedVc)
				.isEqualTo("Barcelona");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:headquarterAddress']['gx:country-name']",
						serializedVc)
				.isEqualTo("ESP");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['gx:street-address']",
						serializedVc)
				.isEqualTo("7 rue Grenette");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['gx:postal-code']", serializedVc)
				.isEqualTo("74000");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['gx:region']", serializedVc)
				.isEqualTo("Savoie");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['gx:locality']", serializedVc)
				.isEqualTo("Annecy");
		TestUtils.assertThatJsonStringValue("$['credentialSubject']['gx:legalAddress']['gx:country-name']", serializedVc)
				.isEqualTo("FRA");
	}

	private static OrganisationVerifiableCredential getOrganisationVerifiableCredential() {
		return OrganisationVerifiableCredential.builder()
				.id("62b573deb33e417edcb34-id")
				.issuer("62b573deb33e417ed-issuer")
				.issuanceDate(LocalDate.of(2022, Month.JULY, 28).atTime(15, 16, 1).atZone(ZoneOffset.UTC))
				.organisationCredentialSubject(OrganisationCredentialSubject.builder()
						.id("62b573deb33e417e-company")
						.name("Mercat de la Boqueria")
						.registrationNumber(OrganisationLegalRegistrationNumber.builder()
								.taxId("AB-1234-YZ")
								.build())
						.headquarterAddress(Address.builder()
								.streetAddress("La Rambla, 91")
								.postalCode("08001")
								.region("Cataluña")
								.locality("Barcelona")
								.countryName("ESP")
								.countrySubdivisionCode("ES-A")
								.build())
						.legalAddress(Address.builder()
								.streetAddress("7 rue Grenette")
								.postalCode("74000")
								.region("Savoie")
								.locality("Annecy")
								.countryName("FRA")
								.countrySubdivisionCode("FR-A")
								.build())
						.build())
				.build();
	}
}
