package com.hakan.spinjection.config.schedulers;

import com.hakan.spinjection.config.annotations.ConfigFile;
import com.hakan.spinjection.config.annotations.ReloadTimer;
import com.hakan.spinjection.config.container.Container;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;

/**
 * ConfigReloadScheduler is a scheduler
 * for reloading the config file automatically.
 */
public class ConfigReloadScheduler extends BukkitRunnable {

    private final Plugin plugin;
    private final Container container;
    private final ReloadTimer reloadTimer;

    /**
     * Constructor of {@link ConfigReloadScheduler}
     *
     * @param plugin     plugin
     * @param container  container
     * @param annotation annotation
     */
    public ConfigReloadScheduler(@Nonnull Plugin plugin,
                                 @Nonnull Container container,
                                 @Nonnull ConfigFile annotation) {
        this.plugin = plugin;
        this.container = container;
        this.reloadTimer = annotation.reloadTimer();
    }

    /**
     * Starts the scheduler.
     */
    public void start() {
        if (!this.reloadTimer.enabled())
            return;

        long delay = this.reloadTimer.timeUnit().toMillis(this.reloadTimer.delay()) / 50L;
        long period = this.reloadTimer.timeUnit().toMillis(this.reloadTimer.period()) / 50L;
        boolean async = this.reloadTimer.async();

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
     * Reloads the config file.
     */
    @Override
    public void run() {
        this.container.reload();
    }
}
