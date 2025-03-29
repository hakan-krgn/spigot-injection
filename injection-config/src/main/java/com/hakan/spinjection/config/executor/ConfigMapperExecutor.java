package com.hakan.spinjection.config.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.config.annotations.ConfigMapper;
import com.hakan.spinjection.config.container.ContainerType;
import com.hakan.spinjection.config.utils.FileUtils;
import com.hakan.spinjection.executor.SpigotExecutor;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * ConfigExecutor is an executor class
 * used to execute ConfigValue methods.
 */
public class ConfigMapperExecutor implements SpigotExecutor {

    private Object instance;
    private final Class<?> clazz;
    private final ConfigMapper annotation;
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    /**
     * Constructor of {@link ConfigMapperExecutor}.
     *
     * @param clazz class
     */
    @SneakyThrows
    public ConfigMapperExecutor(@Nonnull Class<?> clazz) {
        this.clazz = clazz;
        this.annotation = clazz.getAnnotation(ConfigMapper.class);
        this.load();
    }

    /**
     * Gets the instance of the class
     * that is annotated with {@link ConfigMapper}.
     *
     * @return instance
     */
    @Override
    public @Nonnull Object getInstance() {
        return this.instance;
    }

    /**
     * Gets the class of the instance
     * that is annotated with {@link ConfigMapper}.
     *
     * @return class
     */
    @Override
    public @Nonnull Class<?> getDeclaringClass() {
        return this.clazz;
    }



    /**
     * Creates a config file if not exists
     * and creates the container from the config file.
     * <p>
     * Then it starts the config reload and save scheduler.
     *
     * @param bootstrap injector
     * @param instance  instance
     */
    @Override
    public void execute(@Nonnull SpigotBootstrap bootstrap,
                        @Nonnull Object instance) {

    }


    /**
     * Creates the file from the specified resource
     * then generate an instance for mapped model
     */
    @SneakyThrows
    private void load() {
        File file = FileUtils.createFile(
                this.annotation.path(),
                this.annotation.resource()
        );

        if (this.annotation.type() == ContainerType.YAML) {
            this.instance = this.yamlMapper.readValue(file, this.clazz);
        } else if (this.annotation.type() == ContainerType.JSON) {
            this.instance = this.jsonMapper.readValue(file, this.clazz);
        }
    }
}
