package com.dawex.sigourney.trustframework.vc.model.v2411.common;

public enum PolicyLanguage {
	ODRL("ODRL"),
	XACML("XACML"),
	REGO("Rego"),
	JSON("JSON"),
	OTHER("Other");

	private final String value;

	PolicyLanguage(String value) {
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
