package com.hakan.injection.listener.module;

import com.google.inject.Injector;
import com.hakan.injection.listener.annotations.EventListener;
import com.hakan.injection.listener.executor.ListenerExecutor;
import com.hakan.injection.module.impl.MethodModule;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * ListenerModule registers event listeners.
 */
@SuppressWarnings({"unchecked"})
public class ListenerModule extends MethodModule<EventListener> {

    /**
     * Constructor of ListenerModule.
     *
     * @param plugin      plugin
     * @param injector    injector
     * @param reflections reflections
     */
    public ListenerModule(@Nonnull Plugin plugin,
                          @Nonnull Injector injector,
                          @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, EventListener.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRegister(@Nonnull Method method,
                           @Nonnull Object instance,
                           @Nonnull EventListener listener) {
        Class<?> clazz = method.getParameters()[0].getType();

        if (!Event.class.isAssignableFrom(clazz))
            throw new RuntimeException("event listener method parameter must be a subclass of org.bukkit.event.Event!");
        if (method.getParameterCount() != 1)
            throw new RuntimeException("event listener method must have only one parameter!");
        if (method.getReturnType() != void.class)
            throw new RuntimeException("event listener method must have void return type!");

        new ListenerExecutor(this.plugin, listener, (Class<? extends Event>) clazz, instance, method).register();
    }
}
