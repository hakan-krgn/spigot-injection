package com.hakan.spinjection.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Filter annotation to filter
 * the methods which will be called
 * by spigot injection library.
 * <p>
 * You can use this annotation
 * to cancel the method calls
 * for some specific conditions.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter {

	/**
	 * Expression for filtering.
	 *
	 * @return Expression.
	 */
	String value() default "";
}
