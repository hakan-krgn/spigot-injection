package com.hakan.injection.database.executor;

import com.google.inject.Injector;
import com.hakan.injection.database.annotations.Query;
import com.hakan.injection.database.annotations.Repository;
import com.hakan.injection.database.connection.DbConnection;
import com.hakan.injection.database.connection.query.DbQuery;
import com.hakan.injection.executor.SpigotExecutor;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * DatabaseExecutor is the executor class
 * for {@link Query} annotation to execute
 * query processes.
 */
public class DatabaseExecutor implements SpigotExecutor {

    private Object instance;
    private DbConnection dbConnection;
    private final Class<?> clazz;
    private final Repository repository;

    /**
     * Constructor of {@link DatabaseExecutor}.
     *
     * @param clazz class
     */
    public DatabaseExecutor(@Nonnull Class<?> clazz) {
        this.clazz = clazz;
        this.repository = clazz.getAnnotation(Repository.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Object getInstance() {
        return this.instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull Class<?> getDeclaringClass() {
        return this.clazz;
    }

    /**
     * Gets the repository annotation.
     *
     * @return repository
     */
    public @Nonnull Repository getRepository() {
        return this.repository;
    }

    /**
     * Gets the database connection.
     *
     * @return connection
     */
    public @Nonnull DbConnection getConnection() {
        return this.dbConnection;
    }

    /**
     * Sets the instance of interface.
     *
     * @param instance instance
     */
    public void setInstance(@Nonnull Object instance) {
        this.instance = instance;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@Nonnull Object instance,
                        @Nonnull Injector injector) {
        Reflections reflections = injector.getInstance(Reflections.class);

        try {
            this.dbConnection = new DbConnection(injector.getInstance(this.repository.credential()), reflections);
        } catch (Exception e) {
            this.dbConnection = new DbConnection(this.repository, reflections);
        }

        for (String query : this.repository.queries()) {
            this.dbConnection.executeUpdate(query);
        }
    }

    /**
     * Runs when a method called.
     *
     * @param method method
     * @param args   arguments
     * @return method result
     */
    public @Nullable Object preCall(@Nonnull Method method,
                                    @Nonnull Object[] args) {
        if (method.getName().equals("toString"))
            return this.clazz.getName() + "@" + Integer.toHexString(this.hashCode());
        if (method.getName().equals("hashCode"))
            return this.hashCode();

        if (method.getName().equals("save"))
            return this.dbConnection.save(args[0]);
        if (method.getName().equals("delete"))
            return this.dbConnection.delete(args[0]);
        if (method.getName().equals("deleteById"))
            return this.dbConnection.deleteById(this.repository.entity(), args[0]);
        if (method.getName().equals("findById"))
            return this.dbConnection.getSession().get(this.repository.entity(), args[0]);
        if (method.getName().equals("findAll"))
            return this.dbConnection.getSession().createQuery("from " + this.repository.entity().getSimpleName()).list();

        return this.postCall(method, args);
    }

    /**
     * Executes the method with {@link Query} annotation.
     *
     * @param method method
     * @param args   arguments
     * @return method result
     */
    public @Nullable Object postCall(@Nonnull Method method,
                                     @Nonnull Object[] args) {
        if (!method.isAnnotationPresent(Query.class))
            throw new RuntimeException("method is not registered!");
        if (args.length != method.getParameterCount())
            throw new RuntimeException("argument count must be equal to parameter count!");

        Query annotation = method.getAnnotation(Query.class);
        DbQuery dbQuery = DbQuery.create(method.getParameters(), args,
                this.dbConnection.getSession().createQuery(annotation.value()));

        if (method.getReturnType().equals(void.class))
            return this.dbConnection.executeUpdate(dbQuery);
        else if (method.getReturnType().equals(List.class))
            return this.dbConnection.getResultList(dbQuery);
        else
            return this.dbConnection.getSingleResult(dbQuery);
    }
}
