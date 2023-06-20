package com.hakan.injection.database.executor;

import com.hakan.injection.database.annotations.Query;
import com.hakan.injection.executor.SpigotExecutor;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * DatabaseExecutor is the executor class
 * for {@link com.hakan.injection.database.annotations.Query}
 * annotation to execute query processes.
 */
public class DatabaseExecutor implements SpigotExecutor {

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
    public void execute() {
        Proxy.newProxyInstance(
                this.clazz.getClassLoader(),
                new Class[]{this.clazz},
                (proxy, method, args1) -> {
                    if (method.isAnnotationPresent(Query.class))
                        return this.onMethodExecute(method, method.getAnnotation(Query.class));

                    throw new RuntimeException("method is not registered!");
                });
    }

    public Object onMethodExecute(@Nonnull Method method,
                                  @Nonnull Query annotation) {
        String query = annotation.value();
        Class<?> returnType = method.getReturnType();



        return null;
    }
}
