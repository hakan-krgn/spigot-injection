package com.hakan.injection.listener;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.reflections.ReflectionUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

public class ListenerExecutor implements Listener, EventExecutor {

    private final Object instance;
    private final Method method;

    public ListenerExecutor(@Nonnull Object instance,
                            @Nonnull Method method) {
        this.instance = instance;
        this.method = method;
    }

    @Override
    public void execute(@Nonnull Listener listener,
                        @Nonnull Event event) {
        ReflectionUtils.invoke(this.method, this.instance);
    }
}
