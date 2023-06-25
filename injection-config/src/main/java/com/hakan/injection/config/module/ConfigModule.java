package com.hakan.injection.config.module;

import com.google.inject.Injector;
import com.hakan.injection.config.annotations.ConfigFile;
import com.hakan.injection.config.executor.ConfigExecutor;
import com.hakan.injection.executor.SpigotExecutor;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * ConfigModule is a module class
 * that is used to load configuration classes
 * which are annotated with {@link ConfigFile}.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ConfigModule extends SpigotModule<Class, ConfigFile> {

    /**
     * Constructor of ConfigModule.
     *
     * @param plugin      plugin
     * @param reflections reflections
     */
    public ConfigModule(@Nonnull Plugin plugin,
                        @Nonnull Reflections reflections) {
        super(plugin, reflections, Class.class, ConfigFile.class);
    }

    /**
     * Loads classes which are annotated with {@link ConfigFile}.
     * And creates {@link ConfigExecutor} for each class to
     * handle configuration processes.
     *
     * @param classes classes that are annotated with {@link ConfigFile}.
     */
    @Override
    public void load(@Nonnull Set<Class> classes) {
        for (Class clazz : classes) {
            if (!clazz.isInterface())
                throw new RuntimeException("configuration class must be interface!");


            ConfigExecutor configExecutor = new ConfigExecutor(clazz);

            super.executors.add(configExecutor);
            super.bind(clazz).toInstance(configExecutor.getInstance());
        }
    }

    /**
     * Executes all configuration executors which are
     * saved in {@link #executors}.
     * <p>
     * Then it runs execute method of each executor.
     *
     * @param injector injector
     */
    @Override
    public void execute(@Nonnull Injector injector) {
        for (SpigotExecutor executor : super.executors) {
            executor.execute(injector.getInstance(executor.getDeclaringClass()), injector);
        }
    }
}
