package com.hakan.spinjection.config.container.impl;

import com.google.gson.JsonObject;
import com.hakan.spinjection.config.container.Container;
import com.hakan.spinjection.config.utils.JsonUtils;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * {@inheritDoc}
 */
@SuppressWarnings({"unchecked"})
public class JsonContainer extends Container {

    private JsonObject jsonObject;

    /**
     * {@inheritDoc}
     */
    public JsonContainer(@Nonnull String path) {
        super(path);
        this.jsonObject = JsonUtils.loadFromFile(super.path);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable <T> T get(@Nonnull String key) {
        return (T) JsonUtils.getValue(this.jsonObject, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable <T> T get(@Nonnull String key,
                               @Nonnull Class<T> clazz) {
        return clazz.cast(JsonUtils.getValue(this.jsonObject, key));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Container set(@Nonnull String key,
                                  @Nonnull Object value) {
        return this.set(key, value, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Container set(@Nonnull String key,
                                  @Nonnull Object value,
                                  boolean save) {
        JsonUtils.setValue(this.jsonObject, key, value);
        if (save) this.save();
        return this;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized @Nonnull Container save() {
        JsonUtils.saveToFile(this.jsonObject, super.path);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SneakyThrows
    public synchronized @Nonnull Container reload() {
        this.jsonObject = JsonUtils.loadFromFile(super.path);
        return this;
    }
}
