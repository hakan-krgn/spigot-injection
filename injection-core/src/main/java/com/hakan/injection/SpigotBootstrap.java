package com.hakan.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hakan.injection.annotations.Component;
import com.hakan.injection.annotations.Scanner;
import com.hakan.injection.annotations.Service;
import com.hakan.injection.module.PluginModule;
import com.hakan.injection.module.SpigotModule;
import com.hakan.injection.utils.ReflectionUtils;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * SpigotBootstrap is bootstrap class
 * of the injection library. It starts
 * automatic injection and inject
 * all classes that are specified
 * in modules.
 */
@Scanner("com.hakan.injection")
public class SpigotBootstrap extends AbstractModule {

    /**
     * Starts automatic injection.
     *
     * @param plugin plugin instance
     * @return bootstrap
     */
    public static @Nonnull SpigotBootstrap run(@Nonnull Plugin plugin) {
        return new SpigotBootstrap(plugin);
    }


    private final Plugin plugin;
    private final Injector injector;
    private final Reflections apiReflections;
    private final Reflections pluginReflections;
    private final List<SpigotModule<?, ?>> modules;

    /**
     * Constructor of SpigotBootstrap.
     *
     * @param plugin plugin instance
     */
    private SpigotBootstrap(@Nonnull Plugin plugin) {
        this.plugin = plugin;
        this.modules = new ArrayList<>();
        this.apiReflections = ReflectionUtils.createFrom(this);
        this.pluginReflections = ReflectionUtils.createFrom(plugin);
        this.injector = Guice.createInjector(this);

        this.modules.forEach(module -> module.execute(this.injector));
    }

    /**
     * Returns plugin instance.
     *
     * @return plugin
     */
    public @Nonnull Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * Returns injector instance.
     *
     * @return injector
     */
    public @Nonnull Injector getInjector() {
        return this.injector;
    }

    /**
     * Returns reflections instance.
     *
     * @return reflections
     */
    public @Nonnull Reflections getReflections() {
        return this.pluginReflections;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        this.pluginReflections.getTypesAnnotatedWith(Service.class).forEach(this::bind);
        this.pluginReflections.getTypesAnnotatedWith(Component.class).forEach(this::bind);

        this.apiReflections.getSubTypesOf(SpigotModule.class).forEach(this::installSpigotModule);
        this.pluginReflections.getSubTypesOf(PluginModule.class).forEach(this::installPluginModule);
    }

    /**
     * Binds the class to
     * instance of itself.
     *
     * @param clazz class
     */
    private void installSpigotModule(@Nonnull Class<?> clazz) {
        SpigotModule<?, ?> module = ReflectionUtils.newInstance(
                clazz,
                new Class[]{Plugin.class, Reflections.class},
                new Object[]{this.plugin, this.pluginReflections}
        );

        this.install(module);
        this.modules.add(module);
    }

    /**
     * Binds the class to
     * instance of itself.
     *
     * @param clazz class
     */
    private void installPluginModule(@Nonnull Class<?> clazz) {
        PluginModule module = ReflectionUtils.newInstance(
                clazz,
                new Class[]{},
                new Object[]{}
        );

        this.install(module);
    }
}
