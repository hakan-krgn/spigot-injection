package com.hakan.injection.command.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Subcommand class to specify
 * sub commands for command.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subcommand {

    /**
     * Gets permission of annotation.
     *
     * @return Permission of annotation.
     */
    @Nonnull
    String permission() default "";

    /**
     * Gets the permission message of annotation.
     *
     * @return Permission message of annotation.
     */
    @Nonnull
    String permissionMessage() default "";
}
