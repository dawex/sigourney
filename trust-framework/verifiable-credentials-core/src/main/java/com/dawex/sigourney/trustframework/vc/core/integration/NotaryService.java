package com.dawex.sigourney.trustframework.vc.core.integration;

import com.dawex.sigourney.notary.client.ApiException;
import com.dawex.sigourney.notary.client.RegistrationNumberVcApi;
import com.dawex.sigourney.notary.client.dto.CheckRegistrationNumberVCRequest;

import java.util.Map;

/**
 * This class provides utilities to request the Notarization API for validating a registration number.
 * Use {@link NotaryServiceFactory} for creating a new instance of NotaryService.
 */
public class NotaryService {

	private final RegistrationNumberVcApi registrationNumberVcApi;

	protected NotaryService(RegistrationNumberVcApi registrationNumberVcApi) {
		this.registrationNumberVcApi = registrationNumberVcApi;
	}

	public RegistrationNumberVC getRegistrationNumberVC(String registrationNumber, RegistrationNumberType registrationNumberType,
			String vcId) throws ApiException {
		final CheckRegistrationNumberVCRequest request = new CheckRegistrationNumberVCRequest()
				.id(vcId)
				.type(CheckRegistrationNumberVCRequest.TypeEnum.GX_LEGALREGISTRATIONNUMBER);

		switch (registrationNumberType) {
			case EORI -> request.setGxColonEORI(registrationNumber);
			case LEI_CODE -> request.setGxColonLeiCode(registrationNumber);
			case VAT_ID -> request.setGxColonVatID(registrationNumber);
		}

		final Object content = registrationNumberVcApi.checkRegistrationNumberVC(request, vcId);
		return new RegistrationNumberVC(vcId, (Map) content);
	}

	public record RegistrationNumberVC(String vcId, Map content) {
	}

	public enum RegistrationNumberType {
		EORI,
		LEI_CODE,
		VAT_ID
	}
}
