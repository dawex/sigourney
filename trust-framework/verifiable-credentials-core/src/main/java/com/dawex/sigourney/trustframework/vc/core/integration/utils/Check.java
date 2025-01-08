package com.dawex.sigourney.trustframework.vc.core.integration.utils;

import com.dawex.sigourney.trustframework.vc.core.integration.NotaryServiceException;

import java.util.Objects;
import java.util.function.Supplier;

public class Check {
	private final Supplier<Boolean> condition;

	private Check(Supplier<Boolean> condition) {
		this.condition = condition;
	}

	public static Check verify(Supplier<Boolean> check) {
		return new Check(check);
	}

	public static Check verifyNonNull(Object value) {
		return new Check(() -> Objects.nonNull(value));
	}

	public static Check verifyNonEmpty(String value) {
		return new Check(() -> Objects.nonNull(value) && !value.isEmpty());
	}

	public void orThrowWithMessage(String message) {
		if (Boolean.FALSE.equals(condition.get())) {
			throw new NotaryServiceException(message);
		}
	}
}
