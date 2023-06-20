package com.hakan.injection.database.registerer;

import com.google.inject.Injector;
import com.hakan.injection.SpigotRegisterer;
import com.hakan.injection.database.annotations.Query;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public class DatabaseRegisterer extends SpigotRegisterer<Method, Query> {

    public DatabaseRegisterer(@Nonnull Plugin plugin,
                              @Nonnull Injector injector,
                              @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Method.class, Query.class);
    }

    @Override
    public void onRegister(@Nonnull Object instance,
                           @Nonnull Method target,
                           @Nonnull Query annotation) {

    }
}
