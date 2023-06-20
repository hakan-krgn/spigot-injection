package com.hakan.injection.listener.registerer;

import com.google.inject.Injector;
import com.hakan.injection.SpigotRegisterer;
import com.hakan.injection.listener.annotations.EventListener;
import com.hakan.injection.listener.executor.ListenerExecutor;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * ListenerModule registers event listeners.
 */
public class ListenerRegisterer extends SpigotRegisterer<Method, EventListener> {

    /**
     * Constructor of ListenerModule.
     *
     * @param plugin      plugin
     * @param injector    injector
     * @param reflections reflections
     */
    public ListenerRegisterer(@Nonnull Plugin plugin,
                              @Nonnull Injector injector,
                              @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Method.class, EventListener.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRegister(@Nonnull Object instance,
                           @Nonnull Method method,
                           @Nonnull EventListener listener) {
        Class<?> clazz = method.getParameters()[0].getType();

        if (!Event.class.isAssignableFrom(clazz))
            throw new RuntimeException("event listener method parameter must be a subclass of org.bukkit.event.Event!");
        if (method.getParameterCount() != 1)
            throw new RuntimeException("event listener method must have only one parameter!");
        if (method.getReturnType() != void.class)
            throw new RuntimeException("event listener method must have void return type!");

        new ListenerExecutor(this.plugin, listener, instance, method).execute();
    }
}
