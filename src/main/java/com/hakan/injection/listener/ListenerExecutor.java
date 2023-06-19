package com.hakan.injection.listener;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.reflections.ReflectionUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * ListenerExecutor is a class that
 * executes event listener method.
 */
public class ListenerExecutor implements Listener, EventExecutor {

    private final Object instance;
    private final Method method;

    /**
     * Constructor of ListenerExecutor.
     *
     * @param instance instance
     * @param method   method
     */
    public ListenerExecutor(@Nonnull Object instance,
                            @Nonnull Method method) {
        this.instance = instance;
        this.method = method;
    }

    /**
     * Executes event listener method.
     *
     * @param listener listener
     * @param event    event
     */
    @Override
    public void execute(@Nonnull Listener listener,
                        @Nonnull Event event) {
        ReflectionUtils.invoke(this.method, this.instance);
    }
}
