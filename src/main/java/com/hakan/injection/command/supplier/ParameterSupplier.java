package com.hakan.injection.command.supplier;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface ParameterSupplier<I> {

    @Nonnull
    I get(@Nonnull String parameter);
}
