package com.hakan.injection.config.module;

import com.google.inject.Injector;
import com.hakan.injection.config.annotations.Configuration;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.util.Set;

@SuppressWarnings({"rawtypes"})
public class ConfigModule extends SpigotModule<Class, Configuration> {

    /**
     * Constructor of ConfigModule.
     *
     * @param plugin      plugin
     * @param reflections reflections
     */
    public ConfigModule(@Nonnull Plugin plugin,
                        @Nonnull Reflections reflections) {
        super(plugin, reflections, Class.class, Configuration.class);
    }


    @Override
    public void load(@Nonnull Set<Class> list) {

    }

    @Override
    public void execute(@Nonnull Injector injector) {

    }
}
