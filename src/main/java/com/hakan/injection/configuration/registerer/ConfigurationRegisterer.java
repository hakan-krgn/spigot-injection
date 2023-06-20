package com.hakan.injection.configuration.registerer;

import com.google.inject.Injector;
import com.hakan.injection.configuration.annotations.ConfigFile;
import com.hakan.injection.SpigotRegisterer;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;

@SuppressWarnings({"rawtypes"})
public class ConfigurationRegisterer extends SpigotRegisterer<Class, ConfigFile> {

    public ConfigurationRegisterer(@Nonnull Plugin plugin,
                                   @Nonnull Injector injector,
                                   @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Class.class, ConfigFile.class);
    }


    @Override
    public void onRegister(@Nonnull Object instance,
                           @Nonnull Class target,
                           @Nonnull ConfigFile annotation) {

    }
}
