package com.hakan.injection.scheduler.module;

import com.google.inject.Injector;
import com.hakan.injection.module.impl.MethodModule;
import com.hakan.injection.scheduler.annotations.Scheduler;
import com.hakan.injection.scheduler.executor.SchedulerExecutor;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * SchedulerModule registers scheduler methods
 * that are annotated with Scheduler.
 */
public class SchedulerModule extends MethodModule<Scheduler> {

    /**
     * Constructor of SchedulerModule.
     *
     * @param plugin      plugin
     * @param injector    injector
     * @param reflections reflections
     */
    public SchedulerModule(@Nonnull Plugin plugin,
                           @Nonnull Injector injector,
                           @Nonnull Reflections reflections) {
        super(plugin, injector, reflections, Scheduler.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRegister(@Nonnull Method method,
                           @Nonnull Object instance,
                           @Nonnull Scheduler annotation) {
        if (method.getParameterCount() != 0)
            throw new RuntimeException("scheduler method must have no parameters!");
        if (method.getReturnType() != void.class)
            throw new RuntimeException("scheduler method must have void return type!");

        new SchedulerExecutor(super.plugin, annotation, instance, method).register();
    }
}
