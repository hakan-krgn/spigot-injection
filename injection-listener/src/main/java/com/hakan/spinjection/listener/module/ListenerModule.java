package com.hakan.spinjection.listener.module;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.annotations.ExecutorOrder;
import com.hakan.spinjection.executor.SpigotExecutor;
import com.hakan.spinjection.listener.annotations.EventListener;
import com.hakan.spinjection.listener.executor.ListenerExecutor;
import com.hakan.spinjection.module.SpigotModule;
import org.bukkit.event.Event;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * ListenerModule registers event listeners.
 */
@ExecutorOrder(4)
public class ListenerModule extends SpigotModule<Method, EventListener> {

    /**
     * Constructor of ListenerModule.
     *
     * @param bootstrap bootstrap
     */
    public ListenerModule(@Nonnull SpigotBootstrap bootstrap) {
        super(bootstrap, Method.class, EventListener.class);
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
            Class<?> eventClass = method.getParameters()[0].getType();

            if (!Event.class.isAssignableFrom(eventClass))
                throw new RuntimeException("event listener method parameter must be a subclass of org.bukkit.event.Event!");
            if (method.getParameterCount() != 1)
                throw new RuntimeException("event listener method must have only one parameter!");
            if (method.getReturnType() != void.class)
                throw new RuntimeException("event listener method must have void return type!");

            super.executors.add(new ListenerExecutor(super.plugin, method));
        }
    }

    /**
     * Executes all event listener executors that are
     * saved in {@link #executors}.
     * <p>
     * Then it runs execute method of each executor.
     */
    @Override
    public void execute() {
        for (SpigotExecutor executor : super.executors) {
            executor.execute(super.bootstrap, super.bootstrap.getInstance(executor.getDeclaringClass()));
        }
    }
}
