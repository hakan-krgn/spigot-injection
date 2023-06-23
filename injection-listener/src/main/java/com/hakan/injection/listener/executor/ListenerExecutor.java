package com.hakan.injection.listener.executor;

import com.google.inject.Injector;
import com.hakan.injection.executor.SpigotExecutor;
import com.hakan.injection.listener.annotations.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.reflections.ReflectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * ListenerExecutor is a class that
 * executes event listener method.
 */
@SuppressWarnings({"unchecked"})
public class ListenerExecutor implements Listener, EventExecutor, SpigotExecutor {

    private Object instance;
    private final Plugin plugin;
    private final Method method;
    private final EventListener listener;
    private final Class<? extends Event> clazz;

    /**
     * Constructor of ListenerExecutor.
     *
     * @param plugin plugin
     * @param method method
     */
    public ListenerExecutor(@Nonnull Plugin plugin,
                            @Nonnull Method method) {
        this.plugin = plugin;
        this.method = method;
        this.listener = method.getAnnotation(EventListener.class);
        this.clazz = (Class<? extends Event>) method.getParameters()[0].getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Object getInstance() {
        return this.instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Class<?> getDeclaringClass() {
        return this.method.getDeclaringClass();
    }



    /**
     * Registers listener to bukkit and
     * connects events to method.
     */
    @Override
    public void execute(@Nonnull Object instance,
                        @Nonnull Injector injector) {
        this.instance = instance;

        Bukkit.getPluginManager().registerEvent(
                this.clazz,
                this,
                this.listener.priority(),
                this,
                this.plugin,
                false
        );
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
