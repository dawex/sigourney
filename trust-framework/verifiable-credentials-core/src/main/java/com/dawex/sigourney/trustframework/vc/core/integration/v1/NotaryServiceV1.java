package com.dawex.sigourney.trustframework.vc.core.integration.v1;

import com.dawex.sigourney.notary.client.v1.ApiClient;
import com.dawex.sigourney.notary.client.v1.ApiException;
import com.dawex.sigourney.notary.client.v1.RegistrationNumberVcApi;
import com.dawex.sigourney.notary.client.v1.dto.CheckRegistrationNumberVCRequest;
import com.dawex.sigourney.trustframework.vc.core.integration.NotaryService;
import com.dawex.sigourney.trustframework.vc.core.integration.NotaryServiceException;
import com.dawex.sigourney.trustframework.vc.core.integration.NotaryServiceFactory;
import com.dawex.sigourney.trustframework.vc.core.integration.model.RegistrationNumberType;
import com.dawex.sigourney.trustframework.vc.core.integration.model.RegistrationNumberVC;
import tools.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

import static com.dawex.sigourney.trustframework.vc.core.integration.utils.Check.verifyNonEmpty;
import static com.dawex.sigourney.trustframework.vc.core.integration.utils.Check.verifyNonNull;

/**
 * This class provides utilities to request the Notarization API for validating a registration number.
 * Use {@link NotaryServiceFactory} for creating a new instance of NotaryService.
 */
public class NotaryServiceV1 implements NotaryService {

	private final RegistrationNumberVcApi registrationNumberVcApi;

	protected NotaryServiceV1(RegistrationNumberVcApi registrationNumberVcApi) {
		this.registrationNumberVcApi = registrationNumberVcApi;
	}

	public NotaryServiceV1() {
		this(new RegistrationNumberVcApi());
	}

	public NotaryServiceV1(HttpClient.Builder httpClientBuilder, ObjectMapper mapper, String baseUri) {
		this(new RegistrationNumberVcApi(new ApiClient(httpClientBuilder, mapper, baseUri)));
	}

	public RegistrationNumberVC getRegistrationNumberVC(String registrationNumber, RegistrationNumberType registrationNumberType,
			String vcId) throws NotaryServiceException {
		verifyNonEmpty(registrationNumber).orThrowWithMessage("Registration number is required");
		verifyNonNull(registrationNumberType).orThrowWithMessage("Registration number type is required");
		verifyNonEmpty(vcId).orThrowWithMessage("Verifiable credential id is required");
		try {
			final CheckRegistrationNumberVCRequest request = new CheckRegistrationNumberVCRequest()
					.id(vcId)
					.type(CheckRegistrationNumberVCRequest.TypeEnum.GX_LEGAL_REGISTRATION_NUMBER);

			switch (registrationNumberType) {
				case EORI -> request.gxEORI(registrationNumber);
				case LEI_CODE -> request.gxLeiCode(registrationNumber);
				case VAT_ID -> request.gxVatID(registrationNumber);
				default -> throw new NotaryServiceException("Unsupported registration number type [%s]".formatted(registrationNumberType));
			}

			final Object content = registrationNumberVcApi.checkRegistrationNumberVC(request, vcId);
			return new RegistrationNumberVC(vcId, content);

		} catch (ApiException e) {
			throw new NotaryServiceException(e);
		}
	}
}
