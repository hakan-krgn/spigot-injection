package com.hakan.injection.module;

import com.google.inject.AbstractModule;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public abstract class SpigotModule extends AbstractModule {

    protected final Plugin plugin;

    public SpigotModule(@Nonnull Plugin plugin) {
        this.plugin = plugin;
    }

    public @Nonnull Plugin getPlugin() {
        return this.plugin;
    }
}
