package com.hakan.injection.database.utils;

import javax.annotation.Nonnull;
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
        return Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                (proxy, method, args) -> function.apply(method, args)
        );
    }



    /**
     * ProxyFunction is the callback function
     * for {@link Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)}.
     */
    public interface ProxyFunction extends BiFunction<Method, Object[], Object> {

    }
}
