package com.hakan.spinjection.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Scanner annotation to scan
 * classes in the specified package.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scanner {

    /**
     * Package path to scan.
     *
     * @return Package path to scan.
     */
    @Nonnull
    String value() default "";
}
