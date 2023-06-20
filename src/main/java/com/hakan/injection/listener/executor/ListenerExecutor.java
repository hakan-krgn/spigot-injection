package com.hakan.injection.listener.executor;

import com.hakan.injection.SpigotExecutor;
import com.hakan.injection.listener.annotations.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.reflections.ReflectionUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * ListenerExecutor is a class that
 * executes event listener method.
 */
@SuppressWarnings({"unchecked"})
public class ListenerExecutor implements Listener, EventExecutor, SpigotExecutor {

    private final Plugin plugin;
    private final Method method;
    private final Object instance;
    private final EventListener listener;
    private final Class<? extends Event> clazz;

    /**
     * Constructor of ListenerExecutor.
     *
     * @param plugin   plugin
     * @param listener listener
     * @param method   method
     * @param instance instance
     */
    public ListenerExecutor(@Nonnull Plugin plugin,
                            @Nonnull EventListener listener,
                            @Nonnull Object instance,
                            @Nonnull Method method) {
        this.plugin = plugin;
        this.method = method;
        this.instance = instance;
        this.listener = listener;
        this.clazz = (Class<? extends Event>) method.getParameters()[0].getType();
    }

    /**
     * Registers listener to bukkit and
     * connects events to method.
     */
    @Override
    public void execute() {
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
