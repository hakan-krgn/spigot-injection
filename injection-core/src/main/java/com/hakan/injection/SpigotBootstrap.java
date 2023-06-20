package com.hakan.injection;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hakan.injection.modules.PluginModule;
import com.hakan.injection.modules.ScannerModule;
import com.hakan.injection.utils.ReflectionUtils;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * SpigotBootstrap is bootstrap class
 * of the injection library. It starts
 * automatic injection and inject
 * all classes that are specified
 * in modules.
 */
public class SpigotBootstrap {

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
    private final Reflections reflections;

    /**
     * Constructor of SpigotBootstrap.
     *
     * @param plugin plugin instance
     */
    private SpigotBootstrap(@Nonnull Plugin plugin) {
        this(plugin, Guice.createInjector(new PluginModule(plugin)));
    }

    /**
     * Constructor of SpigotBootstrap.
     *
     * @param plugin   plugin instance
     * @param injector injector instance
     */
    private SpigotBootstrap(@Nonnull Plugin plugin,
                            @Nonnull Injector injector) {
        this.plugin = plugin;
        this.reflections = ReflectionUtils.createFrom(plugin);
        this.injector = Guice.createInjector(new ScannerModule(injector, this.reflections));
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
        return this.reflections;
    }

    /**
     * Creates a registerer.
     *
     * @param registerers registerers
     */
    public void register(@Nonnull SpigotRegisterer<?, ?>... registerers) {
        Arrays.stream(registerers).forEach(SpigotRegisterer::register);
    }
}
