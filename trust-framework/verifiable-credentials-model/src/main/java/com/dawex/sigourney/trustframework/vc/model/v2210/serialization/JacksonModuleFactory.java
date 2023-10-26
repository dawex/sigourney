package com.dawex.sigourney.trustframework.vc.model.v2210.serialization;

import com.dawex.sigourney.trustframework.vc.core.Proof;
import com.dawex.sigourney.trustframework.vc.core.SignedObject;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.FormatProvider;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.JsonLdContextsSerializer;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.JsonLdSerializer;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.SignedObjectJsonLdSerializer;
import com.dawex.sigourney.trustframework.vc.model.shared.Did;
import com.dawex.sigourney.trustframework.vc.model.shared.JsonWebKey2020;
import com.dawex.sigourney.trustframework.vc.model.shared.VerifiablePresentation;
import com.dawex.sigourney.trustframework.vc.model.v2210.common.Address;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct.AggregationOf;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct.DataAccountExport;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct.DataProductCredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct.DataProductVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct.ProvidedBy;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataproduct.TermsAndConditionURI;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataresource.DataResourceVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataresource.Distribution;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataresource.ProducedBy;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataresource.DataResourceCredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataresource.ExposedThrough;
import com.dawex.sigourney.trustframework.vc.model.v2210.dataresource.Location;
import com.dawex.sigourney.trustframework.vc.model.v2210.organisation.OrganisationCredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2210.organisation.OrganisationLegalRegistrationNumber;
import com.dawex.sigourney.trustframework.vc.model.v2210.organisation.OrganisationVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2210.termsandconditions.TermsAndConditionsCredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2210.termsandconditions.TermsAndConditionsVerifiableCredential;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;
import java.util.function.Supplier;

public class JacksonModuleFactory {

	/**
	 * Create a configured Jackson module for serializing organisation verifiable credentials
	 */
	public static Module organisationSerializationModule(FormatProvider formatProvider, Supplier<String> baseIriSupplier) {
		final List<Class> domainClasses = List.of(Address.class,
				OrganisationCredentialSubject.class,
				OrganisationLegalRegistrationNumber.class,
				OrganisationVerifiableCredential.class);
		return createVerifiableCredentialSerializationModule(formatProvider, baseIriSupplier, domainClasses);
	}

	/**
	 * Create a configured Jackson module for serializing data product verifiable credentials
	 */
	public static Module dataProductSerializationModule(FormatProvider formatProvider, Supplier<String> baseIriSupplier) {
		final List<Class> domainClasses = List.of(AggregationOf.class,
				DataAccountExport.class,
				DataProductCredentialSubject.class,
				DataProductVerifiableCredential.class,
				Location.class,
				ProvidedBy.class,
				TermsAndConditionURI.class);
		return createVerifiableCredentialSerializationModule(formatProvider, baseIriSupplier, domainClasses);
	}

	/**
	 * Create a configured Jackson module for serializing terms & conditions verifiable credentials
	 */
	public static Module dataResourcesSerializationModule(FormatProvider formatProvider, Supplier<String> baseIriSupplier) {
		final List<Class> domainClasses = List.of(DataResourceCredentialSubject.class, DataResourceVerifiableCredential.class,
				Distribution.class, ExposedThrough.class, Location.class, ProducedBy.class);
		return createVerifiableCredentialSerializationModule(formatProvider, baseIriSupplier, domainClasses);
	}

	/**
	 * Create a configured Jackson module for serializing terms & conditions verifiable credentials
	 */
	public static Module termsAndConditionsSerializationModule(FormatProvider formatProvider, Supplier<String> baseIriSupplier) {
		final List<Class> domainClasses = List.of(TermsAndConditionsVerifiableCredential.class, TermsAndConditionsCredentialSubject.class);
		return createVerifiableCredentialSerializationModule(formatProvider, baseIriSupplier, domainClasses);
	}

	private static SimpleModule createVerifiableCredentialSerializationModule(FormatProvider formatProvider,
			Supplier<String> baseIriSupplier, List<Class> domainClasses) {
		final SimpleModule module = new SimpleModule();

		domainClasses.forEach(clazz -> module.addSerializer(clazz, new JsonLdSerializer<>(clazz, formatProvider)));

		module.addSerializer(JsonLdContexts.class, new JsonLdContextsSerializer(baseIriSupplier));
		module.addSerializer(Proof.class, new JsonLdSerializer<>(Proof.class, formatProvider));
		module.addSerializer(SignedObject.class, new SignedObjectJsonLdSerializer(formatProvider));
		module.addSerializer(VerifiablePresentation.class, new JsonLdSerializer<>(VerifiablePresentation.class, formatProvider));

		return module;
	}

	/**
	 * Create a configured Jackson module for serializing public keys
	 */
	public static Module sharedSerializationModule(Supplier<String> baseIriSupplier) {
		final SimpleModule module = new SimpleModule();
		module.addSerializer(JsonLdContexts.class, new JsonLdContextsSerializer(baseIriSupplier));
		module.addSerializer(Did.class, new JsonLdSerializer<>(Did.class));
		module.addSerializer(JsonWebKey2020.class, new JsonLdSerializer<>(JsonWebKey2020.class));
		return module;
	}

	private JacksonModuleFactory() {
		// no instance allowed
	}
}
