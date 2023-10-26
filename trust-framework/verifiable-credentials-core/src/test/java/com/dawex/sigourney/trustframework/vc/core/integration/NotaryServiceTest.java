package com.dawex.sigourney.trustframework.vc.core.integration;

import com.dawex.sigourney.notary.client.ApiException;
import com.dawex.sigourney.notary.client.RegistrationNumberVcApi;
import com.dawex.sigourney.notary.client.dto.CheckRegistrationNumberVCRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NotaryServiceTest {

	private static final String REGISTRATION_NUMBER = "registrationNumber";

	private NotaryService service;

	private RegistrationNumberVcApi registrationNumberVcApi;

	@BeforeEach
	void setUp() {
		registrationNumberVcApi = mock(RegistrationNumberVcApi.class);
		service = new NotaryService(registrationNumberVcApi);
	}

	@Test
	void shouldGetRegistrationNumberVCWithEORI() throws ApiException {
		final CheckRegistrationNumberVCRequest actualRequest = shouldGetRegistrationNumberVC(NotaryService.RegistrationNumberType.EORI);

		assertThat(actualRequest.getGxColonEORI()).isEqualTo(REGISTRATION_NUMBER);
		assertThat(actualRequest.getGxColonLeiCode()).isNull();
		assertThat(actualRequest.getGxColonVatID()).isNull();
	}

	@Test
	void shouldGetRegistrationNumberVCWithLeiCode() throws ApiException {
		final CheckRegistrationNumberVCRequest actualRequest = shouldGetRegistrationNumberVC(NotaryService.RegistrationNumberType.LEI_CODE);

		assertThat(actualRequest.getGxColonEORI()).isNull();
		assertThat(actualRequest.getGxColonLeiCode()).isEqualTo(REGISTRATION_NUMBER);
		assertThat(actualRequest.getGxColonVatID()).isNull();
	}

	@Test
	void shouldGetRegistrationNumberVCWithVAT() throws ApiException {
		final CheckRegistrationNumberVCRequest actualRequest = shouldGetRegistrationNumberVC(NotaryService.RegistrationNumberType.VAT_ID);

		assertThat(actualRequest.getGxColonEORI()).isNull();
		assertThat(actualRequest.getGxColonLeiCode()).isNull();
		assertThat(actualRequest.getGxColonVatID()).isEqualTo(REGISTRATION_NUMBER);
	}

	private CheckRegistrationNumberVCRequest shouldGetRegistrationNumberVC(NotaryService.RegistrationNumberType registrationNumberType)
			throws ApiException {
		final var vcId = "did:web:dawex.com:registrationNumber";

		when(registrationNumberVcApi.checkRegistrationNumberVC(any(), eq(vcId))).thenReturn(Map.of("id", vcId));

		final NotaryService.RegistrationNumberVC actual = service.getRegistrationNumberVC(REGISTRATION_NUMBER, registrationNumberType, vcId);

		assertThat(actual).isNotNull();
		assertThat(actual.vcId()).isEqualTo(vcId);
		assertThat(actual.content())
				.hasSize(1)
				.containsEntry("id", vcId);

		final ArgumentCaptor<CheckRegistrationNumberVCRequest> captor = ArgumentCaptor.forClass(CheckRegistrationNumberVCRequest.class);
		verify(registrationNumberVcApi).checkRegistrationNumberVC(captor.capture(), anyString());

		final CheckRegistrationNumberVCRequest actualRequest = captor.getValue();
		assertThat(actualRequest.getId()).isEqualTo(vcId);
		assertThat(actualRequest.getType()).isEqualTo(CheckRegistrationNumberVCRequest.TypeEnum.GX_LEGALREGISTRATIONNUMBER);
		return actualRequest;
	}
}