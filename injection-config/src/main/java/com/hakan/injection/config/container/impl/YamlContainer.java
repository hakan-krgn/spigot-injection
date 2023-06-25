package com.hakan.injection.config.container.impl;

import com.hakan.injection.config.annotations.ConfigFile;
import com.hakan.injection.config.container.Container;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

/**
 * {@inheritDoc}
 */
@SuppressWarnings({"unchecked"})
public class YamlContainer extends Container {

    private final File file;
    private final FileConfiguration configuration;

    /**
     * {@inheritDoc}
     */
    public YamlContainer(@Nonnull Object instance,
                         @Nonnull ConfigFile annotation) {
        super(instance, annotation);
        this.file = new File(super.path);
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable <T> T get(@Nonnull String key) {
        return (T) this.configuration.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable <T> T get(@Nonnull String key, @Nonnull Class<T> clazz) {
        return clazz.cast(this.configuration.get(key));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Container set(@Nonnull String key, @Nonnull Object value) {
        return this.set(key, value, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Container set(@Nonnull String key, @Nonnull Object value, boolean save) {
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
        this.configuration.save(this.file);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SneakyThrows
    public synchronized @Nonnull Container reload() {
        this.configuration.load(this.file);
        return this;
    }
}