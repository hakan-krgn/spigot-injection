package com.hakan.spinjection.command.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command annotation to
 * define command features.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * Gets command name of annotation.
     *
     * @return Command name of annotation.
     */
    @Nonnull
    String name();

    /**
     * Gets command description of annotation.
     *
     * @return Command description of annotation.
     */
    @Nonnull
    String description() default "";

    /**
     * Gets command usage of annotation.
     *
     * @return Command usage of annotation.
     */
    @Nonnull
    String usage() default "";

    /**
     * Gets command aliases of annotation.
     *
     * @return Command aliases of annotation.
     */
    @Nonnull
    String[] aliases() default "";

    /**
     * Gets auto tab complete of command.
     *
     * @return Auto tab complete of command.
     */
    boolean tabComplete() default true;
}
