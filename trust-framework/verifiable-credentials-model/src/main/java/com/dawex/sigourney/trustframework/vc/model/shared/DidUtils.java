package com.dawex.sigourney.trustframework.vc.model.shared;

public class DidUtils {

	/**
	 * Converts the base URL into a DID web, as defined in https://w3c-ccg.github.io/did-method-web/#read-resolve.
	 */
	public static String toDidWeb(String baseUrl) {
		if (baseUrl == null || (!baseUrl.startsWith("https://") && !baseUrl.startsWith("http://"))) {
			return null;
		}
		final String path = baseUrl.replaceFirst("https?:\\/\\/", "")
				.replaceFirst("\\/\\.well-known\\/did\\.json", "")
				.replaceFirst("\\/did\\.json", "")
				.replace(":", "%3A")
				.replace("/", ":");
		return "did:web:" + path;
	}

	private DidUtils() {
		// no instance
	}
}
