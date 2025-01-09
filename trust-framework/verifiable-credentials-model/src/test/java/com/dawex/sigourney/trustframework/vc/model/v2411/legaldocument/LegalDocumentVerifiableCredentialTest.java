package com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument;

import com.dawex.sigourney.trustframework.vc.model.shared.DefaultFormatProvider;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.JacksonModuleFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonListValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonMapValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonStringValue;

class LegalDocumentVerifiableCredentialTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void init() {
		final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
		formatProvider.setFormat(Format.LEGAL_DOCUMENT_VERIFIABLE_CREDENTIAL, "./legalDocument/%s/vc");
		formatProvider.setFormat(Format.LEGAL_DOCUMENT_CREDENTIAL_SUBJECT, "./legalDocument/%s/cs");
		formatProvider.setFormat(Format.SERVICE_OFFERING_INVOLVED_PARTIES, "./involvedParty/%s");

		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.registerModule(
				JacksonModuleFactory.legalDocumentSerializationModule(formatProvider, () -> "https://dawex.com"));
	}

	@ParameterizedTest
	@MethodSource("getLegalDocumentVerifiableCredentials")
	void shouldGenerateValidVerifiableCredentialForLegalDocument(
			LegalDocumentVerifiableCredential verifiableCredential, String expectedType)
			throws JsonProcessingException {
		// when
		final String serializedVc = objectMapper.writeValueAsString(verifiableCredential);
		// then
		assertThatSerializedVcIsValid(serializedVc, expectedType);
	}

	private static Stream<Arguments> getLegalDocumentVerifiableCredentials() {
		return Stream.of(
				Arguments.of(CustomerDataAccessTermsVerifiableCredential.builder()
						.id("62b573deb33e417edcb34-id")
						.issuer("62b573deb33e417ed-issuer")
						.validFrom(LocalDate.of(2025, Month.FEBRUARY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
						.validUntil(LocalDate.of(2025, Month.MAY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
						.credentialSubject(LegalDocumentCredentialSubject.builder()
								.id("62b573deb33e417edcb34-legal-document")
								.url("./legal-documents/legal-document.pdf")
								.mimeTypes(List.of("application/pdf"))
								.involvedParties(List.of(new InvolvedParty("62b570acb33e417-involvedParty")))
								.build())
						.build(), "gx:CustomerDataAccessTerms"),
				Arguments.of(CustomerDataProcessingTermsVerifiableCredential.builder()
						.id("62b573deb33e417edcb34-id")
						.issuer("62b573deb33e417ed-issuer")
						.validFrom(LocalDate.of(2025, Month.FEBRUARY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
						.validUntil(LocalDate.of(2025, Month.MAY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
						.credentialSubject(LegalDocumentCredentialSubject.builder()
								.id("62b573deb33e417edcb34-legal-document")
								.url("./legal-documents/legal-document.pdf")
								.mimeTypes(List.of("application/pdf"))
								.involvedParties(List.of(new InvolvedParty("62b570acb33e417-involvedParty")))
								.build())
						.build(), "gx:CustomerDataProcessingTerms"),
				Arguments.of(DocumentChangeProceduresVerifiableCredential.builder()
						.id("62b573deb33e417edcb34-id")
						.issuer("62b573deb33e417ed-issuer")
						.validFrom(LocalDate.of(2025, Month.FEBRUARY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
						.validUntil(LocalDate.of(2025, Month.MAY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
						.credentialSubject(LegalDocumentCredentialSubject.builder()
								.id("62b573deb33e417edcb34-legal-document")
								.url("./legal-documents/legal-document.pdf")
								.mimeTypes(List.of("application/pdf"))
								.involvedParties(List.of(new InvolvedParty("62b570acb33e417-involvedParty")))
								.build())
						.build(), "gx:DocumentChangeProcedures"),
				Arguments.of(LegallyBindingActVerifiableCredential.builder()
						.id("62b573deb33e417edcb34-id")
						.issuer("62b573deb33e417ed-issuer")
						.validFrom(LocalDate.of(2025, Month.FEBRUARY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
						.validUntil(LocalDate.of(2025, Month.MAY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
						.credentialSubject(LegalDocumentCredentialSubject.builder()
								.id("62b573deb33e417edcb34-legal-document")
								.url("./legal-documents/legal-document.pdf")
								.mimeTypes(List.of("application/pdf"))
								.involvedParties(List.of(new InvolvedParty("62b570acb33e417-involvedParty")))
								.build())
						.build(), "gx:LegallyBindingAct"),
				Arguments.of(LegalDocumentVerifiableCredential.builder()
						.id("62b573deb33e417edcb34-id")
						.issuer("62b573deb33e417ed-issuer")
						.validFrom(LocalDate.of(2025, Month.FEBRUARY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
						.validUntil(LocalDate.of(2025, Month.MAY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
						.credentialSubject(LegalDocumentCredentialSubject.builder()
								.id("62b573deb33e417edcb34-legal-document")
								.url("./legal-documents/legal-document.pdf")
								.mimeTypes(List.of("application/pdf"))
								.involvedParties(List.of(new InvolvedParty("62b570acb33e417-involvedParty")))
								.build())
						.build(), "gx:LegalDocument")
		);
	}

	private static void assertThatSerializedVcIsValid(String serializedVc, String type) {
		assertThatJsonListValue("$['@context']", serializedVc).hasSize(3);
		assertThatJsonStringValue("$['@context'][0]", serializedVc).isEqualTo("https://www.w3.org/ns/credentials/v2");
		assertThatJsonStringValue("$['@context'][1]", serializedVc).isEqualTo("https://w3id.org/gaia-x/development#");
		assertThatJsonStringValue("$['@context'][2]['@base']", serializedVc).isEqualTo("https://dawex.com");

		assertThatJsonListValue("$['type']", serializedVc).hasSize(2)
				.contains("VerifiableCredential", type);
		assertThatJsonStringValue("$['id']", serializedVc)
				.isEqualTo("./legalDocument/62b573deb33e417edcb34-id/vc");
		assertThatJsonStringValue("$['issuer']", serializedVc).isEqualTo("62b573deb33e417ed-issuer");
		assertThatJsonStringValue("$['validFrom']", serializedVc).isEqualTo("2025-02-21T15:38:02Z");
		assertThatJsonStringValue("$['validUntil']", serializedVc).isEqualTo("2025-05-21T15:38:02Z");

		assertThatJsonStringValue("$['credentialSubject']['id']", serializedVc)
				.isEqualTo("./legalDocument/62b573deb33e417edcb34-legal-document/cs");

		assertThatJsonMapValue("$['credentialSubject']", serializedVc).hasSize(4)
				.containsExactlyInAnyOrderEntriesOf(Map.of(
						"id", "./legalDocument/62b573deb33e417edcb34-legal-document/cs",
						"gx:url", Map.of("@type", "xsd:anyURI", "@value", "./legal-documents/legal-document.pdf"),
						"gx:mimeTypes", List.of("application/pdf"),
						"gx:involvedParties", List.of(Map.of(
								"type", "gx:LegalPerson",
								"id", "./involvedParty/62b570acb33e417-involvedParty"))
				));
	}
}