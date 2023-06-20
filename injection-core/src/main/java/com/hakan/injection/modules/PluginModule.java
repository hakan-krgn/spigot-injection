package com.hakan.injection.modules;

import com.google.inject.AbstractModule;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

/**
 * PluginModule registers plugin instance.
 */
public class PluginModule extends AbstractModule {

    private final Plugin plugin;

    /**
     * Constructor of PluginModule.
     *
     * @param plugin plugin instance
     */
    public PluginModule(@Nonnull Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        this.bind(Plugin.class).toInstance(this.plugin);
    }
}
