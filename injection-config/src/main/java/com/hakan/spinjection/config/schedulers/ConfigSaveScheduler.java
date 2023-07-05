package com.hakan.spinjection.config.schedulers;

import com.hakan.spinjection.config.annotations.ConfigFile;
import com.hakan.spinjection.config.annotations.ConfigTimer;
import com.hakan.spinjection.config.container.Container;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import java.util.Timer;

/**
 * ConfigSaveScheduler is a scheduler
 * for saving the config file automatically.
 */
public class ConfigSaveScheduler extends BukkitRunnable {

    private final Plugin plugin;
    private final Container container;
    private final ConfigTimer saveTimer;

    /**
     * Constructor of {@link ConfigSaveScheduler}
     *
     * @param plugin     plugin
     * @param container  container
     * @param annotation annotation
     */
    public ConfigSaveScheduler(@Nonnull Plugin plugin,
                               @Nonnull Container container,
                               @Nonnull ConfigFile annotation) {
        this.plugin = plugin;
        this.container = container;
        this.saveTimer = annotation.saveTimer();
    }

    /**
     * Starts the scheduler.
     */
    public void start() {
        if (!this.saveTimer.enabled())
            return;

        long delay = this.saveTimer.timeUnit().toMillis(this.saveTimer.delay()) / 50L;
        long period = this.saveTimer.timeUnit().toMillis(this.saveTimer.period()) / 50L;
        boolean async = this.saveTimer.async();

        if (period == 0 && async) {
            this.runTaskLaterAsynchronously(this.plugin, delay);
        } else if (period == 0) {
            this.runTaskLater(this.plugin, delay);
        } else if (async) {
            this.runTaskTimerAsynchronously(this.plugin, delay, period);
        } else {
            this.runTaskTimer(this.plugin, delay, period);
        }
    }

    /**
     * Saves the config file.
     */
    @Override
    public void run() {
        this.container.save();
    }
}
