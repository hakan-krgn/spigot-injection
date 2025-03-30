package com.hakan.spinjection.config.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hakan.spinjection.config.annotations.ConfigMapper;
import com.hakan.spinjection.config.container.ContainerType;
import com.hakan.spinjection.config.utils.FileUtils;
import lombok.SneakyThrows;

import java.io.File;

/**
 * MapperConfiguration class to
 * define base configuration methods
 * like reload
 */
public abstract class MapperConfiguration {

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    /**
     * Reloads the mapped configuration
     * objects fields from the specified
     * path using ConfigMapper annotation
     */
    @SneakyThrows
    public final void reload() {
        ConfigMapper annotation = this.getClass().getAnnotation(ConfigMapper.class);

        File file = FileUtils.createFile(
                annotation.path(),
                annotation.resource()
        );

        ObjectReader reader = (annotation.type() == ContainerType.YAML) ?
                this.yamlMapper.readerForUpdating(this) :
                this.jsonMapper.readerForUpdating(this);
        reader.readValue(file);
    }
}
