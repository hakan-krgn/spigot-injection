package com.hakan.spinjection.config.configuration;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hakan.spinjection.config.annotations.ConfigMapper;
import com.hakan.spinjection.config.container.Container;
import com.hakan.spinjection.config.container.ContainerFactory;
import com.hakan.spinjection.config.container.ContainerType;
import com.hakan.spinjection.config.utils.FileUtils;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

/**
 * MapperConfiguration class to
 * define base configuration methods
 * like reload
 */
public abstract class MapperConfiguration {

    private final Container container;
    private final ObjectMapper objectMapper;
    private final ConfigMapper configMapper;

    public MapperConfiguration() {
        this.configMapper = this.getClass().getAnnotation(ConfigMapper.class);
        this.objectMapper = (this.configMapper.type() == ContainerType.YAML) ?
                new ObjectMapper(new YAMLFactory()) :
                new ObjectMapper(new JsonFactory());
        this.container = ContainerFactory.of(this.configMapper);
    }

    /**
     * Gets value from the config file.
     *
     * @param key key of the value.
     * @param <T> type of the value.
     * @return value.
     */
    @Nullable
    public final <T> T get(@Nonnull String key) {
        return this.container.get(key);
    }

    /**
     * Gets value from the config file.
     *
     * @param key   key of the value.
     * @param clazz type of the value.
     * @param <T>   type of the value.
     * @return value.
     */
    @Nullable
    public final <T> T get(@Nonnull String key, @Nonnull Class<T> clazz) {
        return this.container.get(key, clazz);
    }

    /**
     * Sets value to the config file.
     *
     * @param key   key of the value.
     * @param value value.
     */
    public final void set(@Nonnull String key, @Nonnull Object value) {
        this.container.set(key, value);
    }

    /**
     * Sets value to the config file.
     *
     * @param key   key of the value.
     * @param value value.
     * @param save  if true, it will save the config file.
     */
    public final void set(@Nonnull String key, @Nonnull Object value, boolean save) {
        this.container.set(key, value, save);
    }

    /**
     * Saves the mapped configuration
     * objects fields from the specified
     * path using ConfigMapper annotation
     */
    public final void save() {
        this.container.save();
    }

    /**
     * Reloads the mapped configuration
     * objects fields from the specified
     * path using ConfigMapper annotation
     */
    @SneakyThrows
    public final void reload() {
        this.container.reload();

        File file = FileUtils.createFile(
                this.configMapper.path(),
                this.configMapper.resource()
        );

        ObjectReader reader = this.objectMapper.readerForUpdating(this);
        reader.readValue(file);
    }
}
