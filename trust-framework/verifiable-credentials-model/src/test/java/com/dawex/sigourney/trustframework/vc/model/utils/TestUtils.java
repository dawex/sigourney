package com.dawex.sigourney.trustframework.vc.model.utils;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.MapAssert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

public class TestUtils {

	private TestUtils() {
		// no instance allowed
	}

	public static AbstractBooleanAssert<?> assertThatJsonBooleanValue(String jsonPath, String json) {
		return assertThat((Boolean) JsonPath.compile(jsonPath).read(json));
	}

	public static AbstractStringAssert<?> assertThatJsonStringValue(String jsonPath, String json) {
		return assertThat((String) JsonPath.compile(jsonPath).read(json));
	}

	public static ListAssert<Object> assertThatJsonListValue(String jsonPath, String json) {
		return assertThat((Object) JsonPath.compile(jsonPath).read(json)).asInstanceOf(LIST);
	}

	public static MapAssert<Object, Object> assertThatJsonMapValue(String jsonPath, String json) {
		return assertThat((Object) JsonPath.compile(jsonPath).read(json)).asInstanceOf(MAP);
	}
}
