package com.hakan.injection.config.container;

import com.hakan.injection.config.annotations.ConfigFile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * ConfigContainer is an abstract class
 * that is used to load and save config files.
 */
public abstract class Container {

    protected final Object instance;
    protected final ConfigFile annotation;

    /**
     * Creates a new ConfigContainer.
     *
     * @param instance   instance of the class
     * @param annotation ConfigFile annotation
     */
    public Container(@Nonnull Object instance,
                     @Nonnull ConfigFile annotation) {
        this.instance = instance;
        this.annotation = annotation;
    }


    /**
     * Gets instance of the class.
     *
     * @return instance of the class
     */
    public @Nonnull Object getInstance() {
        return this.instance;
    }

    /**
     * Gets ConfigFile annotation.
     *
     * @return ConfigFile annotation
     */
    public @Nonnull ConfigFile getAnnotation() {
        return this.annotation;
    }

    /**
     * Gets the path of the config file.
     *
     * @return path of the config file
     */
    public @Nonnull String getPath() {
        return this.annotation.path();
    }

    /**
     * Gets resource of the config file.
     *
     * @return resource of the config file
     */
    public @Nonnull String getResource() {
        return this.annotation.resource();
    }


    /**
     * Gets value from config file
     * with the given path.
     *
     * @param path value path
     * @param <T>  value type
     * @return value
     */
    public abstract @Nullable <T> T get(@Nonnull String path);

    /**
     * Gets value from config file
     * with the given path.
     *
     * @param path  value path
     * @param clazz value class
     * @param <T>   value type
     * @return value
     */
    public abstract @Nullable <T> T get(@Nonnull String path,
                                        @Nonnull Class<T> clazz);

    /**
     * Sets value to config file
     * with the given path and save
     * it to file.
     *
     * @param path  value path
     * @param value value
     * @return instance of the class
     */
    public abstract @Nonnull Container set(@Nonnull String path,
                                           @Nonnull Object value);

    /**
     * Sets value to config file
     * with the given path.
     *
     * @param path  value path
     * @param value value
     * @param save  save config file after
     *              setting the value
     * @return instance of the class
     */
    public abstract @Nonnull Container set(@Nonnull String path,
                                           @Nonnull Object value,
                                           boolean save);

    /**
     * Saves last data to
     * config file as persist.
     *
     * @return instance of the class
     */
    public abstract @Nonnull Container save();

    /**
     * Gets all nodes from config
     * and sets it to the all fields
     * of this class.
     *
     * @return instance of the class
     */
    public abstract @Nonnull Container reload();
}