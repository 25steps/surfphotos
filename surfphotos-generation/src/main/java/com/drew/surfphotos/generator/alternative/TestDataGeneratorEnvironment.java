package com.drew.surfphotos.generator.alternative;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Alternative
@Stereotype
@Retention(RUNTIME)
@Target({TYPE})
public @interface TestDataGeneratorEnvironment {
}
