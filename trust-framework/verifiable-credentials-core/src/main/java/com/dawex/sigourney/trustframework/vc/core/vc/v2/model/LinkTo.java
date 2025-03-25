package com.dawex.sigourney.trustframework.vc.core.vc.v2.model;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdProperty;

import java.util.Objects;

public class LinkTo {

	@JsonLdProperty("id")
	private final String id;

	public LinkTo(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LinkTo that = (LinkTo) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "LinkTo{" +
				"id='" + id + '\'' +
				'}';
	}
}
