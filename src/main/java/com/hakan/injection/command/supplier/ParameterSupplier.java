package com.hakan.injection.command.supplier;

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
     * Gets parameter and convert.
     *
     * @param parameter The parameter to get.
     * @return The parameter.
     */
    @Nonnull
    I get(@Nonnull String parameter);
}
