package com.hakan.injection.config.container;

import com.hakan.injection.config.annotations.ConfigFile;
import com.hakan.injection.config.container.impl.JsonContainer;
import com.hakan.injection.config.container.impl.YamlContainer;

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
        switch (annotation.type()) {
            case YAML:
                return new YamlContainer(instance, annotation);
            case JSON:
                return new JsonContainer(instance, annotation);
            default:
                throw new IllegalArgumentException("unsupported config file type!");
        }
    }
}
