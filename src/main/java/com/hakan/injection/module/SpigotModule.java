package com.hakan.injection.module;

import com.google.inject.AbstractModule;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

/**
 * SpigotModule is a class that
 * extends AbstractModule.
 */
public abstract class SpigotModule extends AbstractModule {

    protected final Plugin plugin;

    /**
     * Constructor of SpigotModule.
     *
     * @param plugin plugin instance
     */
    public SpigotModule(@Nonnull Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Returns plugin instance.
     *
     * @return plugin instance
     */
    public @Nonnull Plugin getPlugin() {
        return this.plugin;
    }
}
