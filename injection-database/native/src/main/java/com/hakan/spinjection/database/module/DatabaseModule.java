package com.hakan.spinjection.database.module;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.annotations.ExecutorOrder;
import com.hakan.spinjection.database.annotations.Repository;
import com.hakan.spinjection.database.executor.DatabaseExecutor;
import com.hakan.spinjection.executor.SpigotExecutor;
import com.hakan.spinjection.module.SpigotModule;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * DatabaseModule registers database
 * executors to listen interfaces and execute
 * database queries.
 */
@ExecutorOrder(2)
@SuppressWarnings({"rawtypes"})
public class DatabaseModule extends SpigotModule<Class, Repository> {

    /**
     * Constructor of DatabaseModule.
     *
     * @param bootstrap bootstrap
     */
    public DatabaseModule(@Nonnull SpigotBootstrap bootstrap) {
        super(bootstrap, Class.class, Repository.class);
    }

    /**
     * Loads the classes that are annotated with {@link Repository}.
     * Creates a new {@link DatabaseExecutor} for each class
     * and adds to the {@link SpigotModule#executors}.
     *
     * @param classes classes that are annotated with {@link Repository}
     */
    @Override
    public void load(@Nonnull Set<Class> classes) {
        for (Class clazz : classes) {
            if (!clazz.isInterface())
                throw new RuntimeException("repository class must be interface!");


            DatabaseExecutor databaseExecutor = new DatabaseExecutor(clazz);

            super.bind(clazz).withInstance(databaseExecutor.getInstance());
            super.executors.add(databaseExecutor);
        }
    }

    /**
     * Executes all configuration executors that are
     * saved in {@link #executors}.
     * <p>
     * Then it runs execute method of each executor.
     */
    @Override
    public void execute() {
        for (SpigotExecutor executor : super.executors) {
            DatabaseExecutor databaseExecutor = (DatabaseExecutor) executor;
            databaseExecutor.execute(super.bootstrap, databaseExecutor.getInstance());
        }
    }
}
