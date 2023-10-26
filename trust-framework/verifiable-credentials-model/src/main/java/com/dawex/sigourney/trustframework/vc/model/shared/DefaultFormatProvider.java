package com.dawex.sigourney.trustframework.vc.model.shared;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.FormatProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class DefaultFormatProvider implements FormatProvider {

	private final Map<String, Supplier<String>> formats = new HashMap<>();

	@Override
	public Optional<String> getFormat(String formatName) {
		if (!formats.containsKey(formatName)) {
			return Optional.empty();
		}
		return Optional.ofNullable(formats.get(formatName).get());
	}

	/**
	 * Sets the specified format.
	 *
	 * @param formatName is the name used in {@link JsonLdProperty#formatName}
	 * @param format     is the format, following the {@link java.util.Formatter} syntax, where the argument is replaced by the attribute value
	 */
	public void setFormat(String formatName, String format) {
		formats.put(formatName, () -> format);
	}

	/**
	 * Sets the specified format.
	 *
	 * @param formatName     is the name used in {@link JsonLdProperty#formatName}
	 * @param formatSupplier supplies the format, that follows the {@link java.util.Formatter} syntax, where the argument is replaced by the attribute value
	 */
	public void setFormat(String formatName, Supplier<String> formatSupplier) {
		formats.put(formatName, formatSupplier);
	}
}
