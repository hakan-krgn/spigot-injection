package com.hakan.spinjection.command.supplier;

import javax.annotation.Nonnull;

/**
 * Parameter supplier to
 * get parameter and convert
 * it to the desired type.
 *
 * @param <I> The type of the parameter.
 */
@FunctionalInterface
public interface ParameterSupplier<I> {

    /**
     * Gets the parameter and
     * converts it to the desired type.
     *
     * @param parameter parameter to parse
     * @return parsed parameter
     */
    @Nonnull
    I get(@Nonnull String parameter);
}
