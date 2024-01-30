# Sigourney - Dawex repository for Data Exchange Open Source projects

## Description

### Verifiable credential core

Gaia-X is a project initiated by Europe where representatives from business, politics, and science from Europe and around the globe are working together, hand in hand, to create a federated and secure data infrastructure. 

The Gaia-X Trust Framework is the set of rules that define the minimum baseline to be part of the Gaia-X Ecosystem. Those rules provide a common governance and the basic level of interoperability across individual ecosystems while letting the users in full control of their choices.

The Trust Framework foresees Verifiable Credentials (VC) and linked data representations as cornerstone of its future operations.

Gaia-X Self-Descriptions are:
- machine readable texts
- cryptographically signed, preventing tampering with its content
- following the Linked Data principles to describe attributes

The format is following the W3C Verifiable Credentials Data Model.


The core library handles base operations for generating and signing verifiable credentials: 
- JSON-LD serialization made easier by providing annotations and Jackson serializers
- Generating / validating a JWS
- Creation of RSA key pairs and X.509 self-signed certificates
- Adding a proof to an existing VC

### Verifiable credential model

The model library regroups the java objects representing the Gaia-X core concepts, with their JSON-LD serialization configuration. 
It is based on the core library, and is the main entry point supposing there is no need for custom configuration.

## Usage

### Maven

The library is built using Java 17, which is the latest Java LTS version available.

Build:
```shell
mvn install --file trust-framework/pom.xml
```

Dependency:
```xml
<dependency>
    <groupId>com.dawex.sigourney</groupId>
    <artifactId>sigourney-verifiable-credentials-model</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Configuration

#### Configure the `FormatProvider` for serializing identifiers

The format of identifiers is configurable through a format provider, that maps a format name to a format String following
the [Formatter syntax](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Formatter.html#syntax).
The arguments given to the Formatter is the field value. If no format is defined for a particular format name, then the raw value is
returned.

Here is an example of how to configure the `DefaultFormatProvider`:

```java
final DefaultFormatProvider formatProvider = new DefaultFormatProvider();
formatProvider.setFormat(Format.ORGANISATION_VERIFIABLE_CREDENTIAL, "./organisations/%s/verifiableCredential");
```

The following format names are available:

| Format name | Applies to | Field | Description |
| --- |--------------|-----------------------------------------|-------------------------------|
| DATA_PRODUCT_AGGREGATION_OF | Data product | `AggregationOf.id` | DataSet id |
| DATA_PRODUCT_CREDENTIAL_SUBJECT | Data product | `DataProductCredentialSubject.id` | credential subject id |
| DATA_PRODUCT_ISSUER | Data product | `DataProductVerifiableCredential.issuer`  | verifiable credential issuer |
| DATA_PRODUCT_PROVIDED_BY | Data product | `DataProductCredentialSubject.providedBy` | credential subject providedBy |
| DATA_PRODUCT_TERMS_AND_CONDITIONS_URI | Data product | `DataProductCredentialSubject.termsAndConditions.url` | credential subject termsAndConditions URL |
| DATA_PRODUCT_VERIFIABLE_CREDENTIAL | Data product | `DataProductVerifiableCredential.id` | verifiable credential id |
| DATA_RESOURCE_CREDENTIAL_SUBJECT | Data resource | `DataResourceCredentialSubject.id` | credential subject id |
| DATA_RESOURCE_COPYRIGHT_OWNED_BY | Data resource | `DataResourceCredentialSubject.copyrightOwnedBy` | credential subject copyright owner |
| DATA_RESOURCE_EXPOSED_THROUGH | Data resource | `DataResourceCredentialSubject.exposedThrough.id` | credential subject exposed through |
| DATA_RESOURCE_ISSUER | Data resource | `DataResourceVerifiableCredential.issuer` | verifiable credential issuer |
| DATA_RESOURCE_PRODUCED_BY | Data resource | `DataResourceCredentialSubject.producedBy` | credential subject produced by |
| DATA_RESOURCE_VERIFIABLE_CREDENTIAL | Data resource | `DataResourceVerifiableCredential.id` | verifiable credential id |
| ORGANISATION_CREDENTIAL_SUBJECT | Organisation | `OrganisationCredentialSubject.id` | credential subject id |
| ORGANISATION_ISSUER | Organisation | `OrganisationVerifiableCredential.issuer` | verifiable credential issuer |
| ORGANISATION_VERIFIABLE_CREDENTIAL | Organisation | `OrganisationVerifiableCredential.id` | verifiable credential id |
| TERMS_AND_CONDITIONS_CREDENTIAL_SUBJECT | Terms and conditions | `TermsAndConditionsCredentialSubject.id` | credential subject id |
| TERMS_AND_CONDITIONS_ISSUER | Terms and conditions | `TermsAndConditionsVerifiableCredential.issuer` | verifiable credential issuer |
| TERMS_AND_CONDITIONS_VERIFIABLE_CREDENTIAL | Terms and conditions | `TermsAndConditionsVerifiableCredential.id` | verifiable credential id |

#### Configure the `ObjectMapper` for serializing organisation verifiable credentials

Jackson modules are preconfigured to handle the JSON-LD serialization (for organisations, data products, data resources, ...).
They require a `FormatProvider` (configured in the previous section), and the base IRI that will be used as the base context of the JSON-LD document. 

Here is an example of how to configure the `ObjectMapper` for generating both organisation and data product verifiable credentials:
```java
final var objectMapper = new ObjectMapper();
// for serializing organisation verifiable credentials
objectMapper.registerModule(JacksonModuleFactory.organisationSerializationModule(formatProvider, () -> "https://mycompany.com"));
// for serializing data product verifiable credentials
objectMapper.registerModule(JacksonModuleFactory.dataProductSerializationModule(formatProvider, () -> "https://mycompany.com"));
```

#### Configure the JSON Web Key (JWK) 

If necessary, the library also provide a way to generate : 
- a JWK containing a key pair (private and public RSA keys)
- a self-signed X.509 certificate

```java
// Generate a key pair and a X.509 self-signed certificate (with common name "My Company", and a 12 months validity) 
final JwkSetUtils.CreatedKeys createdKeys = JwkSetUtils.createKeysWithSelfSignedCertificate("https://mycompany.com", "My Company", 12);
// Get the JWK
final JWK jwk = createdKeys.jwkSet().getKeys().stream().findFirst().orElseThrow();
// Get the X.509 certificate
final String certificate = createdKeys.certificates().stream().findFirst().orElseThrow();
final X509Certificate x509Certificate = com.nimbusds.jose.util.X509CertUtils.parse(certificate);
```

There are also utilities for : 
- loading an existing private key and generating a self-signed X.509 certificate, with `JwkSetUtils.importKeysWithSelfSignedCertificate`
- loading existing private key and X.509 certificate, with `JwkSetUtils.importKeysAndCertificate`


### Generate a verifiable credential

With the configuration defined in the previous section, we can now serialize a verifiable credential POJO in JSON-LD, and add a proof 
 JWK (using the private key):

```java
// Build the verifiableCredential POJO
final var verifiableCredential = OrganisationVerifiableCredential.builder()
        .id("6f77c1344-8b25-4a9a-b0ae-ebe9bd2af61d")
        .issuer("4f21b0c9-63f2-4c5d-88db-239e2389687a")
        .issuanceDate(LocalDate.of(2023, Month.OCTOBER, 16).atTime(13, 59, 1).atZone(ZoneOffset.UTC))
        .organisationCredentialSubject(OrganisationCredentialSubject.builder()
                .id("640cf1a8-f43d-4b2b-822e-8770585e2182")
                .name("My Company")
                .registrationNumber(OrganisationLegalRegistrationNumber.builder()
                        .id("1234567890")
                        .build())
                // ...
                .build())
        .build();

