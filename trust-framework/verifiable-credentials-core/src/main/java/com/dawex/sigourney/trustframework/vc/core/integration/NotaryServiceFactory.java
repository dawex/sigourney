package com.dawex.sigourney.trustframework.vc.core.integration;

import com.dawex.sigourney.trustframework.vc.core.integration.v1.NotaryServiceV1;
import com.dawex.sigourney.trustframework.vc.core.integration.v2.NotaryServiceV2;
import tools.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

public class NotaryServiceFactory {

	public static NotaryService create(GaiaxVersion gaiaxVersion) {
		return switch (gaiaxVersion) {
			case V1 -> new NotaryServiceV1();
			case V2 -> new NotaryServiceV2();
		};
	}

	public static NotaryService create(GaiaxVersion gaiaxVersion, HttpClient.Builder httpClientBuilder, ObjectMapper mapper,
			String baseUri) {
		return switch (gaiaxVersion) {
			case V1 -> new NotaryServiceV1(httpClientBuilder, mapper, baseUri);
			case V2 -> new NotaryServiceV2(httpClientBuilder, mapper, baseUri);
		};
	}

	private NotaryServiceFactory() {
		// no instance allowed
	}
}
