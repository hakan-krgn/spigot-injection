package com.hakan.spinjection;

import com.hakan.basicdi.Injector;
import com.hakan.basicdi.annotations.Component;
import com.hakan.basicdi.annotations.Service;
import com.hakan.basicdi.module.Module;
import com.hakan.basicdi.reflection.Reflection;
import com.hakan.spinjection.annotations.Scanner;
import com.hakan.spinjection.filter.FilterEngine;
import com.hakan.spinjection.module.PluginModule;
import com.hakan.spinjection.module.SpigotModule;
import com.hakan.spinjection.utils.ReflectionUtils;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.TreeSet;

/**
 * SpigotBootstrap is bootstrap class
 * of the injection library. It starts
 * automatic injection and injects
 * all classes that are specified
 * in modules.
 */
@Scanner("com.hakan.spinjection")
public class SpigotBootstrap extends Module {

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
    private final Reflection spigotReflection;
    private final Reflection pluginReflection;
    private final FilterEngine filterEngine;
    private final Set<SpigotModule<?, ?>> modules;

    /**
     * Constructor of SpigotBootstrap.
     *
     * @param plugin plugin instance
     */
    private SpigotBootstrap(@Nonnull Plugin plugin) {
        this.plugin = plugin;
        this.modules = new TreeSet<>();
        this.spigotReflection = ReflectionUtils.createFrom(this);
        this.pluginReflection = ReflectionUtils.createFrom(plugin);

        this.injector = Injector.of(this);
        this.filterEngine = new FilterEngine(this.injector);

        this.modules.forEach(SpigotModule::execute);
        this.injector.create();
    }

    /**
     * Gets Plugin instance.
     *
     * @return plugin
     */
    public @Nonnull Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * Gets Injector instance.
     *
     * @return injector
     */
    public @Nonnull Injector getInjector() {
        return this.injector;
    }

    /**
     * Gets FilterEngine instance.
     *
     * @return filterEngine
     */
    public @Nonnull FilterEngine getFilterEngine() {
        return this.filterEngine;
    }

    /**
     * Gets Reflections instance.
     *
     * @return reflections
     */
    public @Nonnull Reflection getReflection() {
        return this.pluginReflection;
    }



    /**
     * This method is called by Guice to
     * configure the injector.
     * <p>
     * When Guice#createInjector is called,
     * it will invoke this method and
     * bind all classes to register
     * them to the injector.
     */
    @Override
    public void configure() {
        this.bind(Plugin.class).withInstance(this.plugin);
        this.bind(Reflection.class).withInstance(this.pluginReflection);
        this.bind(SpigotBootstrap.class).withInstance(this);

        this.pluginReflection.getTypesAnnotatedWith(Service.class).forEach(this::bind);
        this.pluginReflection.getTypesAnnotatedWith(Component.class).forEach(this::bind);
        this.pluginReflection.getSubtypesOf(PluginModule.class).forEach(this::installPluginModule);
        this.spigotReflection.getSubtypesOf(SpigotModule.class).forEach(this::installSpigotModule);
    }



    /**
     * Binds the class to
     * instance of itself.
     *
     * @param clazz class
     */
    @SneakyThrows
    private void installSpigotModule(@Nonnull Class<?> clazz) {
        SpigotModule<?, ?> spigotModule = (SpigotModule<?, ?>) clazz
                .getConstructor(SpigotBootstrap.class)
                .newInstance(this);

        this.install(spigotModule);
        this.modules.add(spigotModule);
    }

    /**
     * Binds the class to
     * instance of itself.
     *
     * @param clazz class
     */
    @SneakyThrows
    private void installPluginModule(@Nonnull Class<?> clazz) {
        this.install((PluginModule) clazz.getConstructor().newInstance());
    }
}
