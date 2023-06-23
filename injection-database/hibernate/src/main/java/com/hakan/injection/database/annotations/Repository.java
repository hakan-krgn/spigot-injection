package com.hakan.injection.database.annotations;

import com.hakan.injection.database.connection.credential.DbCredential;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Repository annotation is used to
 * mark the interface as a repository.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {

    /**
     * Database url to connect.
     * Default value is "jdbc:mysql://localhost:3306".
     *
     * @return url
     */
    @Nonnull
    String url() default "jdbc:mysql://localhost:3306";

    /**
     * Database driver.
     *
     * @return driver
     */
    @Nonnull
    String driver() default "com.mysql.cj.jdbc.Driver";

    /**
     * Username to connect to the database.
     *
     * @return username
     */
    @Nonnull
    String username() default "root";

    /**
     * Password to connect to the database.
     *
     * @return password
     */
    @Nonnull
    String password() default "";


    /**
     * Queries to execute on startup.
     *
     * @return queries
     */
    @Nonnull
    String[] queries() default {};

    /**
     * Properties to set on creation.
     *
     * @return properties
     */
    @Nonnull
    String[] properties() default {};


    /**
     * ID class of the repository.
     *
     * @return id class
     */
    @Nonnull
    Class<?> id();

    /**
     * Main entity class of the repository.
     *
     * @return entity class
     */
    @Nonnull
    Class<?> entity();


    /**
     * Credential class to connect to the database.
     *
     * @return credential
     */
    @Nonnull
    Class<? extends DbCredential> credential() default DbCredential.class;
}
