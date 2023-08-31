package com.hakan.spinjection.database.connection.properties;

import com.hakan.spinjection.SpigotBootstrap;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * DbProperties is the property container class
 * to create a {@link Configuration} for {@link SessionFactory}.
 */
public class DbProperties extends Configuration {

    /**
     * Gets or creates a {@link DbProperties}.
     * <p>
     * If the {@link DbProperties} is not found
     * in the {@link SpigotBootstrap} instance,
     * it will create a new {@link DbProperties}.
     *
     * @param bootstrap spigot bootstrap instance
     * @return properties
     */
    public static @Nonnull DbProperties of(@Nonnull SpigotBootstrap bootstrap) {
        try {
            return bootstrap.getInstance(DbProperties.class);
        } catch (Exception ex) {
            return new DbProperties();
        }
    }



    /**
     * Constructor of {@link DbProperties}.
     */
    public DbProperties() {
        this.set("hibernate.hbm2ddl.auto", "update");
        this.set("hibernate.connection.autocommit", "true");
        this.set("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
    }

    /**
     * Checks if the properties contain the key.
     *
     * @param key key
     * @return contains
     */
    public boolean contains(@Nonnull String key) {
        return super.getProperties().contains(key);
    }

    /**
     * Gets the value of the key.
     *
     * @param key key
     * @return value
     */
    public @Nullable String get(@Nonnull String key) {
        return super.getProperty(key);
    }

    /**
     * Sets the value of the key.
     *
     * @param key   key
     * @param value value
     */
    public void set(@Nonnull String key, @Nonnull String value) {
        super.setProperty(key, value);
    }

    /**
     * Removes the key.
     *
     * @param key key
     */
    public void remove(@Nonnull String key) {
        super.getProperties().remove(key);
    }

    /**
     * Checks if the properties are empty.
     *
     * @return empty
     */
    public boolean isEmpty() {
        return super.getProperties().isEmpty();
    }

    /**
     * Clears the properties.
     */
    public void clear() {
        super.getProperties().clear();
    }
}
