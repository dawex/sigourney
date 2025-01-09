package com.dawex.sigourney.trustframework.vc.core.utils;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.ObjectAssert;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

public class TestUtils {

	private TestUtils() {
		// no instance allowed
	}

	public static AbstractStringAssert<?> assertThatJsonStringValue(String jsonPath, String json) {
		return assertThat((String) JsonPath.compile(jsonPath).read(json));
	}

	public static AbstractListAssert<?, List<?>, Object, ObjectAssert<Object>> assertThatJsonListValue(String jsonPath, String json) {
		return assertThat((Object) JsonPath.compile(jsonPath).read(json)).asInstanceOf(LIST);
	}
}
