package com.dawex.sigourney.trustframework.vc.model.v2411.serialization;

import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdContexts;
import com.dawex.sigourney.trustframework.vc.core.jsonld.annotation.JsonLdType;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.FormatProvider;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.JsonLdContextsSerializer;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.JsonLdSerializer;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.JsonLdTypeSerializer;
import com.dawex.sigourney.trustframework.vc.core.jsonld.serialization.SignedObjectJsonLdSerializer;
import com.dawex.sigourney.trustframework.vc.core.vc.signature.model.Proof;
import com.dawex.sigourney.trustframework.vc.core.vc.signature.model.SignedObject;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.EnvelopedVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.core.vc.v2.model.VerifiablePresentation;
import com.dawex.sigourney.trustframework.vc.model.shared.Did;
import com.dawex.sigourney.trustframework.vc.model.shared.JsonWebKey2020;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.AccessUsagePolicy;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.Address;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.ContactInformation;
import com.dawex.sigourney.trustframework.vc.model.v2411.common.TermsAndConditions;
import com.dawex.sigourney.trustframework.vc.model.v2411.dataresource.CopyrightOwnedBy;
import com.dawex.sigourney.trustframework.vc.model.v2411.dataresource.DataResourceCredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.dataresource.DataResourceVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2411.dataresource.ExposedThrough;
import com.dawex.sigourney.trustframework.vc.model.v2411.dataresource.ProducedBy;
import com.dawex.sigourney.trustframework.vc.model.v2411.issuer.IssuerCredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.issuer.IssuerVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument.CustomerDataAccessTermsVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument.CustomerDataProcessingTermsVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument.DocumentChangeProceduresVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument.InvolvedParty;
import com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument.LegalDocumentCredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument.LegalDocumentVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2411.legaldocument.LegallyBindingActVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2411.legalperson.LegalPersonCredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.legalperson.LegalPersonVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2411.legalperson.RegistrationNumber;
import com.dawex.sigourney.trustframework.vc.model.v2411.physicalresource.MaintainedBy;
import com.dawex.sigourney.trustframework.vc.model.v2411.physicalresource.PhysicalResourceCredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.physicalresource.PhysicalResourceVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.AggregationOfResource;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.DataAccountExport;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.Measure;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.ProvidedBy;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.ServiceOfferingCredentialSubject;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.ServiceOfferingVerifiableCredential;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.SubContractor;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.CustomerDataAccessTerms;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.CustomerDataProcessingTerms;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.DocumentChangeProcedures;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.LegalDocument;
import com.dawex.sigourney.trustframework.vc.model.v2411.serviceoffering.legaldocument.LegallyBindingAct;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;
import java.util.function.Supplier;

public class JacksonModuleFactory {

	/**
	 * Create a configured Jackson module for serializing legalPerson verifiable credentials
	 */
	public static Module legalPersonSerializationModule(FormatProvider formatProvider, Supplier<String> baseIriSupplier) {
		final List<Class> domainClasses = List.of(Address.class,
				LegalPersonCredentialSubject.class,
				RegistrationNumber.class,
				LegalPersonVerifiableCredential.class);
		return createVerifiableCredentialSerializationModule(formatProvider, baseIriSupplier, domainClasses);
	}

	/**
	 * Create a configured Jackson module for serializing data product verifiable credentials
	 */
	public static Module serviceOfferingSerializationModule(FormatProvider formatProvider, Supplier<String> baseIriSupplier) {
		final List<Class> domainClasses = List.of(
				AccessUsagePolicy.class,
				Address.class,
				AggregationOfResource.class,
				ContactInformation.class,
				CustomerDataAccessTerms.class,
				CustomerDataProcessingTerms.class,
				DataAccountExport.class,
				DocumentChangeProcedures.class,
				LegallyBindingAct.class,
				LegalDocument.class,
				Measure.class,
				ServiceOfferingCredentialSubject.class,
				ServiceOfferingVerifiableCredential.class,
				SubContractor.class,
				ProvidedBy.class,
				TermsAndConditions.class);
		return createVerifiableCredentialSerializationModule(formatProvider, baseIriSupplier, domainClasses);
	}

