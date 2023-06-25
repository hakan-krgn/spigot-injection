package com.hakan.injection.config.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ConfigValue annotation to
 * define config value and get
 * it from the config file.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigValue {

    /**
     * Key of the value.
     *
     * @return key of the value
     */
    @Nonnull
    String value();

    /**
     * If the value is text, it will be
     * colored if this setting is true.
     *
     * @return if the value is text, it will be colored
     */
    boolean colored() default true;
}