package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

import com.dawex.sigourney.trustframework.vc.model.shared.DefaultFormatProvider;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.AccessUsagePolicy;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.Address;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.ContactInformation;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.PolicyLanguage;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.TermsAndConditions;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.Format;
import com.dawex.sigourney.trustframework.vc.model.v2411.serialization.JacksonModuleFactory;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.CustomerDataAccessTerms;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.CustomerDataProcessingTerms;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.DocumentChangeProcedures;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.LegalDocument;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.LegallyBindingAct;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.providedby.ProvidedByDataProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonListValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonMapValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonStringValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

class DataProductVerifiableCredentialTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void init() {
		final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
		formatProvider.setFormat(Format.DATA_PRODUCT_VERIFIABLE_CREDENTIAL, "./dataOfferings/%s/vc");
		formatProvider.setFormat(Format.DATA_PRODUCT_CREDENTIAL_SUBJECT, "./dataOfferings/%s/cs");
		formatProvider.setFormat(Format.DATA_PRODUCT_AGGREGATION_OF, "./aggregationOf/%s");
		formatProvider.setFormat(Format.DATA_PRODUCT_PROVIDED_BY, "./providedBy/%s");
		formatProvider.setFormat(Format.LEGAL_DOCUMENT_INVOLVED_PARTIES, "./involvedParty/%s");
		formatProvider.setFormat(Format.SERVICE_OFFERING_AGGREGATION_OF_RESOURCE, "./aggregationOfResources/%s");
		formatProvider.setFormat(Format.TERMS_AND_CONDITIONS_URL, "./termsAndConditions/%s");

		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.registerModule(JacksonModuleFactory.serviceOfferingSerializationModule(formatProvider, () -> "https://dawex.com"));
	}

	@Test
	void shouldGenerateValidVerifiableCredentialForDataProduct() throws JsonProcessingException {
		// given
		final var verifiableCredential = getDataProductVerifiableCredential();
		// when
		final String serializedVc = objectMapper.writeValueAsString(verifiableCredential);
		// then
		assertThatClaimsAreValid(serializedVc);
	}

	private static void assertThatClaimsAreValid(String serializedVc) {
		assertThatJsonListValue("$['@context']", serializedVc).hasSize(3);
		assertThatJsonStringValue("$['@context'][0]", serializedVc).isEqualTo("https://www.w3.org/ns/credentials/v2");
		assertThatJsonStringValue("$['@context'][1]", serializedVc).isEqualTo("https://w3id.org/gaia-x/development#");
		assertThatJsonStringValue("$['@context'][2]['@base']", serializedVc).isEqualTo("https://dawex.com");

		assertThatJsonListValue("$['type']", serializedVc).hasSize(2)
				.contains("VerifiableCredential", "gx:DataProduct");
		assertThatJsonStringValue("$['id']", serializedVc)
				.isEqualTo("./dataOfferings/62bab5ae84fd784b1541e8f3/vc");
		assertThatJsonStringValue("$['issuer']", serializedVc).isEqualTo("62b570acb33e417ed-issuer");
		assertThatJsonStringValue("$['validFrom']", serializedVc).isEqualTo("2025-02-21T15:38:02Z");
		assertThatJsonStringValue("$['validUntil']", serializedVc).isEqualTo("2025-05-21T15:38:02Z");

		assertThatJsonStringValue("$['credentialSubject']['id']", serializedVc)
				.isEqualTo("./dataOfferings/62bab5ae84fd784-dataProduct/cs");
		assertThatJsonStringValue("$['credentialSubject']['schema:name']", serializedVc)
				.isEqualTo("Statistics of road accidents in France");
		assertThatJsonStringValue("$['credentialSubject']['schema:description']", serializedVc)
				.isEqualTo("This publication provides data on road accidents in France.");
		assertThatJsonStringValue("$['credentialSubject']['dcterms:identifier']", serializedVc)
				.isEqualTo("62bab5ae84fd784-dataProduct-identifier");
		assertThatJsonStringValue("$['credentialSubject']['dcterms:title']", serializedVc)
				.isEqualTo("62bab5ae84fd784-dataProduct-title");
		assertThatJsonMapValue("$['credentialSubject']['dcterms:issued']", serializedVc).hasSize(2)
				.containsExactlyInAnyOrderEntriesOf(Map.of(
						"@type", "xsd:date",
						"@value", "2025-03-14Z"));
		assertThatJsonStringValue("$['credentialSubject']['gx:termsAndConditions']", serializedVc)
				.isEqualTo("62bab5ae84fd784-dataProduct-terms-and-conditions");
		assertThatJsonListValue("$['credentialSubject']['dcterms:license']", serializedVc).hasSize(1)
				.first().asString().isEqualTo("62bab5ae84fd784-dataProduct-license");
		assertThatJsonListValue("$['credentialSubject']['gx:aggregationOf']", serializedVc).hasSize(1)
				.first().asInstanceOf(MAP)
				.containsExactlyInAnyOrderEntriesOf(Map.of(
						"type", "gx:DataSet",
						"id", "./aggregationOf/62bab5ae84fd784-dataset"));

		assertThatJsonStringValue("$['credentialSubject']['gx:providedBy']['id']", serializedVc)
				.isEqualTo("./providedBy/62b570acb33e417-provider");
		assertThatJsonListValue("$['credentialSubject']['gx:serviceOfferingTermsAndConditions']", serializedVc).hasSize(1);
		assertThatJsonStringValue("$['credentialSubject']['gx:serviceOfferingTermsAndConditions'][0]['type']", serializedVc)
				.isEqualTo("gx:TermsAndConditions");
		assertThatJsonMapValue("$['credentialSubject']['gx:serviceOfferingTermsAndConditions'][0]['gx:url']", serializedVc)
				.containsExactlyInAnyOrderEntriesOf(Map.of("@type", "xsd:anyURI", "@value", "./termsAndConditions/60f5ab"));
		assertThatJsonStringValue("$['credentialSubject']['gx:serviceOfferingTermsAndConditions'][0]['gx:hash']", serializedVc)
				.isEqualTo("d8402a23de560f5ab34b22d1a142feb9e13b3143");

		assertThatJsonListValue("$['credentialSubject']['gx:servicePolicy']", serializedVc).hasSize(1)
				.first().asInstanceOf(MAP)
				.containsExactlyInAnyOrderEntriesOf(Map.of(
						"type", "gx:AccessUsagePolicy",
						"gx:policyLanguage", "Rego",
						"gx:policyDocument", "allow { true }"));

		assertThatJsonListValue("$['credentialSubject']['gx:dataAccountExport']", serializedVc).hasSize(1)
				.first().asInstanceOf(MAP)
				.containsExactlyInAnyOrderEntriesOf(Map.of(
						"type", "gx:DataAccountExport",
						"gx:requestType", "API",
						"gx:accessType", "digital",
						"gx:formatType", "application/json"));

		assertThatJsonListValue("$['credentialSubject']['gx:aggregationOfResources']", serializedVc).hasSize(1)
				.first().asInstanceOf(MAP)
				.containsExactlyInAnyOrderEntriesOf(Map.of(
						"type", "gx:PhysicalResource",
						"id", "./aggregationOfResources/62bac14584fd784b1541e9cb"));

		assertThatJsonListValue("$['credentialSubject']['gx:legalDocuments']", serializedVc).hasSize(5)
				.satisfies(list -> {
					final List<Map<String, ?>> legalDocuments = (List<Map<String, ?>>) list;
					assertThatLegalDocumentsContains(legalDocuments, "gx:CustomerDataAccessTerms", "customerDataAccessTermsId");
					assertThatLegalDocumentsContains(legalDocuments, "gx:CustomerDataProcessingTerms", "customerDataProcessingTermsId");
					assertThatLegalDocumentsContains(legalDocuments, "gx:DocumentChangeProcedures", "documentChangeProceduresId");
					assertThatLegalDocumentsContains(legalDocuments, "gx:LegallyBindingAct", "legallyBindingActId");
					assertThatLegalDocumentsContains(legalDocuments, "gx:LegalDocument", "legalDocumentId");
				});
		assertThatJsonListValue("$['credentialSubject']['gx:subContractors']", serializedVc).hasSize(1)
				.first().asInstanceOf(MAP)
				.satisfies(jur -> {
					assertThat(jur).hasSize(5);
					assertThat(jur).containsEntry("type", "gx:SubContractor");
					assertThat(jur).containsEntry("gx:applicableJurisdiction", "applicableJurisdiction");
					assertThat(jur).containsEntry("gx:legalName", "legalName");
					assertThat(jur).containsKey("gx:informationDocuments")
							.extractingByKey("gx:communicationMethods").asInstanceOf(LIST).hasSize(1)
							.first().asInstanceOf(MAP)
							.containsExactlyInAnyOrderEntriesOf(Map.of("type", "gx:LegalDocument", "id", "legalDocumentId"));
					assertThat(jur).containsKey("gx:informationDocuments")
							.extractingByKey("gx:informationDocuments").asInstanceOf(LIST).hasSize(1)
							.first().asInstanceOf(MAP)
							.containsExactlyInAnyOrderEntriesOf(Map.of("type", "gx:LegalDocument", "id", "legalDocumentId"));
				});
		assertThatJsonListValue("$['credentialSubject']['gx:requiredMeasures']", serializedVc).hasSize(1)
				.first().asInstanceOf(MAP)
				.satisfies(measure -> {
					assertThat(measure).hasSize(3);
					assertThat(measure).containsEntry("type", "gx:Measure");
					assertThat(measure).containsEntry("schema:description", "Required measure description");
					assertThat(measure).containsKey("gx:legalDocuments")
							.extractingByKey("gx:legalDocuments").asInstanceOf(LIST).hasSize(1)
							.first().asInstanceOf(MAP)
							.containsExactlyInAnyOrderEntriesOf(Map.of("type", "gx:LegalDocument", "id", "legalDocumentId"));
				});
		assertThatJsonMapValue("$['credentialSubject']['gx:providerContactInformation']", serializedVc)
				.satisfies(info -> {
					assertThat(info).extractingByKey("gx:email").isEqualTo("contact@dawex.com");
					assertThat(info).extractingByKey("gx:phoneNumber").isEqualTo("0123456789");
					assertThat(info).extractingByKey("gx:url").asInstanceOf(MAP)
							.containsExactlyInAnyOrderEntriesOf(Map.of(
									"@type", "xsd:anyURI",
									"@value", "https://dawex.com/contact"));
					assertThat(info).extractingByKey("gx:postalAddress").asInstanceOf(MAP)
							.containsExactlyInAnyOrderEntriesOf(Map.of(
									"type", "gx:Address",
									"vcard:street-address", "7 rue Grenette",
									"vcard:postal-code", "74000",
									"gx:region", "Savoie",
									"vcard:locality", "Annecy",
									"gx:countryCode", "FRA",
									"gx:countryName", "France"));
				});
	}

	private static void assertThatLegalDocumentsContains(List<Map<String, ?>> legalDocuments, String type, String id) {
		assertThat(legalDocuments).filteredOn(doc -> doc.get("type").equals(type)).hasSize(1)
				.first().asInstanceOf(MAP)
				.containsExactlyInAnyOrderEntriesOf(Map.of("type", type, "id", id));
	}

	private static DataProductVerifiableCredential getDataProductVerifiableCredential() {
		return DataProductVerifiableCredential.builder()
				.id("62bab5ae84fd784b1541e8f3")
				.issuer("62b570acb33e417ed-issuer")
				.validFrom(LocalDate.of(2025, Month.FEBRUARY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.validUntil(LocalDate.of(2025, Month.MAY, 21).atTime(15, 38, 2).atZone(ZoneOffset.UTC))
				.credentialSubject(DataProductCredentialSubject.builder()
						.identifier("62bab5ae84fd784-dataProduct-identifier")
						.title("62bab5ae84fd784-dataProduct-title")
						.issued(LocalDate.of(2025, 3, 14))
						.termsAndConditions("62bab5ae84fd784-dataProduct-terms-and-conditions")
						.licenses(List.of("62bab5ae84fd784-dataProduct-license"))
						.aggregationOf(List.of(new AggregationOf("62bab5ae84fd784-dataset")))
						.id("62bab5ae84fd784-dataProduct")
						.name("Statistics of road accidents in France")
						.description("This publication provides data on road accidents in France.")
						.providedBy(new ProvidedByDataProducer("62b570acb33e417-provider"))
						.serviceOfferingTermsAndConditions(List.of(TermsAndConditions.builder()
								.url("60f5ab")
								.hash("d8402a23de560f5ab34b22d1a142feb9e13b3143")
								.build()))
						.servicePolicy(List.of(AccessUsagePolicy.builder()
								.policyLanguage(PolicyLanguage.REGO)
								.policyDocument("allow { true }")
								.build()))
						.dataAccountExport(List.of(DataAccountExport.builder()
								.requestType(RequestType.API)
								.accessType(AccessType.DIGITAL)
								.formatType(MimeType.APPLICATION_JSON)
								.build()))
						.aggregationOfResources(List.of(new AggregationOfResource("62bac14584fd784b1541e9cb")))
						.legalDocuments(List.of(
								new CustomerDataAccessTerms("customerDataAccessTermsId"),
								new CustomerDataProcessingTerms("customerDataProcessingTermsId"),
								new DocumentChangeProcedures("documentChangeProceduresId"),
								new LegallyBindingAct("legallyBindingActId"),
								new LegalDocument("legalDocumentId")
						))
						.subContractors(List.of(
								SubContractor.builder()
										.applicableJurisdiction("applicableJurisdiction")
										.legalName("legalName")
										.communicationMethods(List.of(new LegalDocument("legalDocumentId")))
										.informationDocuments(List.of(new LegalDocument("legalDocumentId")))
										.build()
						))
						.requiredMeasures(List.of(
								Measure.builder()
										.description("Required measure description")
										.legalDocuments(List.of(new LegalDocument("legalDocumentId")))
										.build()))
						.providerContactInformation(ContactInformation.builder()
								.email("contact@dawex.com")
								.phoneNumber("0123456789")
								.url("https://dawex.com/contact")
								.postalAddress(Address.builder()
										.streetAddress("7 rue Grenette")
										.postalCode("74000")
										.region("Savoie")
										.locality("Annecy")
										.countryCode("FRA")
										.countryName("France")
										.build())
								.build())
						.build())
				.build();
	}
}
