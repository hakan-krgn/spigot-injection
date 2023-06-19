package com.hakan.injection.listener.annotations;

import org.bukkit.event.EventPriority;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Listener annotation to define
 * event listener method.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {

    /**
     * Gets event priority of annotation.
     *
     * @return Event priority of annotation.
     */
    @Nonnull
    EventPriority priority() default EventPriority.NORMAL;
}
