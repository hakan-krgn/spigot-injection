package com.hakan.spinjection.config.container.impl;

import com.hakan.spinjection.config.container.Container;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * {@inheritDoc}
 */
@SuppressWarnings({"unchecked"})
public class YamlContainer extends Container {

    private final FileConfiguration configuration;

    /**
     * {@inheritDoc}
     */
    public YamlContainer(@Nonnull String path) {
        super(path);
        this.configuration = YamlConfiguration.loadConfiguration(super.file);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable <T> T get(@Nonnull String key) {
        return this.configuration.contains(key) ? (T) this.configuration.get(key) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable <T> T get(@Nonnull String key,
                               @Nonnull Class<T> clazz) {
        return this.configuration.contains(key) ? clazz.cast(this.configuration.get(key)) : null;
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
        this.configuration.set(key, value);
        if (save) this.save();
        return this;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @SneakyThrows
    public synchronized @Nonnull Container save() {
        this.configuration.save(super.file);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SneakyThrows
    public synchronized @Nonnull Container reload() {
        this.configuration.load(super.file);
        return this;
    }
}
