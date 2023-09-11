package com.hakan.spinjection.config.container;

import com.hakan.spinjection.config.annotations.ConfigValue;
import com.hakan.spinjection.config.utils.ColorUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

/**
 * ConfigContainer is an abstract class
 * that is used to load and save config files.
 */
@SuppressWarnings({"unchecked"})
public abstract class Container {

    protected final String path;
    protected final File file;

    /**
     * Creates a new ConfigContainer.
     *
     * @param path path of the file
     */
    public Container(@Nonnull String path) {
        this.path = path;
        this.file = new File(path);
    }


    /**
     * Gets the path of the config file.
     *
     * @return path of the config file
     */
    public @Nonnull String getPath() {
        return this.path;
    }

    /**
     * Gets the file of the config file.
     *
     * @return file of the config file
     */
    public @Nonnull File getFile() {
        return this.file;
    }


    /**
     * Gets value from config file
     * with the given key.
     *
     * @param key          value key
     * @param defaultValue default value
     * @param <T>          value type
     * @return value
     */
    public @Nullable <T> T get(@Nonnull String key,
                               @Nullable T defaultValue) {
        T value = this.get(key);
        return (value == null) ? defaultValue : value;
    }

    /**
     * Gets value from config file
     * with the given key.
     *
     * @param key          value key
     * @param clazz        value class
     * @param defaultValue default value
     * @param <T>          value type
     * @return value
     */
    public @Nullable <T> T get(@Nonnull String key,
                               @Nullable T defaultValue,
                               @Nonnull Class<T> clazz) {
        T value = this.get(key, clazz);
        return (value == null) ? defaultValue : value;
    }

    /**
     * Gets value from config file with the given key, and if
     * colored is true and value is String, it will be colored.
     *
     * @param annotation ConfigValue annotation
     * @param <T>        value type
     * @return value
     */
    public @Nullable <T> T get(@Nonnull ConfigValue annotation) {
        return this.get(annotation, null);
    }

    /**
     * Gets value from config file with the given key, and if
     * colored is true and value is String, it will be colored.
     *
     * @param annotation   ConfigValue annotation
     * @param defaultValue default value
     * @param <T>          value type
     * @return value
     */
    public @Nullable <T> T get(@Nonnull ConfigValue annotation,
                               @Nullable T defaultValue) {
        T value = this.get(annotation.value());

        if (value == null)
            return defaultValue;
        if (value instanceof String && annotation.colored())
            return (T) ColorUtils.colored(value.toString());

        return value;
    }



    /**
     * Gets value from config file
     * with the given key.
     *
     * @param key value key
     * @param <T> value type
     * @return value
     */
    public abstract @Nullable <T> T get(@Nonnull String key);

    /**
     * Gets value from config file
     * with the given key.
     *
     * @param key   value key
     * @param clazz value class,
     * @param <T>   value type
     * @return value
     */
    public abstract @Nullable <T> T get(@Nonnull String key, @Nonnull Class<T> clazz);


    /**
     * Sets value to config file
     * with the given key and save
     * it to file.
     *
     * @param key   key
     * @param value value
     * @return instance of the class
     */
    public abstract @Nonnull Container set(@Nonnull String key, @Nonnull Object value);

    /**
     * Sets value to config file
     * with the given key.
     *
     * @param key   key
     * @param value value
     * @param save  save config file after
     *              setting the value
     * @return instance of the class
     */
    public abstract @Nonnull Container set(@Nonnull String key, @Nonnull Object value, boolean save);


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
