package com.dawex.sigourney.trustframework.vc.core.integration.v2;

import com.dawex.sigourney.notary.client.v2.ApiException;
import com.dawex.sigourney.trustframework.vc.core.integration.NotaryServiceException;
import com.dawex.sigourney.trustframework.vc.core.integration.model.RegistrationNumberType;
import com.dawex.sigourney.trustframework.vc.core.integration.model.RegistrationNumberVC;
import com.dawex.sigourney.trustframework.vc.core.integration.v2.client.RegistrationNumberApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class NotaryServiceV2Test {

	private static final String REGISTRATION_NUMBER = "registrationNumber";

	private NotaryServiceV2 service;

	private RegistrationNumberApi registrationNumberApi;

	@BeforeEach
	void setUp() {
		registrationNumberApi = mock(RegistrationNumberApi.class);
		service = new NotaryServiceV2(registrationNumberApi);
	}

	@Test
	void shouldGetRegistrationNumberVCWithEORI() throws ApiException {
		final var vcId = "did:web:dawex.com:registrationNumber";

		when(registrationNumberApi.eoriControllerCheckEori(vcId, vcId, REGISTRATION_NUMBER)).thenReturn("vcRegistrationNumber");

		final RegistrationNumberVC actual = service.getRegistrationNumberVC(REGISTRATION_NUMBER, RegistrationNumberType.EORI, vcId);

		assertThat(actual).isNotNull();
		assertThat(actual.vcId()).isEqualTo(vcId);
		assertThat(actual.content())
				.isInstanceOf(String.class)
				.isEqualTo("vcRegistrationNumber");
	}

	@Test
	void shouldGetRegistrationNumberVCWithLeiCode() throws ApiException {
		final var vcId = "did:web:dawex.com:registrationNumber";

		when(registrationNumberApi.leiCodeControllerCheckLeiCode(vcId, vcId, REGISTRATION_NUMBER)).thenReturn("vcRegistrationNumber");

		final RegistrationNumberVC actual = service.getRegistrationNumberVC(REGISTRATION_NUMBER, RegistrationNumberType.LEI_CODE, vcId);

		assertThat(actual).isNotNull();
		assertThat(actual.vcId()).isEqualTo(vcId);
		assertThat(actual.content())
				.isInstanceOf(String.class)
				.isEqualTo("vcRegistrationNumber");
	}

	@Test
	void shouldGetRegistrationNumberVCWithTaxId() throws ApiException {
		final var vcId = "did:web:dawex.com:registrationNumber";

		when(registrationNumberApi.openCorporatesTaxIdControllerCheckTaxId(vcId, vcId, REGISTRATION_NUMBER)).thenReturn(
				"vcRegistrationNumber");

		final RegistrationNumberVC actual = service.getRegistrationNumberVC(REGISTRATION_NUMBER, RegistrationNumberType.TAX_ID, vcId);

		assertThat(actual).isNotNull();
		assertThat(actual.vcId()).isEqualTo(vcId);
		assertThat(actual.content())
				.isInstanceOf(String.class)
				.isEqualTo("vcRegistrationNumber");
	}

	@Test
	void shouldGetRegistrationNumberVCWithVAT() throws ApiException {
		final var vcId = "did:web:dawex.com:registrationNumber";

		when(registrationNumberApi.vatIdControllerCheckVatId(vcId, vcId, REGISTRATION_NUMBER)).thenReturn("vcRegistrationNumber");

		final RegistrationNumberVC actual = service.getRegistrationNumberVC(REGISTRATION_NUMBER, RegistrationNumberType.VAT_ID, vcId);

		assertThat(actual).isNotNull();
		assertThat(actual.vcId()).isEqualTo(vcId);
		assertThat(actual.content())
				.isInstanceOf(String.class)
				.isEqualTo("vcRegistrationNumber");
	}

	@ParameterizedTest
	@NullAndEmptySource
	void withNoRegistrationNumberShouldNotGetRegistrationNumberVC(String registrationNumber) {
		assertThatThrownBy(
				() -> service.getRegistrationNumberVC(registrationNumber, RegistrationNumberType.EORI,
						"did:web:dawex.com:registrationNumber"))
				.isInstanceOf(NotaryServiceException.class);

		verifyNoInteractions(registrationNumberApi);
	}

	@ParameterizedTest
	@EnumSource(value = RegistrationNumberType.class, names = {"EUID"})
	@NullSource
	void withInvalidRegistrationTypeShouldNotGetRegistrationNumberVC(RegistrationNumberType registrationNumberType) {
		assertThatThrownBy(
				() -> service.getRegistrationNumberVC(REGISTRATION_NUMBER, registrationNumberType, "did:web:dawex.com:registrationNumber"))
				.isInstanceOf(NotaryServiceException.class);

		verifyNoInteractions(registrationNumberApi);
	}

	@ParameterizedTest
	@NullAndEmptySource
	void withNoVcIdShouldNotGetRegistrationNumberVC(String vcId) {
		assertThatThrownBy(
				() -> service.getRegistrationNumberVC(REGISTRATION_NUMBER, RegistrationNumberType.EORI, vcId))
				.isInstanceOf(NotaryServiceException.class);

		verifyNoInteractions(registrationNumberApi);
	}
}