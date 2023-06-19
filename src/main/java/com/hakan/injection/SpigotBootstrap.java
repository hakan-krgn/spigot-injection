package com.hakan.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hakan.injection.command.CommandModule;
import com.hakan.injection.listener.ListenerModule;
import com.hakan.injection.module.SpigotModule;
import com.hakan.injection.scanner.ScannerModule;
import com.hakan.injection.scheduler.SchedulerModule;
import com.hakan.injection.utils.ReflectionUtils;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;

public class SpigotBootstrap extends AbstractModule {

    public static @Nonnull SpigotBootstrap run(@Nonnull Plugin plugin) {
        SpigotBootstrap bootstrap = new SpigotBootstrap(plugin);

        bootstrap.register(new ScannerModule(plugin, bootstrap.injector, bootstrap.reflections));
        bootstrap.register(new CommandModule(plugin, bootstrap.injector, bootstrap.reflections));
        bootstrap.register(new ListenerModule(plugin, bootstrap.injector, bootstrap.reflections));
        bootstrap.register(new SchedulerModule(plugin, bootstrap.injector, bootstrap.reflections));

        return bootstrap;
    }



    private final Plugin plugin;
    private final Injector injector;
    private final Reflections reflections;

    private SpigotBootstrap(@Nonnull Plugin plugin) {
        this.plugin = plugin;
        this.injector = Guice.createInjector(this);
        this.reflections = ReflectionUtils.createFrom(plugin);
    }

    public @Nonnull Plugin getPlugin() {
        return this.plugin;
    }

    public @Nonnull Injector getInjector() {
        return this.injector;
    }

    public @Nonnull Reflections getReflections() {
        return this.reflections;
    }

    public @Nonnull Injector register(@Nonnull SpigotModule module) {
        return Guice.createInjector(module);
    }


    @Override
    protected void configure() {
        this.bind(Plugin.class).toInstance(this.plugin);
    }
}
