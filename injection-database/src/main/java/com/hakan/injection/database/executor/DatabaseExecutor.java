package com.hakan.injection.database.executor;

import com.hakan.injection.database.annotations.Query;
import com.hakan.injection.executor.SpigotExecutor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * DatabaseExecutor is the executor class
 * for {@link Query} annotation to execute
 * query processes.
 */
public class DatabaseExecutor implements SpigotExecutor {

    private Object instance;
    private final Class<?> clazz;

    /**
     * Constructor of {@link DatabaseExecutor}.
     *
     * @param clazz class
     */
    public DatabaseExecutor(@Nonnull Class<?> clazz) {
        this.clazz = clazz;
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
     * {@inheritDoc}
     */
    @Override
    public void execute(@Nonnull Object instance) {
        this.instance = instance;
    }

    /**
     * Executes the method with {@link Query} annotation.
     *
     * @param method     method
     * @param annotation annotation
     * @return method result
     */
    public Object onMethodExecute(@Nonnull Method method,
                                  @Nonnull Query annotation) {
        String query = annotation.value();
        Class<?> returnType = method.getReturnType();

        System.out.println("query: " + query);
        System.out.println("returnType: " + returnType);

        return null;
    }
}
