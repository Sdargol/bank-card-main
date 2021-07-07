package org.sdargol.http.security.annotation;

import org.sdargol.http.security.Roles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Security {
    Roles[] hasRoles() default {Roles.USER};
}
