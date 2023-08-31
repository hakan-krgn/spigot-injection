package com.hakan.spinjection.config.container;

import com.hakan.spinjection.config.annotations.ConfigFile;
import com.hakan.spinjection.config.container.impl.JsonContainer;
import com.hakan.spinjection.config.container.impl.YamlContainer;
import com.hakan.spinjection.config.utils.FileUtils;

import javax.annotation.Nonnull;

/**
 * ContainerFactory is a factory class
 * that is used to create ConfigContainer.
 */
public class ContainerFactory {

    /**
     * Create a new ConfigContainer.
     *
     * @param annotation configFile annotation
     * @return new instance of ConfigContainer
     */
    public static @Nonnull Container of(@Nonnull ConfigFile annotation) {
        return of(annotation.path(), annotation.resource(), annotation.type());
    }

    /**
     * Create a new ConfigContainer.
     *
     * @param path path of the file
     * @param type config file type
     * @return new instance of ConfigContainer
     */
    public static @Nonnull Container of(@Nonnull String path,
                                        @Nonnull ContainerType type) {
        return of(path, "", type);
    }

    /**
     * Create a new ConfigContainer.
     *
     * @param path path of the file
     * @param type config file type
     * @return new instance of ConfigContainer
     */
    public static @Nonnull Container of(@Nonnull String path,
                                        @Nonnull String resource,
                                        @Nonnull ContainerType type) {
        FileUtils.createFile(path, resource);

        if (type == ContainerType.YAML) return new YamlContainer(path);
        if (type == ContainerType.JSON) return new JsonContainer(path);

        throw new IllegalArgumentException("unsupported config file type!");
    }
}
