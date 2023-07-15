package com.hakan.spinjection.scheduler.module;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.annotations.ExecutorOrder;
import com.hakan.spinjection.executor.SpigotExecutor;
import com.hakan.spinjection.module.SpigotModule;
import com.hakan.spinjection.scheduler.annotations.Scheduler;
import com.hakan.spinjection.scheduler.executor.SchedulerExecutor;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * SchedulerModule registers scheduler methods
 * that are annotated with Scheduler.
 */
@ExecutorOrder(5)
public class SchedulerModule extends SpigotModule<Method, Scheduler> {

    /**
     * Constructor of SchedulerModule.
     *
     * @param bootstrap bootstrap
     */
    public SchedulerModule(@Nonnull SpigotBootstrap bootstrap) {
        super(bootstrap, Method.class, Scheduler.class);
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
     * Executes all scheduler executors that are
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
