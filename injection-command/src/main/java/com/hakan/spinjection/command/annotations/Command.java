package com.hakan.spinjection.command.annotations;

import com.hakan.spinjection.command.utils.NoTabCompleter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.*;

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
     * <b>NO REALIZATION FOR THIS. ALWAYS TRUE</b>
     *
     * <p> Gets auto tab complete of command.
     *
     * @return Auto tab complete of command.
     */
    boolean tabComplete() default true;

    /**
     * A class that implements the {@link org.bukkit.command.TabCompleter} interface for this command.
     * The class must have an empty constructor!
     *
     */
    @Nullable
    Class<?> tabCompleter() default NoTabCompleter.class;
}
