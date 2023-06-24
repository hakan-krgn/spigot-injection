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
    private FileConfiguration configuration;

    /**
     * {@inheritDoc}
     */
    public YamlContainer(@Nonnull Object instance,
                         @Nonnull ConfigFile annotation) {
        super(instance, annotation);
        this.file = new File(annotation.path());
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public @Nullable <T> T get(@Nonnull String path) {
        return (T) this.configuration.get(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable <T> T get(@Nonnull String path,
                               @Nonnull Class<T> clazz) {
        return clazz.cast(this.configuration.get(path));
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public @Nonnull Container set(@Nonnull String path,
                                  @Nonnull Object value) {
        return this.set(path, value, true);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public @Nonnull Container set(@Nonnull String path,
                                  @Nonnull Object value,
                                  boolean save) {
        this.configuration.set(path, value);
        if (save) this.save();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SneakyThrows
    public @Nonnull Container save() {
        this.configuration.save(this.file);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SneakyThrows
    public @Nonnull Container reload() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
        return this;
    }
}