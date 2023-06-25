package com.hakan.injection.config.annotations;

import com.google.inject.Singleton;
import com.hakan.injection.config.container.ContainerType;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ConfigFile annotation to
 * define config file settings.
 */
@Singleton
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigFile {

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


    /**
     * Save timer settings
     * for the config file.
     *
     * @return save timer settings
     */
    SaveTimer saveTimer() default @SaveTimer;

    /**
     * Reload timer settings
     * for the config file.
     *
     * @return reload timer settings
     */
    ReloadTimer reloadTimer() default @ReloadTimer;
}