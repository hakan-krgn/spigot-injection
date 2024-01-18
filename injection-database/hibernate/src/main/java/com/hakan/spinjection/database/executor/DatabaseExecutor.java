package com.hakan.spinjection.database.executor;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.database.annotations.Query;
import com.hakan.spinjection.database.annotations.Repository;
import com.hakan.spinjection.database.connection.DbConnection;
import com.hakan.spinjection.database.connection.credential.DbCredential;
import com.hakan.spinjection.database.connection.properties.DbProperties;
import com.hakan.spinjection.database.connection.query.DbQuery;
import com.hakan.spinjection.executor.SpigotExecutor;
import com.hakan.spinjection.utils.ProxyUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * DatabaseExecutor is the executor class
 * for {@link Query} annotation to execute
 * query processes.
 */
public class DatabaseExecutor implements SpigotExecutor {

    private DbConnection dbConnection;
    private final Object instance;
    private final Class<?> clazz;
    private final Class<?> idType;
    private final Class<?> entityType;
    private final Repository repository;

    /**
     * Constructor of {@link DatabaseExecutor}.
     *
     * @param clazz class
     */
    public DatabaseExecutor(@Nonnull Class<?> clazz) {
        this.clazz = clazz;
        this.repository = clazz.getAnnotation(Repository.class);
        this.instance = ProxyUtils.create(clazz, this::preCall);
        this.idType = ProxyUtils.getGenericTypes(this.instance)[0];
        this.entityType = ProxyUtils.getGenericTypes(this.instance)[1];
    }

    /**
     * Gets the instance of the class
     * that is annotated with {@link Repository}.
     *
     * @return instance
     */
    @Override
    public @Nonnull Object getInstance() {
        return this.instance;
    }

    /**
     * Gets the class of the instance
     * that is annotated with {@link Repository}.
     *
     * @return class
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
     * Executes the database queries which are
     * saved in {@link Repository#queries()} and
     * creates a new {@link DbConnection} instance.
     *
     * @param bootstrap injector
     * @param instance  instance
     */
    @Override
    public void execute(@Nonnull SpigotBootstrap bootstrap,
                        @Nonnull Object instance) {
        this.dbConnection = new DbConnection(
                DbCredential.of(bootstrap, this.repository),
                DbProperties.of(bootstrap),
                bootstrap.getReflection()
        );


        Session session = this.dbConnection.getSession();
        Transaction transaction = session.getTransaction();

        try {
            transaction.begin();
            Arrays.stream(this.repository.queries())
                    .forEach(query -> session.createNativeQuery(query).executeUpdate());
            transaction.commit();
        } catch (Exception e) {
            if (this.repository.rollbackOnException())
                transaction.rollback();
            else e.printStackTrace();
        }
    }

    /**
     * Runs when an interface
     * method is called.
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

        if (method.getName().equals("save") && args.length == 1)
            return this.dbConnection.save(args[0]);
        if (method.getName().equals("delete") && args.length == 1)
            return this.dbConnection.delete(args[0]);
        if (method.getName().equals("deleteById") && args.length == 1)
            return this.dbConnection.deleteById(this.entityType, args[0]);
        if (method.getName().equals("findById") && args.length == 1)
            return this.dbConnection.getSession().find(this.entityType, args[0]);
        if (method.getName().equals("findAll") && args.length == 0)
            return this.dbConnection.getSession().createQuery("from " + this.entityType.getSimpleName()).list();

        return this.postCall(method, args);
    }

    /**
     * Executes the methods which are
     * annotated with {@link Query} annotation.
     *
     * @param method method
     * @param args   arguments
     * @return method result
     */
    public @Nullable Object postCall(@Nonnull Method method,
                                     @Nonnull Object[] args) {
        if (!method.isAnnotationPresent(Query.class))
            throw new RuntimeException("method must be annotated with @Query!");
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
