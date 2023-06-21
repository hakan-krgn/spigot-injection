package com.hakan.injection.database.module;

import com.google.inject.Injector;
import com.hakan.injection.database.annotations.Query;
import com.hakan.injection.database.annotations.Repository;
import com.hakan.injection.database.executor.DatabaseExecutor;
import com.hakan.injection.executor.SpigotExecutor;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * DatabaseRegisterer registers database
 * executors to listen interfaces and execute
 * database queries.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class DatabaseModule extends SpigotModule<Class, Repository> {

    /**
     * Constructor of DatabaseRegisterer.
     *
     * @param plugin      plugin
     * @param reflections reflections
     */
    public DatabaseModule(@Nonnull Plugin plugin,
                          @Nonnull Reflections reflections) {
        super(plugin, reflections, true, Class.class, Repository.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(@Nonnull Set<Class> classes) {
        for (Class clazz : classes) {
            if (!clazz.isInterface())
                throw new RuntimeException("repository class must be interface!");

            super.executors.add(new DatabaseExecutor(clazz));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@Nonnull Injector injector) {
        for (SpigotExecutor executor : super.executors) {
            DatabaseExecutor databaseExecutor = (DatabaseExecutor) executor;

            Class clazz = executor.getDeclaringClass();
            Class<?>[] interfaces = new Class[]{clazz};
            ClassLoader classLoader = clazz.getClassLoader();

            executor.execute(Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
                if (method.getName().equals("toString"))
                    return clazz.getSimpleName();
                if (!method.isAnnotationPresent(Query.class))
                    throw new RuntimeException("method is not registered!");

                return databaseExecutor.onMethodExecute(method, method.getAnnotation(Query.class));
            }));


            super.bind(clazz).toInstance(executor.getInstance());
        }
    }
}
