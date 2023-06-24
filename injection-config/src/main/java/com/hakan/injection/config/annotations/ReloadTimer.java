package com.hakan.injection.config.annotations;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

/**
 * ReloadTimer annotation to
 * define auto-reload settings.
 */
public @interface ReloadTimer {

    /**
     * Auto-reload status of the
     * config file. If it is true,
     * the config file will take
     * the settings from the file
     * automatically.
     *
     * @return auto reload status
     */
    boolean enabled() default false;

    /**
     * If the reload is async,
     * it will be reloaded in
     * another thread. In a
     * nutshell, it will be
     * asynchronous.
     *
     * @return async status
     */
    boolean async() default true;

    /**
     * Auto-reload delay time
     * of the config file.
     * <p>
     * It's reload process will start
     * after the delay time.
     *
     * @return timestamp
     */
    long delay() default 0L;

    /**
     * Auto-reload period time
     * of the config file.
     * <p>
     * It's reload process will be
     * repeated every period time.
     *
     * @return timestamp
     */
    long period() default 0L;

    /**
     * Time unit of the auto reload.
     * Default is seconds.
     *
     * @return time unit
     */
    @Nonnull
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
