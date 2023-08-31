package com.hakan.spinjection.executor;

import com.hakan.spinjection.SpigotBootstrap;

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
     * When the process is executed,
     * the instance will be set and the
     * bukkit process will be registered.
     *
     * @param bootstrap injector
     * @param instance  instance
     */
    void execute(@Nonnull SpigotBootstrap bootstrap, @Nonnull Object instance);
}
