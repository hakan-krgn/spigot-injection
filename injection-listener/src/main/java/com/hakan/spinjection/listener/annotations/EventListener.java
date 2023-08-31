package com.hakan.spinjection.listener.annotations;

import org.bukkit.event.EventPriority;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Listener annotation to define
 * event listener method.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {

	/**
	 * If true, the event won't be called
	 * if the event is canceled by another plugin.
	 *
	 * @return Should ignore if the event is canceled.
	 */
	boolean ignoreCancelled() default false;

	/**
	 * Gets event priority of annotation.
	 *
	 * @return Event priority of annotation.
	 */
	@Nonnull
	EventPriority priority() default EventPriority.NORMAL;
}
