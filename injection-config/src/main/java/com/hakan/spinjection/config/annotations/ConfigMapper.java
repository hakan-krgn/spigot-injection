package com.hakan.spinjection.config.annotations;

import com.hakan.spinjection.config.container.ContainerType;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ConfigMapper annotation can be used
 * to define a model as a configuration
 * equivalent in the code.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigMapper {

    /**
     * Path of the config file.
     *
     * @return Path of the config file.
     */
    @Nonnull
    String path();

    /**
     * Resource to include
     * the config file.
     *
     * @return Resource to include
     * the config file
     */
    @Nonnull
    String resource() default "";

    /**
     * Config type of the file.
     *
     * @return config type of the file
     */
    @Nonnull
    ContainerType type() default ContainerType.YAML;
}
