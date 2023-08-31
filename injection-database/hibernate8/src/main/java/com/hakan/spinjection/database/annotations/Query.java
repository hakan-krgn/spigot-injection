package com.hakan.spinjection.database.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Query annotation is used to
 * mark the method as a query
 * method.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

	/**
	 * HQL query to execute.
	 *
	 * @return query
	 */
	@Nonnull
	String value();
}
