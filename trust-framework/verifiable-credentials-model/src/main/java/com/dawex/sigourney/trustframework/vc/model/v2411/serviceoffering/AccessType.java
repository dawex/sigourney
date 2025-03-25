package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

public enum AccessType {
	DIGITAL("digital"),
	PHYSICAL("physical");

	private final String value;

	AccessType(String value) {
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
