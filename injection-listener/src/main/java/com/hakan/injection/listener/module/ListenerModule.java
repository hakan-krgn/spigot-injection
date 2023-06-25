package com.hakan.injection.listener.module;

import com.google.inject.Injector;
import com.hakan.injection.executor.SpigotExecutor;
import com.hakan.injection.listener.annotations.EventListener;
import com.hakan.injection.listener.executor.ListenerExecutor;
import com.hakan.injection.module.SpigotModule;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * ListenerModule registers event listeners.
 */
public class ListenerModule extends SpigotModule<Method, EventListener> {

    /**
     * Constructor of ListenerModule.
     *
     * @param plugin      plugin
     * @param reflections reflections
     */
    public ListenerModule(@Nonnull Plugin plugin,
                          @Nonnull Reflections reflections) {
        super(plugin, reflections, Method.class, EventListener.class);
    }

    /**
     * Loads event listener methods that are annotated with {@link EventListener}.
     * And creates {@link ListenerExecutor} for each class to
     * handle configuration processes.
     *
     * @param methods methods that are annotated with {@link EventListener}.
     */
    @Override
    public void load(@Nonnull Set<Method> methods) {
        for (Method method : methods) {
            Class<?> clazz = method.getParameters()[0].getType();

            if (!Event.class.isAssignableFrom(clazz))
                throw new RuntimeException("event listener method parameter must be a subclass of org.bukkit.event.Event!");
            if (method.getParameterCount() != 1)
                throw new RuntimeException("event listener method must have only one parameter!");
            if (method.getReturnType() != void.class)
                throw new RuntimeException("event listener method must have void return type!");

            super.executors.add(new ListenerExecutor(super.plugin, method));
        }
    }

    /**
     * Executes all event listener executors which are
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
