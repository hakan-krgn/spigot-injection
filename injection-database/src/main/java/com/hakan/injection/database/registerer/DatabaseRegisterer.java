package com.hakan.injection.database.registerer;

import com.google.inject.Injector;
import com.hakan.injection.database.annotations.Repository;
import com.hakan.injection.database.executor.DatabaseExecutor;
import com.hakan.injection.registerer.SpigotRegisterer;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings({"rawtypes"})
public class DatabaseRegisterer extends SpigotRegisterer<Class, Repository> {

    public DatabaseRegisterer(@Nonnull Plugin plugin,
                              @Nonnull Injector injector,
                              @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Class.class, Repository.class);
    }

    @Override
    public void onRegister(@Nullable Object instance,
                           @Nonnull Class target,
                           @Nonnull Repository annotation) {
        new DatabaseExecutor(target).execute();
    }
}
