package com.hakan.spinjection.database.connection;

import com.hakan.basicdi.reflection.Reflection;
import com.hakan.spinjection.database.annotations.Repository;
import com.hakan.spinjection.database.connection.credential.DbCredential;
import com.hakan.spinjection.database.connection.query.DbQuery;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DbConnection is the connection class to
 * connect to the database and execute queries.
 */
public class DbConnection {

    private final Session session;
    private final Reflection reflections;
    private final DbCredential credential;
    private final Map<String, String> properties;

    /**
     * Constructor of {@link DbConnection}.
     *
     * @param repository repository
     */
    public DbConnection(@Nonnull Repository repository,
                        @Nonnull Reflection reflections) {
        this(DbCredential.of(repository), reflections);
    }

    /**
     * Constructor of {@link DbConnection}.
     *
     * @param url      url
     * @param driver   driver
     * @param username username
     * @param password password
     */
    public DbConnection(@Nonnull String url,
                        @Nonnull String driver,
                        @Nonnull String username,
                        @Nonnull String password,
                        @Nonnull Reflection reflections) {
        this(DbCredential.of(url, driver, username, password), reflections);
    }

    /**
     * Constructor of {@link DbConnection}.
     *
     * @param credential credential
     */
    public DbConnection(@Nonnull DbCredential credential,
                        @Nonnull Reflection reflections) {
        this.credential = credential;
        this.reflections = reflections;
        this.properties = new HashMap<>();
        this.session = this.connect();
    }

    /**
     * Gets the statement.
     *
     * @return statement
     */
    public @Nonnull Session getSession() {
        return this.session;
    }

    /**
     * Gets the credential of
     * the database connection.
     *
     * @return credential
     */
    public @Nonnull DbCredential getCredential() {
        return this.credential;
    }

    /**
     * Gets the properties of
     * the database connection.
     *
     * @return properties
     */
    public @Nonnull Map<String, String> getProperties() {
        return this.properties;
    }

    /**
     * Checks if the connection is connected.
     *
     * @return connected
     */
    public boolean isConnected() {
        return this.session.isConnected();
    }

    /**
     * Sets the properties of
     * the database connection.
     *
     * @param properties properties
     */
    public void setProperties(@Nonnull Map<String, String> properties) {
        this.properties.putAll(properties);
    }



    /**
     * Gets a single result by
     * running the query.
     *
     * @param query query text
     * @return single result
     */
    public synchronized @Nullable Object getSingleResult(@Nonnull String query) {
        return this.getSingleResult(new DbQuery(this.session.createQuery(query)));
    }

    /**
     * Gets a single result by
     * running the query.
     *
     * @param dbQuery dbQuery
     * @return single result
     */
    public synchronized @Nullable Object getSingleResult(@Nonnull DbQuery dbQuery) {
        return dbQuery.getQuery().getSingleResult();
    }

    /**
     * Gets result list by
     * running the query.
     *
     * @param query query text
     * @return result list
     */
    public synchronized @Nonnull List<?> getResultList(@Nonnull String query) {
        return this.getResultList(new DbQuery(this.session.createQuery(query)));
    }

    /**
     * Gets result list by
     * running the query.
     *
     * @param dbQuery dbQuery
     * @return result list
     */
    public synchronized @Nonnull List<?> getResultList(@Nonnull DbQuery dbQuery) {
        return dbQuery.getQuery().getResultList();
    }

    /**
     * Executes the query.
     *
     * @param query query text
     * @return executed
     */
    public synchronized boolean executeUpdate(@Nonnull String query) {
        return this.executeUpdate(new DbQuery(this.session.createQuery(query)));
    }

    /**
     * Executes the query.
     *
     * @param dbQuery dbQuery
     * @return executed
     */
    public synchronized boolean executeUpdate(@Nonnull DbQuery dbQuery) {
        try {
            this.session.beginTransaction();
            dbQuery.getQuery().executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("query couldn't be executed!", e);
        } finally {
            this.session.getTransaction().commit();
        }
    }

    /**
     * Saves the entity to the database.
     *
     * @param entity entity which has saved
     */
    public synchronized @Nonnull Object save(@Nonnull Object entity) {
        try {
            this.session.beginTransaction();
            this.session.persist(entity);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("entity couldn't be saved!", e);
        } finally {
            this.session.getTransaction().commit();
        }
    }

    /**
     * Deletes the entity from the database.
     *
     * @param entity entity which will be deleted
     */
    public synchronized @Nonnull Object delete(@Nonnull Object entity) {
        try {
            this.session.beginTransaction();
            this.session.delete(entity);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("entity couldn't be deleted!", e);
        } finally {
            this.session.getTransaction().commit();
        }
    }

    /**
     * Deletes the entity from the database.
     *
     * @param id entity id which will be deleted
     */
    public synchronized @Nonnull Object deleteById(@Nonnull Class<?> clazz,
                                                   @Nonnull Object id) {
        Object entity = this.session.find(clazz, id);

        if (entity == null)
            throw new IllegalArgumentException("entity with id " + id + " not found!");
        if (!entity.getClass().isAnnotationPresent(Entity.class))
            throw new IllegalArgumentException("entity with id " + id + " is not an entity!");

        return this.delete(entity);
    }



    /**
     * Connects to the database.
     *
     * @return connection
     */
    public @Nonnull Session connect() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.connection.autocommit", "true");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

        configuration.setProperty("hibernate.connection.url", this.credential.url());
        configuration.setProperty("hibernate.connection.username", this.credential.username());
        configuration.setProperty("hibernate.connection.password", this.credential.password());
        configuration.setProperty("hibernate.connection.driver_class", this.credential.driver());


        this.properties.forEach(configuration::setProperty);
        this.reflections.getTypesAnnotatedWith(Entity.class).forEach(configuration::addAnnotatedClass);
        this.reflections.getTypesAnnotatedWith(Embeddable.class).forEach(configuration::addAnnotatedClass);

        return configuration.buildSessionFactory().openSession();
    }
}
