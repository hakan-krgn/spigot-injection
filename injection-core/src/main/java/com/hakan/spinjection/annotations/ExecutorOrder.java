package com.hakan.spinjection.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ExecutorOrder annotation to define
 * executor order of the spigot modules.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecutorOrder {

    /**
     * Order of the executor.
     * If the value is smaller, the
     * executor will be executed first.
     *
     * @return Order of the executor.
     */
    int value();
}
