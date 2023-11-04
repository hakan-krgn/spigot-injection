package com.hakan.spinjection.database.executor;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.database.annotations.Query;
import com.hakan.spinjection.database.annotations.Repository;
import com.hakan.spinjection.database.connection.DbConnection;
import com.hakan.spinjection.database.connection.credential.DbCredential;
import com.hakan.spinjection.database.connection.query.DbQuery;
import com.hakan.spinjection.database.connection.result.DbResult;
import com.hakan.spinjection.executor.SpigotExecutor;
import com.hakan.spinjection.utils.ProxyUtils;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * DatabaseExecutor is the executor class
 * for {@link Query} annotation to execute
 * query processes.
 */
public class DatabaseExecutor implements SpigotExecutor {

    private DbConnection dbConnection;
    private final Object instance;
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
        this.instance = ProxyUtils.create(clazz, this::preCall);
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
     * Registers the repository queries
     * and creates the database connection.
     *
     * @param bootstrap injector
     * @param instance  instance
     */
    @Override
    public void execute(@Nonnull SpigotBootstrap bootstrap,
                        @Nonnull Object instance) {
        this.dbConnection = new DbConnection(DbCredential.of(bootstrap, this.repository));

        try {
            Arrays.stream(this.repository.queries())
                    .forEach(query -> this.dbConnection.executeUpdate(query));
        } catch (Exception e) {
            Bukkit.getLogger().warning("Error while executing queries!");
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
                                    @Nullable Object[] args) {
        if (method.getName().equals("toString"))
            return this.clazz.getName() + "@" + Integer.toHexString(this.hashCode());
        if (method.getName().equals("hashCode"))
            return this.hashCode();

        return this.postCall(method, (args != null) ? args : new Object[0]);
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
        DbQuery dbQuery = DbQuery.create(method.getParameters(), args, annotation.value());

        if (method.getReturnType().equals(void.class))
            return this.dbConnection.executeUpdate(dbQuery);
        else if (method.getReturnType().equals(DbResult.class))
            return this.dbConnection.executeQuery(dbQuery);

        throw new RuntimeException("return type must be void or DbResult!");
    }
}
