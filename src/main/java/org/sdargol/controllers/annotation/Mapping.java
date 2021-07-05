package org.sdargol.controllers.annotation;

import org.sdargol.controllers.method.HTTPMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Mapping {
    String url();
    HTTPMethod httpMethod() default HTTPMethod.GET;
}

