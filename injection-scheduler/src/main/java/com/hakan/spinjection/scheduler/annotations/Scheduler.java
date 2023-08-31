package com.hakan.spinjection.scheduler.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Scheduler is an annotation that
 * registers scheduler methods.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scheduler {

	/**
	 * Gets scheduler delay of annotation.
	 *
	 * @return Scheduler delay of annotation.
	 */
	long delay() default 0L;

	/**
	 * Gets a scheduler period of annotation.
	 *
	 * @return Scheduler period of annotation.
	 */
	long period() default 0L;

	/**
	 * Gets scheduler time unit of annotation.
	 *
	 * @return Scheduler time unit of annotation.
	 */
	@Nonnull
	TimeUnit timeUnit() default TimeUnit.SECONDS;
}
