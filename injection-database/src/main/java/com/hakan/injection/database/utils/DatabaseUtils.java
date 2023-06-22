package com.hakan.injection.database.utils;

import com.hakan.injection.database.annotations.Table;
import com.hakan.injection.database.connection.result.DbResult;
import com.hakan.injection.utils.ReflectionUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.BiFunction;

/**
 * DatabaseUtils is the utility class
 * for database processes.
 */
public class DatabaseUtils {

    /**
     * Creates a proxy instance from
     * the given interface.
     *
     * @param clazz    interface
     * @param function function
     * @return proxy instance
     */
    public static @Nonnull Object createProxy(@Nonnull Class<?> clazz,
                                              @Nonnull ProxyFunction function) {
        Class<?>[] interfaces = new Class[]{clazz};
        ClassLoader classLoader = clazz.getClassLoader();
        return Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> function.apply(method, args));
    }

    /**
     * Creates an instance from the given
     * {@link DbResult} and {@link Class}.
     *
     * @param dbResult result
     * @param clazz    class
     * @param <T>      type
     * @return instance
     */
    public static @Nonnull <T> T create(@Nonnull DbResult dbResult,
                                        @Nonnull Class<T> clazz) {
        T instance = ReflectionUtils.newInstance(clazz, new Class[]{}, new Object[]{});
        String table = clazz.getAnnotation(Table.class).value();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            if (field.getType().isPrimitive() || field.getType().equals(String.class)) {
                ReflectionUtils.setValue(instance, field.getName(), dbResult.getObject(table + "." + field.getName()));
                continue;
            }

            ReflectionUtils.setValue(instance, field.getName(), create(dbResult, field.getType()));
        }

        return instance;
    }



    /**
     * ProxyFunction is the callback function
     * for {@link Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)}.
     */
    public interface ProxyFunction extends BiFunction<Method, Object[], Object> {

    }
}
