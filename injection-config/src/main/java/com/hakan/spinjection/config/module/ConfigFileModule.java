package com.hakan.spinjection.config.module;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.annotations.ExecutorOrder;
import com.hakan.spinjection.config.annotations.ConfigFile;
import com.hakan.spinjection.config.executor.ConfigFileExecutor;
import com.hakan.spinjection.executor.SpigotExecutor;
import com.hakan.spinjection.module.SpigotModule;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * ConfigModule is a module class
 * that is used to load configuration classes
 * which are annotated with {@link ConfigFile}.
 */
@ExecutorOrder(1)
@SuppressWarnings({"rawtypes"})
public class ConfigFileModule extends SpigotModule<Class, ConfigFile> {

    /**
     * Constructor of ConfigModule.
     *
     * @param bootstrap bootstrap
     */
    public ConfigFileModule(@Nonnull SpigotBootstrap bootstrap) {
        super(bootstrap, Class.class, ConfigFile.class);
    }

    /**
     * Loads classes which are annotated with {@link ConfigFile}.
     * And creates {@link ConfigFileExecutor} for each class to
     * handle configuration processes.
     *
     * @param classes classes that are annotated with {@link ConfigFile}.
     */
    @Override
    public void load(@Nonnull Set<Class> classes) {
        for (Class clazz : classes) {
            if (!clazz.isInterface())
                throw new RuntimeException("configuration class must be interface!");


            ConfigFileExecutor configFileExecutor = new ConfigFileExecutor(clazz);

            super.bind(clazz).withInstance(configFileExecutor.getInstance());
            super.executors.add(configFileExecutor);
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
            ConfigFileExecutor configFileExecutor = (ConfigFileExecutor) executor;
            configFileExecutor.execute(super.bootstrap, configFileExecutor.getInstance());
        }
    }
}
