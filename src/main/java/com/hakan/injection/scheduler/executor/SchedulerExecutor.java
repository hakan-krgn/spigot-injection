package com.hakan.injection.scheduler.executor;

import com.hakan.injection.SpigotExecutor;
import com.hakan.injection.scheduler.annotations.Scheduler;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.ReflectionUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * SchedulerRunnable is a class that
 * executes scheduler method.
 */
public class SchedulerExecutor extends BukkitRunnable implements SpigotExecutor {

    private final Plugin plugin;
    private final long delay;
    private final long period;
    private final boolean async;

    private final Object instance;
    private final Method method;

    /**
     * Constructor of SchedulerRunnable.
     *
     * @param plugin    plugin
     * @param scheduler scheduler annotation
     * @param instance  instance
     * @param method    method
     */
    public SchedulerExecutor(@Nonnull Plugin plugin,
                             @Nonnull Object instance,
                             @Nonnull Method method,
                             @Nonnull Scheduler scheduler) {
        this.plugin = plugin;
        this.method = method;
        this.instance = instance;
        this.async = scheduler.async();
        this.delay = scheduler.timeUnit().toMillis(scheduler.delay()) / 50;
        this.period = scheduler.timeUnit().toMillis(scheduler.period()) / 50;
    }

    /**
     * Starts scheduler.
     */
    @Override
    public void execute() {
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
