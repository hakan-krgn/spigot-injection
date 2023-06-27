package com.hakan.spinjection.config.configuration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * BaseConfiguration interface to
 * define base configuration methods.
 */
public interface BaseConfiguration {

    /**
     * Gets value from the config file.
     *
     * @param key key of the value.
     * @param <T> type of the value.
     * @return value.
     */
    @Nullable
    <T> T get(@Nonnull String key);

    /**
     * Gets value from the config file.
     *
     * @param key   key of the value.
     * @param clazz type of the value.
     * @param <T>   type of the value.
     * @return value.
     */
    @Nullable
    <T> T get(@Nonnull String key, @Nonnull Class<T> clazz);

    /**
     * Sets value to the config file.
     *
     * @param key   key of the value.
     * @param value value.
     */
    void set(@Nonnull String key, @Nonnull Object value);

    /**
     * Sets value to the config file.
     *
     * @param key   key of the value.
     * @param value value.
     * @param save  if true, it will save the config file.
     */
    void set(@Nonnull String key, @Nonnull Object value, boolean save);

    /**
     * Saves the config file.
     */
    void save();

    /**
     * Reloads the config file.
     */
    void reload();
}
