package com.dawex.sigourney.trustframework.vc.model.v2411.common;

public enum GaiaxTermsAndConditions {
	V2411("4bd7554097444c960292b4726c2efa1373485e8a5565d94d41195214c5e0ceb3");

	private final String value;

	GaiaxTermsAndConditions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return getValue();
	}
}
