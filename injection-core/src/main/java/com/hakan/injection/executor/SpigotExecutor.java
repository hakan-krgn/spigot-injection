package com.hakan.injection.executor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Base interface for executors
 * of bukkit process classes.
 */
public interface SpigotExecutor {

    /**
     * Executes the process.
     */
    void execute(@Nonnull Object instance);

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
}
