package com.hakan.injection.config.configuration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface BaseConfiguration {

    @Nullable
    <T> T get(@Nonnull String key);

    @Nullable
    <T> T get(@Nonnull String key,
              @Nonnull Class<T> clazz);

    void set(@Nonnull String key,
             @Nonnull Object value);

    void set(@Nonnull String key,
             @Nonnull Object value,
             boolean save);

    void save();

    void reload();
}