// Generate the proof
final var proof = ProofGenerator.generateProof(
		objectMapper.writeValueAsString(verifiableCredential),
		"https://mycompany.com/jwks",
		jwk);
// Get a signed VC, by adding the proof to the verifiableCredential POJO
final var signedVc = new SignedObject<>(verifiableCredential, proof);
// Serialize the signed VC to get it as a JSON-LD String
final var serializedVc = objectMapper.writeValueAsString(signedVc);
```

### Generate a verifiable presentation

Calling Gaia-X compliance service requires a verifiable presentation, which aggregates verifiable credentials. A verifiable presentation for an organisation contains : 
- the verifiable credential of the organisation, obtained through the previous step
- the verifiable of the registration number, returned by the Gaia-X registryNumber notarization API

#### Requesting the Gaia-X registryNumber notarization API

The library provides a service to make requests to Gaia-X registryNumber notarization API easier :
```java
final NotaryService notaryService = new NotaryServiceFactory().create();
final NotaryService.RegistrationNumberVC registrationNumberVC = notaryService.getRegistrationNumberVC("FR9999999999X", RegistrationNumberType.VAT_ID, "https://mycompany.com/legalRegistrationNumberVC.json");
```

The response contains the registration number verifiable credential, signed by the notarization API.


#### Building the verifiable presentation

From the `registrationNumberVC` retrieved at the previous step : 
- Add a link between the organisation credential subject and the registration number with a `OrganisationLegalRegistrationNumber`
- Add the registration number verificable credential to the verifiable presentation, alongside the organisation signed verificable credential

```java
// Build the verifiableCredential POJO of the organisation
final var verifiableCredential = OrganisationVerifiableCredential.builder()
        .id("6f77c1344-8b25-4a9a-b0ae-ebe9bd2af61d")
        .issuer("4f21b0c9-63f2-4c5d-88db-239e2389687a")
        .issuanceDate(LocalDate.of(2023, Month.OCTOBER, 16).atTime(13, 59, 1).atZone(ZoneOffset.UTC))
        .organisationCredentialSubject(OrganisationCredentialSubject.builder()
                .id("640cf1a8-f43d-4b2b-822e-8770585e2182")
                .name("My Company")
                .registrationNumber(OrganisationLegalRegistrationNumber.builder()
                        .id(registrationNumberVC.vcId())
                        .build())
                // ...
                .build())
        .build();
// Generate the proof
final var proof = ProofGenerator.generateProof(
		objectMapper.writeValueAsString(verifiableCredential),
		"https://mycompany.com/jwks",
		jwk);
// Get a signed VC, by adding the proof to the verifiableCredential POJO
final var signedVc = new SignedObject<>(verifiableCredential, proof);

// Build the verifiable presentation of the organisation
final var organisationVp = VerifiablePresentation.builder()
                .verifiableCredential(List.of(
                        signedVc, 
                        registrationNumberVC.content()))
                .build();
// Serialize the VP to get it as a JSON-LD String
final var serializedVp = objectMapper.writeValueAsString(organisationVp);
```


## Contributing

You may contribute to this test suite by submitting pull requests here:

https://github.com/dawex/sigourney

## License

[Apache License, Version 2.0](LICENSE)
