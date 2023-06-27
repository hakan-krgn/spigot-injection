package com.hakan.spinjection.scheduler.executor;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.executor.SpigotExecutor;
import com.hakan.spinjection.scheduler.annotations.Scheduler;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

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
     * Gets the instance of the method
     * that is annotated with {@link Scheduler}.
     *
     * @return instance
     */
    @Override
    public @Nullable Object getInstance() {
        return this.instance;
    }

    /**
     * Gets the declaring class of the method
     * that is annotated with {@link Scheduler}.
     *
     * @return declaring class
     */
    @Override
    public @Nonnull Class<?> getDeclaringClass() {
        return this.method.getDeclaringClass();
    }



    /**
     * Starts the scheduler.
     *
     * @param bootstrap injector
     * @param instance  instance
     */
    @Override
    public void execute(@Nonnull SpigotBootstrap bootstrap,
                        @Nonnull Object instance) {
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
     * Executes the method which is
     * annotated with {@link Scheduler}.
     */
    @Override
    @SneakyThrows
    public void run() {
        this.method.invoke(this.instance);
    }
}
