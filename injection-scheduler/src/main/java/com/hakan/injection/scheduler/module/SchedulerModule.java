package com.hakan.injection.scheduler.module;

import com.google.inject.Injector;
import com.hakan.injection.executor.SpigotExecutor;
import com.hakan.injection.module.SpigotModule;
import com.hakan.injection.scheduler.annotations.Scheduler;
import com.hakan.injection.scheduler.executor.SchedulerExecutor;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * SchedulerModule registers scheduler methods
 * that are annotated with Scheduler.
 */
public class SchedulerModule extends SpigotModule<Method, Scheduler> {

    /**
     * Constructor of SchedulerModule.
     *
     * @param plugin      plugin
     * @param reflections reflections
     */
    public SchedulerModule(@Nonnull Plugin plugin,
                           @Nonnull Reflections reflections) {
        super(plugin, reflections, Method.class, Scheduler.class);
    }

    /**
     * Loads the scheduler methods that are annotated with {@link Scheduler}.
     * And creates {@link SchedulerExecutor} for each class to
     * handle configuration processes.
     *
     * @param methods methods that are annotated with {@link Scheduler}.
     */
    @Override
    public void load(@Nonnull Set<Method> methods) {
        for (Method method : methods) {
            if (method.getParameterCount() != 0)
                throw new RuntimeException("scheduler method must have no parameters!");
            if (method.getReturnType() != void.class)
                throw new RuntimeException("scheduler method must have void return type!");

            super.executors.add(new SchedulerExecutor(super.plugin, method));
        }
    }

    /**
     * Executes all scheduler executors which are
     * saved in {@link #executors}.
     * <p>
     * Then it runs execute method of each executor.
     *
     * @param injector injector
     */
    @Override
    public void execute(@Nonnull Injector injector) {
        for (SpigotExecutor executor : super.executors) {
            executor.execute(injector.getInstance(executor.getDeclaringClass()), injector);
        }
    }
}
