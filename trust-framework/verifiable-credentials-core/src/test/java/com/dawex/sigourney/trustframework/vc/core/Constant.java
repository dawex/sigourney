package com.dawex.sigourney.trustframework.vc.core;

public class Constant {

	public static final String JSON_LD = """
			{
			    "@context": [
			    {
			      "@base": "https://dwx-13071.platform.dev.corp.dawex.net"
			    },
			      "https://www.w3.org/2018/credentials/v1",
			      "https://w3id.org/security/suites/jws-2020/v1",
			      "https://registry.lab.gaia-x.eu/development/api/trusted-shape-registry/v1/shapes/jsonld/trustframework#"
			  ],
			  "type" : "VerifiableCredential",
			  "@id" : "./api/secure/participant/organisations/62b573deb33e417edcb34-id/verifiableCredential",
			  "issuer" : "./organisations/62b573deb33e417ed-issuer",
			  "issuanceDate" : "2022-07-28T15:16:01Z",
			  "credentialSubject" : {
			    "type": "gx:LegalParticipant",
			    "id" : "./organisations/62b573deb33e417e-company",
			    "gx:name" : "Mercat de la Boqueria",
			    "gx:registrationNumber" : "AB-1234-YZ",
			    "gx:headquarterAddress" : {
			      "gx:street-address" : "La Rambla, 91",
			      "gx:postal-code" : "08001",
			      "gx:region" : "Cataluña",
			      "gx:locality" : "Barcelona",
			      "gx:country-name" : "ESP"
			    },
			    "gx:legalAddress" : {
			      "gx:street-address" : "7 rue Grenette",
			      "gx:postal-code" : "74000",
			      "gx:region" : "Savoie",
			      "gx:locality" : "Annecy",
			      "gx:country-name" : "FRA"
			    }
			  }
			}""";

	public static final String DID_ISSUER = "did:web:dawex.com";

	private Constant() {
		// no instance allowed
	}
}
