package com.dawex.sigourney.trustframework.vc.core.integration;

import com.dawex.sigourney.trustframework.vc.core.integration.v1.NotaryServiceV1;
import com.dawex.sigourney.trustframework.vc.core.integration.v2.NotaryServiceV2;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.net.http.HttpClient;

import static org.assertj.core.api.Assertions.assertThat;

class NotaryServiceFactoryTest {

	@Test
	void shouldCreateNotaryServiceV1() {
		assertThat(NotaryServiceFactory.create(GaiaxVersion.V1))
				.isInstanceOf(NotaryServiceV1.class);
		assertThat(NotaryServiceFactory.create(GaiaxVersion.V1, HttpClient.newBuilder(), new JsonMapper(), "uri"))
				.isInstanceOf(NotaryServiceV1.class);
	}

	@Test
	void shouldCreateNotaryServiceV2() {
		assertThat(NotaryServiceFactory.create(GaiaxVersion.V2))
				.isInstanceOf(NotaryServiceV2.class);
		assertThat(NotaryServiceFactory.create(GaiaxVersion.V2, HttpClient.newBuilder(), new JsonMapper(), "uri"))
				.isInstanceOf(NotaryServiceV2.class);
	}
}