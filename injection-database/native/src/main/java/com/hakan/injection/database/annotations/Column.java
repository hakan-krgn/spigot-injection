package com.hakan.injection.database.annotations;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Column annotation is used to
 * specify the column name in the database.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * Column name in the database.
     *
     * @return column name
     */
    @Nonnull
    String value();
}
