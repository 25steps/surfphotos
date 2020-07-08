package com.drew.surfphotos.ejb.model;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Target({FIELD, TYPE, METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface ProfileUidGenerator {
    public Category category();

    public static enum Category {

        PRIMARY,

        SECONDARY;
    }
}
