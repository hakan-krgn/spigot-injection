package com.hakan.spinjection.command.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Parameter annotation to define
 * command parameter in method.
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandParam {

	/**
	 * If the value is not empty, the parameter
	 * must be equal to the value of the annotation.
	 * And if value and parameter are equal, the
	 * method won't be executed.
	 *
	 * @return value
	 */
	@Nonnull
	String value() default "";
}
