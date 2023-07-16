package com.hakan.spinjection.cache;

import com.hakan.spinjection.SpigotBootstrap;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Bootstrap cache to
 * get, register and remove
 * SpigotBootstrap instances.
 */
public class BootstrapCache {

    private final Map<Class<?>, SpigotBootstrap> cache = new HashMap<>();


    /**
     * Finds a SpigotBootstrap by class.
     *
     * @param clazz class of the plugin
     * @return SpigotBootstrap instance
     */
    public @Nonnull Optional<SpigotBootstrap> findByClass(@Nonnull Class<?> clazz) {
        return Optional.ofNullable(this.cache.get(clazz));
    }

    /**
     * Returns the SpigotBootstrap instance
     * if it is initialized.
     * <p>
     * Otherwise, it throws an exception.
     *
     * @param clazz class of the plugin
     * @return SpigotBootstrap instance
     */
    public @Nonnull SpigotBootstrap getByClass(@Nonnull Class<?> clazz) {
        return this.findByClass(clazz).orElseThrow(() -> new RuntimeException("SpigotBootstrap is not initialized!"));
    }


    /**
     * Puts a SpigotBootstrap
     * instance to the cache.
     *
     * @param bootstrap SpigotBootstrap instance
     * @return SpigotBootstrap instance
     */
    public @Nonnull SpigotBootstrap put(@Nonnull SpigotBootstrap bootstrap) {
        this.cache.put(bootstrap.getPlugin().getClass(), bootstrap);
        return bootstrap;
    }

    /**
     * Removes a SpigotBootstrap
     * instance from the cache.
     *
     * @param clazz class of the plugin
     * @return SpigotBootstrap instance
     */
    public @Nonnull SpigotBootstrap remove(@Nonnull Class<?> clazz) {
        return this.cache.remove(clazz);
    }
}
