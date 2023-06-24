package com.hakan.injection.config.annotations;

import com.hakan.injection.config.container.ContainerType;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * ConfigFile annotation.
 */
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
     * the config file.
     */
    @Nonnull
    String resource() default "";

    /**
     * Config type of the file.
     *
     * @return config type of the file.
     */
    @Nonnull
    ContainerType type() default ContainerType.YAML;


    /**
     * Auto-save time of the
     * config file.
     *
     * @return timestamp.
     */
    long saveInterval() default 0L;

    /**
     * Time unit of the auto save.
     * Default is seconds.
     *
     * @return time unit.
     */
    @Nonnull
    TimeUnit saveIntervalUnit() default TimeUnit.SECONDS;

    /**
     * Auto-save status of the
     * config file. If it is true,
     * the config file will take
     * the settings from the file
     * automatically.
     *
     * @return auto reload status.
     */
    boolean autoSave() default true;


    /**
     * Auto-reload time of the
     * config file.
     *
     * @return timestamp.
     */
    long reloadInterval() default 0L;

    /**
     * Time unit of the auto reload.
     * Default is seconds.
     *
     * @return time unit.
     */
    @Nonnull
    TimeUnit reloadIntervalUnit() default TimeUnit.SECONDS;

    /**
     * Auto-reload status of the
     * config file. If it is true,
     * the config file will take
     * the settings from the file
     * automatically.
     *
     * @return auto reload status.
     */
    boolean autoReload() default true;
}