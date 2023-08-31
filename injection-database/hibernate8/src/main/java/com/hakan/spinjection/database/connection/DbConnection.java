package com.hakan.spinjection.database.connection;

import com.hakan.basicdi.reflection.Reflection;
import com.hakan.spinjection.database.annotations.Repository;
import com.hakan.spinjection.database.connection.credential.DbCredential;
import com.hakan.spinjection.database.connection.properties.DbProperties;
import com.hakan.spinjection.database.connection.query.DbQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.List;

/**
 * DbConnection is the connection class to
 * connect to the database and execute queries.
 */
public class DbConnection {

	private final Session session;
	private final SessionFactory factory;
	private final DbCredential credential;
	private final DbProperties properties;
	private final Reflection reflections;

	/**
	 * Constructor of {@link DbConnection}.
	 *
	 * @param repository repository
	 */
	public DbConnection(@Nonnull Repository repository,
						@Nonnull DbProperties properties,
						@Nonnull Reflection reflections) {
		this(DbCredential.of(repository), properties, reflections);
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
						@Nonnull DbProperties properties,
						@Nonnull Reflection reflections) {
		this(DbCredential.of(url, driver, username, password), properties, reflections);
	}

	/**
	 * Constructor of {@link DbConnection}.
	 *
	 * @param credential credential
	 */
	public DbConnection(@Nonnull DbCredential credential,
						@Nonnull DbProperties properties,
						@Nonnull Reflection reflections) {
		this.credential = credential;
		this.properties = properties;
		this.reflections = reflections;
		this.factory = this.buildFactory();
		this.session = this.openSession();
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
	public @Nonnull DbProperties getProperties() {
		return this.properties;
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
			this.session.getTransaction().begin();
			dbQuery.getQuery().executeUpdate();
			this.session.getTransaction().commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("query couldn't be executed!", e);
		}
	}

	/**
	 * Saves the entity to the database.
	 *
	 * @param entity entity which has saved
	 */
	public synchronized @Nonnull Object save(@Nonnull Object entity) {
		try {
			this.session.getTransaction().begin();
			this.session.saveOrUpdate(entity);
			this.session.getTransaction().commit();

			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("entity couldn't be saved!", e);
		}
	}

	/**
	 * Deletes the entity from the database.
	 *
	 * @param entity entity which will be deleted
	 */
	public synchronized @Nonnull Object delete(@Nonnull Object entity) {
		try {
			this.session.getTransaction().begin();
			this.session.delete(entity);
			this.session.getTransaction().commit();

			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("entity couldn't be deleted!", e);
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
	 * Creates a new session factory
	 * from given properties.
	 *
	 * @return session factory
	 */
	public @Nonnull SessionFactory buildFactory() {
		this.properties.set("hibernate.connection.url", this.credential.url());
		this.properties.set("hibernate.connection.username", this.credential.username());
		this.properties.set("hibernate.connection.password", this.credential.password());
		this.properties.set("hibernate.connection.driver_class", this.credential.driver());

		this.reflections.getTypesAnnotatedWith(Entity.class)
			.forEach(this.properties::addAnnotatedClass);
		this.reflections.getTypesAnnotatedWith(Embeddable.class)
			.forEach(this.properties::addAnnotatedClass);

		return this.properties.buildSessionFactory();
	}

	/**
	 * Opens a new hibernate session.
	 *
	 * @return hibernate session
	 */
	public @Nonnull Session openSession() {
		return this.factory.openSession();
	}
}
