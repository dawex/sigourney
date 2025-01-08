package com.dawex.sigourney.trustframework.vc.core.integration.v1;

import com.dawex.sigourney.notary.client.v1.ApiException;
import com.dawex.sigourney.notary.client.v1.RegistrationNumberVcApi;
import com.dawex.sigourney.notary.client.v1.dto.CheckRegistrationNumberVCRequest;
import com.dawex.sigourney.trustframework.vc.core.integration.model.RegistrationNumberType;
import com.dawex.sigourney.trustframework.vc.core.integration.model.RegistrationNumberVC;
import org.assertj.core.api.InstanceOfAssertFactories;
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

class NotaryServiceV1Test {

	private static final String REGISTRATION_NUMBER = "registrationNumber";

	private NotaryServiceV1 service;

	private RegistrationNumberVcApi registrationNumberVcApi;

	@BeforeEach
	void setUp() {
		registrationNumberVcApi = mock(RegistrationNumberVcApi.class);
		service = new NotaryServiceV1(registrationNumberVcApi);
	}

	@Test
	void shouldGetRegistrationNumberVCWithEORI() throws ApiException {
		final CheckRegistrationNumberVCRequest actualRequest = shouldGetRegistrationNumberVC(RegistrationNumberType.EORI);

		assertThat(actualRequest.getGxEORI()).isEqualTo(REGISTRATION_NUMBER);
		assertThat(actualRequest.getGxLeiCode()).isNull();
		assertThat(actualRequest.getGxVatID()).isNull();
	}

	@Test
	void shouldGetRegistrationNumberVCWithLeiCode() throws ApiException {
		final CheckRegistrationNumberVCRequest actualRequest = shouldGetRegistrationNumberVC(RegistrationNumberType.LEI_CODE);

		assertThat(actualRequest.getGxEORI()).isNull();
		assertThat(actualRequest.getGxLeiCode()).isEqualTo(REGISTRATION_NUMBER);
		assertThat(actualRequest.getGxVatID()).isNull();
	}

	@Test
	void shouldGetRegistrationNumberVCWithVAT() throws ApiException {
		final CheckRegistrationNumberVCRequest actualRequest = shouldGetRegistrationNumberVC(RegistrationNumberType.VAT_ID);

		assertThat(actualRequest.getGxEORI()).isNull();
		assertThat(actualRequest.getGxLeiCode()).isNull();
		assertThat(actualRequest.getGxVatID()).isEqualTo(REGISTRATION_NUMBER);
	}

	private CheckRegistrationNumberVCRequest shouldGetRegistrationNumberVC(RegistrationNumberType registrationNumberType)
			throws ApiException {
		final var vcId = "did:web:dawex.com:registrationNumber";

		when(registrationNumberVcApi.checkRegistrationNumberVC(any(), eq(vcId))).thenReturn(Map.of("id", vcId));

		final RegistrationNumberVC actual = service.getRegistrationNumberVC(REGISTRATION_NUMBER, registrationNumberType, vcId);

		assertThat(actual).isNotNull();
		assertThat(actual.vcId()).isEqualTo(vcId);
		assertThat(actual.content())
				.isInstanceOf(Map.class)
				.asInstanceOf(InstanceOfAssertFactories.MAP)
				.hasSize(1)
				.containsEntry("id", vcId);

		final ArgumentCaptor<CheckRegistrationNumberVCRequest> captor = ArgumentCaptor.forClass(CheckRegistrationNumberVCRequest.class);
		verify(registrationNumberVcApi).checkRegistrationNumberVC(captor.capture(), anyString());

		final CheckRegistrationNumberVCRequest actualRequest = captor.getValue();
		assertThat(actualRequest.getId()).isEqualTo(vcId);
		assertThat(actualRequest.getType()).isEqualTo(CheckRegistrationNumberVCRequest.TypeEnum.GX_LEGAL_REGISTRATION_NUMBER);
		return actualRequest;
	}
}