package com.hakan.injection.config.schedulers;

import com.hakan.injection.config.annotations.ConfigFile;
import com.hakan.injection.config.annotations.SaveTimer;
import com.hakan.injection.config.container.Container;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;

/**
 * ConfigSaveScheduler is a scheduler
 * for saving the config file automatically.
 */
public class ConfigSaveScheduler extends BukkitRunnable {

    private final Plugin plugin;
    private final Container container;
    private final SaveTimer saveTimer;

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

        if (!this.saveTimer.async()) this.runTaskTimer(this.plugin, delay, period);
        else this.runTaskTimerAsynchronously(this.plugin, delay, period);
    }

    /**
     * Saves the config file.
     */
    @Override
    public void run() {
        this.container.save();
    }
}
