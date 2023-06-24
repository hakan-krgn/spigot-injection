package com.hakan.injection.config.container.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hakan.injection.config.annotations.ConfigFile;
import com.hakan.injection.config.container.Container;
import com.hakan.injection.config.utils.JsonUtils;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * {@inheritDoc}
 */
@SuppressWarnings({"unchecked"})
public class JsonContainer extends Container {

    private static final Gson GSON = new Gson();



    private JsonObject jsonObject;

    /**
     * {@inheritDoc}
     */
    public JsonContainer(@Nonnull Object instance,
                         @Nonnull ConfigFile annotation) {
        super(instance, annotation);
        this.jsonObject = JsonUtils.loadFromFile(annotation.path());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable <T> T get(@Nonnull String path) {
        return (T) JsonUtils.getValue(this.jsonObject, path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable <T> T get(@Nonnull String path,
                               @Nonnull Class<T> clazz) {
        return clazz.cast(JsonUtils.getValue(this.jsonObject, path));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Container set(@Nonnull String path,
                                  @Nonnull Object value) {
        return this.set(path, value, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Container set(@Nonnull String path,
                                  @Nonnull Object value,
                                  boolean save) {
        JsonUtils.setValue(this.jsonObject, path, GSON.toJsonTree(value));
        if (save) this.save();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Container save() {
        JsonUtils.saveToFile(this.jsonObject, super.annotation.path());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SneakyThrows
    public @Nonnull Container reload() {
        this.jsonObject = JsonUtils.loadFromFile(super.annotation.path());
        return this;
    }
}