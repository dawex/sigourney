package com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering;

public enum RequestType {
	API("API"),
	EMAIL("email"),
	WEBFORM("webform"),
	UNREGISTERED_LETTER("unregisteredLetter"),
	REGISTERED_LETTER("registeredLetter"),
	SUPPORT_CENTER("supportCenter");

	private final String value;

	RequestType(String value) {
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
