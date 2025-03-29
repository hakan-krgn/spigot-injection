package com.hakan.spinjection.config.module;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.annotations.ExecutorOrder;
import com.hakan.spinjection.config.annotations.ConfigMapper;
import com.hakan.spinjection.config.executor.ConfigMapperExecutor;
import com.hakan.spinjection.executor.SpigotExecutor;
import com.hakan.spinjection.module.SpigotModule;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * ConfigModule is a module class
 * used to load configuration classes
 * which are annotated with {@link ConfigMapper}.
 */
@ExecutorOrder(1)
@SuppressWarnings({"rawtypes"})
public class ConfigMapperModule extends SpigotModule<Class, ConfigMapper> {

    /**
     * Constructor of ConfigModule.
     *
     * @param bootstrap bootstrap
     */
    public ConfigMapperModule(@Nonnull SpigotBootstrap bootstrap) {
        super(bootstrap, Class.class, ConfigMapper.class);
    }

    /**
     * Loads classes which are annotated with {@link ConfigMapper}.
     * And creates {@link ConfigMapperExecutor} for each class to
     * handle configuration processes.
     *
     * @param classes classes that are annotated with {@link ConfigMapper}.
     */
    @Override
    public void load(@Nonnull Set<Class> classes) {
        for (Class clazz : classes) {
            ConfigMapperExecutor executor = new ConfigMapperExecutor(clazz);
            super.bind(clazz).withInstance(executor.getInstance());

            super.executors.add(executor);
        }
    }

    /**
     * Executes all configuration executors that are
     * saved in {@link #executors}.
     * <p>
     * Then it runs execute method of each executor.
     */
    @Override
    public void execute() {
        for (SpigotExecutor executor : super.executors) {
            ConfigMapperExecutor configMapperExecutor = (ConfigMapperExecutor) executor;
            configMapperExecutor.execute(super.bootstrap, configMapperExecutor.getInstance());
        }
    }
}
