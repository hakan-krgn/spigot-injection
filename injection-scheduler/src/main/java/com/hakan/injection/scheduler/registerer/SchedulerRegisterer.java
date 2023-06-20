package com.hakan.injection.scheduler.registerer;

import com.google.inject.Injector;
import com.hakan.injection.registerer.SpigotRegisterer;
import com.hakan.injection.scheduler.annotations.Scheduler;
import com.hakan.injection.scheduler.executor.SchedulerExecutor;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * SchedulerModule registers scheduler methods
 * that are annotated with Scheduler.
 */
public class SchedulerRegisterer extends SpigotRegisterer<Method, Scheduler> {

    /**
     * Constructor of SchedulerModule.
     *
     * @param plugin      plugin
     * @param injector    injector
     * @param reflections reflections
     */
    public SchedulerRegisterer(@Nonnull Plugin plugin,
                               @Nonnull Injector injector,
                               @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Method.class, Scheduler.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRegister(@Nullable Object instance,
                           @Nonnull Method method,
                           @Nonnull Scheduler scheduler) {
        if (instance == null)
            throw new RuntimeException("event listener instance cannot be null!");
        if (method.getParameterCount() != 0)
            throw new RuntimeException("scheduler method must have no parameters!");
        if (method.getReturnType() != void.class)
            throw new RuntimeException("scheduler method must have void return type!");

        new SchedulerExecutor(super.plugin, instance, method, scheduler).execute();
    }
}
