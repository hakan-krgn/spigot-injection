package com.hakan.spinjection.config.container;

import com.hakan.spinjection.config.annotations.ConfigFile;
import com.hakan.spinjection.config.container.impl.JsonContainer;
import com.hakan.spinjection.config.container.impl.YamlContainer;

import javax.annotation.Nonnull;

/**
 * ContainerFactory is a factory class
 * that is used to create ConfigContainer.
 */
public class ContainerFactory {

    /**
     * Create a new ConfigContainer.
     *
     * @param instance   instance of the class
     * @param annotation configFile annotation
     * @return new instance of ConfigContainer
     */
    public static @Nonnull Container of(@Nonnull Object instance,
                                        @Nonnull ConfigFile annotation) {
        if (annotation.type() == ContainerType.YAML)
            return new YamlContainer(instance, annotation);
        if (annotation.type() == ContainerType.JSON)
            return new JsonContainer(instance, annotation);
        throw new IllegalArgumentException("unsupported config file type!");
    }
}
