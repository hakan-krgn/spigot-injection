package com.hakan.injection.configuration.module;

import com.google.inject.Injector;
import com.hakan.injection.configuration.annotations.Configuration;
import com.hakan.injection.module.impl.ClassModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;

public class ConfigurationModule extends ClassModule<Configuration> {

    public ConfigurationModule(@Nonnull Plugin plugin,
                               @Nonnull Injector injector,
                               @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Configuration.class);
    }

    @Override
    public void onRegister(@Nonnull Class<?> clazz,
                           @Nonnull Object instance,
                           @Nonnull Configuration annotation) {

    }
}
