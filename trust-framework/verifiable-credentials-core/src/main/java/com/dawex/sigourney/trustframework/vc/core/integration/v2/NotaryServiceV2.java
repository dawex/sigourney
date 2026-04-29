package com.dawex.sigourney.trustframework.vc.core.integration.v2;

import com.dawex.sigourney.notary.client.v2.ApiClient;
import com.dawex.sigourney.notary.client.v2.ApiException;
import com.dawex.sigourney.trustframework.vc.core.integration.NotaryService;
import com.dawex.sigourney.trustframework.vc.core.integration.NotaryServiceException;
import com.dawex.sigourney.trustframework.vc.core.integration.NotaryServiceFactory;
import com.dawex.sigourney.trustframework.vc.core.integration.model.RegistrationNumberType;
import com.dawex.sigourney.trustframework.vc.core.integration.model.RegistrationNumberVC;
import com.dawex.sigourney.trustframework.vc.core.integration.v2.client.RegistrationNumberApi;
import tools.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

import static com.dawex.sigourney.trustframework.vc.core.integration.utils.Check.verifyNonEmpty;
import static com.dawex.sigourney.trustframework.vc.core.integration.utils.Check.verifyNonNull;

/**
 * This class provides utilities to request the Notarization API for validating a registration number.
 * Use {@link NotaryServiceFactory} for creating a new instance of NotaryService.
 */
public class NotaryServiceV2 implements NotaryService {

	private final RegistrationNumberApi registrationNumberApi;

	protected NotaryServiceV2(RegistrationNumberApi registrationNumberApi) {
		this.registrationNumberApi = registrationNumberApi;
	}

	public NotaryServiceV2() {
		this(new RegistrationNumberApi());
	}

	public NotaryServiceV2(HttpClient.Builder httpClientBuilder, ObjectMapper mapper, String baseUri) {
		this(new RegistrationNumberApi(new ApiClient(httpClientBuilder, mapper, baseUri)));
	}

	public RegistrationNumberVC getRegistrationNumberVC(String registrationNumber, RegistrationNumberType registrationNumberType,
			String vcId) throws NotaryServiceException {
		verifyNonEmpty(registrationNumber).orThrowWithMessage("Registration number is required");
		verifyNonNull(registrationNumberType).orThrowWithMessage("Registration number type is required");
		verifyNonEmpty(vcId).orThrowWithMessage("Verifiable credential id is required");
		try {
			final String content = switch (registrationNumberType) {
				case EORI -> registrationNumberApi.eoriControllerCheckEori(vcId, vcId, registrationNumber);
				case LEI_CODE -> registrationNumberApi.leiCodeControllerCheckLeiCode(vcId, vcId, registrationNumber);
				case TAX_ID -> registrationNumberApi.openCorporatesTaxIdControllerCheckTaxId(vcId, vcId, registrationNumber);
				case VAT_ID -> registrationNumberApi.vatIdControllerCheckVatId(vcId, vcId, registrationNumber);
				default -> throw new NotaryServiceException("Unsupported registration number type [%s]".formatted(registrationNumberType));
			};
			return new RegistrationNumberVC(vcId, content);

		} catch (ApiException e) {
			throw new NotaryServiceException(e);
		}
	}
}
