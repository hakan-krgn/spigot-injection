package com.hakan.spinjection.database.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * QueryParam annotation is used to
 * mark the method parameter as a
 * query parameter and use it in
 * the query dynamically.
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParam {

	/**
	 * Query parameter name.
	 *
	 * @return parameter name
	 */
	@Nonnull
	String value();
}
