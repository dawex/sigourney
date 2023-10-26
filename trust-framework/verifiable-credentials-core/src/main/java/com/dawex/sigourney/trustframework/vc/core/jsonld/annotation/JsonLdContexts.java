package com.dawex.sigourney.trustframework.vc.core.jsonld.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonLdContexts {
	boolean addBaseContext() default false;

	JsonLdEmbeddedContext[] embeddedContexts() default {};

	String[] referencedContexts() default {};
}