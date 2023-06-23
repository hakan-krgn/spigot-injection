package com.hakan.injection.scheduler.executor;

import com.google.inject.Injector;
import com.hakan.injection.executor.SpigotExecutor;
import com.hakan.injection.scheduler.annotations.Scheduler;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.ReflectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * SchedulerRunnable is a class that
 * executes scheduler method.
 */
public class SchedulerExecutor extends BukkitRunnable implements SpigotExecutor {

    private Object instance;
    private final Plugin plugin;
    private final Method method;
    private final long delay;
    private final long period;
    private final boolean async;

    /**
     * Constructor of SchedulerRunnable.
     *
     * @param plugin plugin
     * @param method method
     */
    public SchedulerExecutor(@Nonnull Plugin plugin,
                             @Nonnull Method method) {
        this(plugin, method, method.getAnnotation(Scheduler.class));
    }

    /**
     * Constructor of SchedulerRunnable.
     *
     * @param plugin    plugin
     * @param scheduler scheduler annotation
     * @param method    method
     */
    public SchedulerExecutor(@Nonnull Plugin plugin,
                             @Nonnull Method method,
                             @Nonnull Scheduler scheduler) {
        this.plugin = plugin;
        this.method = method;
        this.async = scheduler.async();
        this.delay = scheduler.timeUnit().toMillis(scheduler.delay()) / 50;
        this.period = scheduler.timeUnit().toMillis(scheduler.period()) / 50;
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
     * Starts scheduler.
     */
    @Override
    public void execute(@Nonnull Object instance,
                        @Nonnull Injector injector) {
        this.instance = instance;

        if (this.period == 0 && this.async) {
            this.runTaskLaterAsynchronously(this.plugin, this.delay);
        } else if (this.period == 0) {
            this.runTaskLater(this.plugin, this.delay);
        } else if (this.async) {
            this.runTaskTimerAsynchronously(this.plugin, this.delay, this.period);
        } else {
            this.runTaskTimer(this.plugin, this.delay, this.period);
        }
    }


    /**
     * Executes scheduler method.
     */
    @Override
    public void run() {
        ReflectionUtils.invoke(this.method, this.instance);
    }
}
