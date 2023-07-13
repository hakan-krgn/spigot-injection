package com.hakan.spinjection.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Async annotation is used to define
 * the method is asynchronous or not.
 * <p>
 * If the method is specified with
 * Async annotation, the method will
 * be executed asynchronously.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Async {

}