package com.hakan.injection.database.connection.credential;

import com.hakan.injection.database.annotations.Repository;

import javax.annotation.Nonnull;

/**
 * DbCredential is the credential class
 * to connect to the database.
 */
public class DbCredential {

    /**
     * Creates a new {@link DbCredential}.
     *
     * @param repository repository annotation
     * @return credential
     */
    public static @Nonnull DbCredential of(@Nonnull Repository repository) {
        return of(
                repository.url(),
                repository.driver(),
                repository.username(),
                repository.password()
        );
    }

    /**
     * Creates a new {@link DbCredential}.
     *
     * @param url      url
     * @param driver   driver
     * @param username username
     * @param password password
     * @return credential
     */
    public static @Nonnull DbCredential of(@Nonnull String url,
                                           @Nonnull String driver,
                                           @Nonnull String username,
                                           @Nonnull String password) {
        return new DbCredential(
                url,
                driver,
                username,
                password
        );
    }



    private final String url;
    private final String driver;
    private final String username;
    private final String password;

    /**
     * Constructor of {@link DbCredential}.
     *
     * @param url      url
     * @param driver   driver
     * @param username username
     * @param password password
     */
    public DbCredential(@Nonnull String url,
                        @Nonnull String driver,
                        @Nonnull String username,
                        @Nonnull String password) {
        this.url = url;
        this.driver = driver;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the url.
     *
     * @return url
     */
    public @Nonnull String url() {
        return this.url;
    }

    /**
     * Gets the driver.
     *
     * @return driver
     */
    public @Nonnull String driver() {
        return this.driver;
    }

    /**
     * Gets the username.
     *
     * @return username
     */
    public @Nonnull String username() {
        return this.username;
    }

    /**
     * Gets the password.
     *
     * @return password
     */
    public @Nonnull String password() {
        return this.password;
    }
}
