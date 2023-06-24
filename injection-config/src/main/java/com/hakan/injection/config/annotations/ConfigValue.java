package com.hakan.injection.config.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ConfigValue annotation to
 * define config value and get
 * connected filed.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigValue {

    /**
     * Path of the file.
     *
     * @return Path of the file.
     */
    @Nonnull
    String value();

    /**
     * If the value is text, it will be
     * colored if this setting is true.
     *
     * @return If the value is text, it will be colored.
     */
    boolean colored() default true;
}