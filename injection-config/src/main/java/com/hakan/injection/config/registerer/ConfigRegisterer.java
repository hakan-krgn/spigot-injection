package com.hakan.injection.config.registerer;

import com.google.inject.Injector;
import com.hakan.injection.registerer.SpigotRegisterer;
import com.hakan.injection.config.annotations.ConfigFile;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings({"rawtypes"})
public class ConfigRegisterer extends SpigotRegisterer<Class, ConfigFile> {

    public ConfigRegisterer(@Nonnull Plugin plugin,
                            @Nonnull Injector injector,
                            @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Class.class, ConfigFile.class);
    }


    @Override
    public void onRegister(@Nullable Object instance,
                           @Nonnull Class target,
                           @Nonnull ConfigFile annotation) {

    }
}
