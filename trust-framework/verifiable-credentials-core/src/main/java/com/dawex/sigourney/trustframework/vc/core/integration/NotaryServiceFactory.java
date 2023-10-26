package com.dawex.sigourney.trustframework.vc.core.integration;

import com.dawex.sigourney.notary.client.ApiClient;
import com.dawex.sigourney.notary.client.RegistrationNumberVcApi;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

public class NotaryServiceFactory {

	public NotaryService create() {
		return new NotaryService(new RegistrationNumberVcApi());
	}

	public NotaryService create(HttpClient.Builder httpClientBuilder, ObjectMapper mapper, String baseUri) {
		final RegistrationNumberVcApi registrationNumberVcApi = new RegistrationNumberVcApi(
				new ApiClient(httpClientBuilder, mapper, baseUri));
		return new NotaryService(registrationNumberVcApi);
	}
}
