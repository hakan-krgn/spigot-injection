package com.hakan.spinjection.listener.executor;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.executor.SpigotExecutor;
import com.hakan.spinjection.listener.annotations.EventListener;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

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
     * Gets the instance of the method class
     * that is annotated with {@link EventListener}.
     *
     * @return instance
     */
    @Override
    public @Nullable Object getInstance() {
        return this.instance;
    }

    /**
     * Gets the class of method that is
     * annotated with {@link EventListener}.
     *
     * @return method
     */
    @Override
    public @Nonnull Class<?> getDeclaringClass() {
        return this.method.getDeclaringClass();
    }



    /**
     * Registers event listener method which is
     * annotated with {@link EventListener}
     * to {@link PluginManager} of the plugin.
     *
     * @param bootstrap injector
     * @param instance  instance
     */
    @Override
    public void execute(@Nonnull SpigotBootstrap bootstrap,
                        @Nonnull Object instance) {
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
     * Executes event listener method which is
     * annotated with {@link EventListener}.
     *
     * @param listener listener
     * @param event    event
     */
    @Override
    @SneakyThrows
    public void execute(@Nonnull Listener listener,
                        @Nonnull Event event) {
        if (!event.getClass().equals(this.clazz))
            return;

        this.method.invoke(this.instance, event);
    }
}
