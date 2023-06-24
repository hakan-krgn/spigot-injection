package com.hakan.injection.database.module;

import com.google.inject.Injector;
import com.hakan.injection.database.annotations.Repository;
import com.hakan.injection.database.executor.DatabaseExecutor;
import com.hakan.injection.database.utils.DatabaseUtils;
import com.hakan.injection.executor.SpigotExecutor;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * DatabaseModule registers database
 * executors to listen interfaces and execute
 * database queries.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class DatabaseModule extends SpigotModule<Class, Repository> {

    /**
     * Constructor of DatabaseModule.
     *
     * @param plugin      plugin
     * @param reflections reflections
     */
    public DatabaseModule(@Nonnull Plugin plugin,
                          @Nonnull Reflections reflections) {
        super(plugin, reflections, Class.class, Repository.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(@Nonnull Set<Class> classes) {
        for (Class clazz : classes) {
            if (!clazz.isInterface())
                throw new RuntimeException("repository class must be interface!");


            DatabaseExecutor databaseExecutor = new DatabaseExecutor(clazz);

            super.executors.add(databaseExecutor);
            super.bind(clazz).toInstance(databaseExecutor.getInstance());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@Nonnull Injector injector) {
        for (SpigotExecutor executor : super.executors) {
            executor.execute(injector.getInstance(executor.getDeclaringClass()), injector);
        }
    }
}
