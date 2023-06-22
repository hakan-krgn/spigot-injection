package com.hakan.injection.config.module;

import com.google.inject.Injector;
import com.hakan.injection.config.annotations.ConfigFile;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

@SuppressWarnings({"rawtypes"})
public class ConfigModule extends SpigotModule<Class, ConfigFile> {

    public ConfigModule(@Nonnull Plugin plugin,
                        @Nonnull Reflections reflections) {
        super(plugin, reflections, Class.class, ConfigFile.class);
    }

    @Override
    public void load(@Nonnull Set<Class> list) {

    }

    @Override
    public void execute(@Nonnull Injector injector) {

    }
}
