package com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct;

import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.FormatProvider;
import com.dawex.sigourney.trustframework.vc.model.shared.DefaultFormatProvider;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.Format;
import com.dawex.sigourney.trustframework.vc.model.v2210.serialization.JacksonModuleFactory;
import com.dawex.sigourney.trustframework.vc.model.v2210.AbstractVerifiableCredentialTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;

import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonListValue;
import static com.dawex.sigourney.trustframework.vc.model.utils.TestUtils.assertThatJsonStringValue;

class DataProductVerifiableCredentialTest extends AbstractVerifiableCredentialTest {

	@Test
	void shouldGenerateValidVerifiableCredentialForDataProduct() throws JsonProcessingException {
		// given
		final var verifiableCredential = getDataProductVerifiableCredential();

		// when
		final String serializedVc = serializeVc(verifiableCredential);

		// then
		assertThatProofIsValid(serializedVc);

		assertThatJsonListValue("$['@context']", serializedVc).hasSize(4);
		assertThatJsonStringValue("$['@context'][0]['@base']", serializedVc).isEqualTo("https://dawex.com");
		assertThatJsonStringValue("$['@context'][0]['dct']", serializedVc).isEqualTo("http://purl.org/dc/terms/");
		assertThatJsonStringValue("$['@context'][1]", serializedVc).isEqualTo("https://www.w3.org/2018/credentials/v1");
		assertThatJsonStringValue("$['@context'][2]", serializedVc).isEqualTo("https://w3id.org/security/suites/jws-2020/v1");
		assertThatJsonStringValue("$['@context'][3]", serializedVc).isEqualTo(
				"https://registry.lab.gaia-x.eu/development/api/trusted-shape-registry/v1/shapes/jsonld/trustframework#");

		assertThatJsonStringValue("$['type']", serializedVc).isEqualTo("VerifiableCredential");
		assertThatJsonStringValue("$['id']", serializedVc)
				.isEqualTo(
						"./api/secure/participant/organisations/62b570acb33e417edcb345ee/dataOfferings/62bab5ae84fd784b1541e8f3/verifiableCredential");
		assertThatJsonStringValue("$['issuer']", serializedVc).isEqualTo("./organisations/62b570acb33e417ed-issuer");
		assertThatJsonStringValue("$['issuanceDate']", serializedVc).isEqualTo("2022-08-04T00:00:00Z");

		assertThatJsonStringValue("$['credentialSubject']['id']", serializedVc)
				.isEqualTo("./dataOfferings/62bab5ae84fd784-dataproduct");
		assertThatJsonStringValue("$['credentialSubject']['type']", serializedVc)
				.isEqualTo("gx:ServiceOffering");
		assertThatJsonStringValue("$['credentialSubject']['dct:title']", serializedVc)
				.isEqualTo("Statistics of road accidents in France");
		assertThatJsonStringValue("$['credentialSubject']['dct:description']", serializedVc)
				.isEqualTo("This publication provides data on road accidents in France.");
		assertThatJsonStringValue("$['credentialSubject']['dct:issued']", serializedVc).isEqualTo("2022-01-18T00:00:00Z");
		assertThatJsonStringValue("$['credentialSubject']['gx:providedBy']['id']", serializedVc)
				.isEqualTo("./organisations/62b570acb33e417-provider");
		assertThatJsonListValue("$['credentialSubject']['gx:termsAndConditions']", serializedVc)
				.hasSize(1);
		assertThatJsonStringValue("$['credentialSubject']['gx:termsAndConditions'][0]['gx:URL']", serializedVc)
				.isEqualTo("./termsAndConditions/https://dawex.com/termsAndConditions");
		assertThatJsonStringValue("$['credentialSubject']['gx:termsAndConditions'][0]['gx:hash']", serializedVc)
				.isEqualTo("d8402a23de560f5ab34b22d1a142feb9e13b3143");
		assertThatJsonListValue("$['credentialSubject']['gx:policy']", serializedVc)
				.hasSize(1)
				.first()
				.isEqualTo("policy");
		assertThatJsonStringValue("$['credentialSubject']['gx:dataAccountExport']['gx:requestType']", serializedVc)
				.isEqualTo("DataAccountExport.requestType");
		assertThatJsonStringValue("$['credentialSubject']['gx:dataAccountExport']['gx:accessType']", serializedVc)
				.isEqualTo("DataAccountExport.accessType");
		assertThatJsonStringValue("$['credentialSubject']['gx:dataAccountExport']['gx:formatType']", serializedVc)
				.isEqualTo("DataAccountExport.formatType");
		assertThatJsonListValue("$['credentialSubject']['gx:aggregationOf']", serializedVc).hasSize(1);
		assertThatJsonStringValue("$['credentialSubject']['gx:aggregationOf'][0]['id']", serializedVc)
				.isEqualTo("./dataResource/62bac14584fd784b1541e9cb");
	}

	private static DataProductVerifiableCredential getDataProductVerifiableCredential() {
		return DataProductVerifiableCredential.builder()
				.id(new DataProductVerifiableCredential.Id("62bab5ae84fd784b1541e8f3", "62b570acb33e417edcb345ee"))
				.issuer("62b570acb33e417ed-issuer")
				.issuanceDate(LocalDate.of(2022, Month.AUGUST, 4).atStartOfDay(ZoneOffset.UTC))
				.credentialSubject(DataProductCredentialSubject.builder()
						.id("62bab5ae84fd784-dataproduct")
						.title("Statistics of road accidents in France")
						.description("This publication provides data on road accidents in France.")
						.issued(LocalDate.of(2022, Month.JANUARY, 18).atStartOfDay(ZoneOffset.UTC))
						.providedBy(ProvidedBy.builder().id("62b570acb33e417-provider").build())
						.termsAndConditions(List.of(TermsAndConditionURI.builder()
								.url("https://dawex.com/termsAndConditions")
								.hash("d8402a23de560f5ab34b22d1a142feb9e13b3143")
								.build()))
						.policy(List.of("policy"))
						.dataAccountExport(DataAccountExport.builder()
								.requestType("DataAccountExport.requestType")
								.accessType("DataAccountExport.accessType")
								.formatType("DataAccountExport.formatType")
								.build())
						.aggregationOf(List.of(
								AggregationOf.builder()
										.id("62bac14584fd784b1541e9cb")
										.build()
						))
						.build())
				.build();
	}

	@Override
	protected ObjectMapper getObjectMapper() {
		final var objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.registerModule(JacksonModuleFactory.dataProductSerializationModule(getFormatProvider(), () -> "https://dawex.com"));
		return objectMapper;
	}

	private static FormatProvider getFormatProvider() {
		final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
		formatProvider.setFormat(Format.DATA_PRODUCT_AGGREGATION_OF, "./dataResource/%s");
		formatProvider.setFormat(Format.DATA_PRODUCT_CREDENTIAL_SUBJECT, "./dataOfferings/%s");
		formatProvider.setFormat(Format.DATA_PRODUCT_ISSUER, "./organisations/%s");
		formatProvider.setFormat(Format.DATA_PRODUCT_PROVIDED_BY, "./organisations/%s");
		formatProvider.setFormat(Format.DATA_PRODUCT_TERMS_AND_CONDITIONS_URI, "./termsAndConditions/%s");
		formatProvider.setFormat(Format.DATA_PRODUCT_VERIFIABLE_CREDENTIAL,
				"./api/secure/participant/organisations/%s/dataOfferings/%s/verifiableCredential");
		return formatProvider;
	}
}