	/**
	 * Create a configured Jackson module for serializing data resources verifiable credentials
	 */
	public static Module dataResourceSerializationModule(FormatProvider formatProvider, Supplier<String> baseIriSupplier) {
		final List<Class> domainClasses = List.of(CopyrightOwnedBy.class,
				DataResourceCredentialSubject.class,
				DataResourceVerifiableCredential.class,
				ExposedThrough.class,
				ProducedBy.class);
		return createVerifiableCredentialSerializationModule(formatProvider, baseIriSupplier, domainClasses);
	}

	/**
	 * Create a configured Jackson module for serializing issuer verifiable credentials
	 */
	public static Module issuerSerializationModule(FormatProvider formatProvider, Supplier<String> baseIriSupplier) {
		final List<Class> domainClasses = List.of(IssuerCredentialSubject.class, IssuerVerifiableCredential.class);
		return createVerifiableCredentialSerializationModule(formatProvider, baseIriSupplier, domainClasses);
	}

	/**
	 * Create a configured Jackson module for serializing legal documents verifiable credentials
	 */
	public static Module legalDocumentSerializationModule(FormatProvider formatProvider, Supplier<String> baseIriSupplier) {
		final List<Class> domainClasses = List.of(
				CustomerDataAccessTermsVerifiableCredential.class,
				CustomerDataProcessingTermsVerifiableCredential.class,
				DocumentChangeProceduresVerifiableCredential.class,
				InvolvedParty.class,
				LegalDocumentCredentialSubject.class,
				LegalDocumentVerifiableCredential.class,
				LegallyBindingActVerifiableCredential.class);
		return createVerifiableCredentialSerializationModule(formatProvider, baseIriSupplier, domainClasses);
	}

	/**
	 * Create a configured Jackson module for serializing physical resource verifiable credentials
	 */
	public static Module physicalResourceSerializationModule(FormatProvider formatProvider, Supplier<String> baseIriSupplier) {
		final List<Class> domainClasses = List.of(Address.class,
				MaintainedBy.class,
				PhysicalResourceCredentialSubject.class,
				PhysicalResourceVerifiableCredential.class);
		return createVerifiableCredentialSerializationModule(formatProvider, baseIriSupplier, domainClasses);
	}

	private static SimpleModule createVerifiableCredentialSerializationModule(FormatProvider formatProvider,
			Supplier<String> baseIriSupplier, List<Class> domainClasses) {
		final SimpleModule module = new SimpleModule();

		domainClasses.forEach(clazz -> module.addSerializer(clazz, new JsonLdSerializer<>(clazz, formatProvider)));

		module.addSerializer(JsonLdContexts.class, new JsonLdContextsSerializer(baseIriSupplier));
		module.addSerializer(JsonLdType.class, new JsonLdTypeSerializer());
		module.addSerializer(Proof.class, new JsonLdSerializer<>(Proof.class, formatProvider));
		module.addSerializer(SignedObject.class, new SignedObjectJsonLdSerializer(formatProvider));
		module.addSerializer(VerifiablePresentation.class, new JsonLdSerializer<>(VerifiablePresentation.class, formatProvider));
		module.addSerializer(EnvelopedVerifiableCredential.class,
				new JsonLdSerializer<>(EnvelopedVerifiableCredential.class, formatProvider));

		return module;
	}

	/**
	 * Create a configured Jackson module for serializing public keys
	 */
	public static Module sharedSerializationModule(Supplier<String> baseIriSupplier) {
		final SimpleModule module = new SimpleModule();
		module.addSerializer(JsonLdContexts.class, new JsonLdContextsSerializer(baseIriSupplier));
		module.addSerializer(JsonLdType.class, new JsonLdTypeSerializer());
		module.addSerializer(Did.class, new JsonLdSerializer<>(Did.class));
		module.addSerializer(JsonWebKey2020.class, new JsonLdSerializer<>(JsonWebKey2020.class));
		return module;
	}

	private JacksonModuleFactory() {
		// no instance allowed
	}
}
