package com.hakan.injection.config.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * SaveTimer annotation to
 * define auto-save settings.
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SaveTimer {

    /**
     * Auto-save status of the
     * config file. If it is true,
     * the config file will take
     * the settings from the file
     * automatically.
     *
     * @return auto reload status
     */
    boolean enabled() default false;

    /**
     * If the save is async,
     * it will be saved in
     * another thread. In a
     * nutshell, it will be
     * asynchronous.
     *
     * @return async status
     */
    boolean async() default true;

    /**
     * Auto-save delay time
     * of the config file.
     * <p>
     * It's reload process will start
     * after the delay time.
     *
     * @return timestamp
     */
    long delay() default 0L;

    /**
     * Auto-save period time
     * of the config file.
     * <p>
     * It's reload process will be
     * repeated every period time.
     *
     * @return timestamp
     */
    long period() default 0L;

    /**
     * Time unit of the auto save.
     * Default is seconds.
     *
     * @return time unit
     */
    @Nonnull
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
