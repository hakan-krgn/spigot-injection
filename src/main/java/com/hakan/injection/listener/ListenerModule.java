package com.hakan.injection.listener;

import com.google.inject.Injector;
import com.hakan.injection.listener.annotations.Listener;
import com.hakan.injection.module.impl.MethodModule;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

@SuppressWarnings({"unchecked"})
public class ListenerModule extends MethodModule<Listener> {

    public ListenerModule(@Nonnull Plugin plugin,
                          @Nonnull Injector injector,
                          @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Listener.class);
    }

    @Override
    public void onRegister(@Nonnull Method method,
                           @Nonnull Object instance,
                           @Nonnull Listener listener) {
        Class<?> clazz = method.getParameters()[0].getType();

        if (!Event.class.isAssignableFrom(clazz))
            throw new RuntimeException("event listener method parameter must be a subclass of org.bukkit.event.Event!");
        if (method.getParameterCount() != 1)
            throw new RuntimeException("event listener method must have only one parameter!");
        if (method.getReturnType() != void.class)
            throw new RuntimeException("event listener method must have void return type!");


        ListenerExecutor listenerExecutor = new ListenerExecutor(instance, method);

        Bukkit.getPluginManager().registerEvent(
                (Class<? extends Event>) clazz,
                listenerExecutor,
                listener.priority(),
                listenerExecutor,
                super.plugin,
                false
        );
    }
}
