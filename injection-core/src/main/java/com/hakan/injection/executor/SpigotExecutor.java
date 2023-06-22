package com.hakan.injection.executor;

import com.google.inject.Injector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Base interface for executors
 * of bukkit process classes.
 */
public interface SpigotExecutor {

    /**
     * Gets the instance.
     *
     * @return instance
     */
    @Nullable
    Object getInstance();

    /**
     * Gets the declaring
     * class of the instance.
     *
     * @return declaring class
     */
    @Nonnull
    Class<?> getDeclaringClass();

    /**
     * Executes the process.
     * When the process executed,
     * the instance will be set and
     * bukkit process will be registered.
     *
     * @param instance instance
     * @param injector injector
     */
    void execute(@Nonnull Object instance,
                 @Nonnull Injector injector);
}
