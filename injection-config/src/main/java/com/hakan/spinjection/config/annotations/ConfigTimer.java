package com.hakan.spinjection.config.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * ReloadTimer annotation to
 * define auto-reload settings.
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigTimer {

	/**
	 * Timer status of the
	 * config file. If it is true,
	 * the config file will do
	 * the processes.
	 *
	 * @return status
	 */
	boolean enabled() default false;

	/**
	 * If the status is async,
	 * it will be processed in
	 * another thread. In a
	 * nutshell, it will be
	 * asynchronous.
	 *
	 * @return async status
	 */
	boolean async() default false;

	/**
	 * Auto-reload delay time
	 * of the config file.
	 * <p>
	 * It's process will start
	 * after the delay time.
	 *
	 * @return timestamp
	 */
	long delay() default 0L;

	/**
	 * Auto-process period time
	 * of the config file.
	 * <p>
	 * It's process will be
	 * repeated every period time.
	 *
	 * @return timestamp
	 */
	long period() default 0L;

	/**
	 * Time unit of the auto-process.
	 * Default is seconds.
	 *
	 * @return time unit
	 */
	@Nonnull
	TimeUnit timeUnit() default TimeUnit.SECONDS;
}
