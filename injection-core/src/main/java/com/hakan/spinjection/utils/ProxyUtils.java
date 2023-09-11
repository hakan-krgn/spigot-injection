package com.hakan.spinjection.utils;

import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ProxyUtils is the utility class
 * for creating interfaces.
 */
public class ProxyUtils {

    private static final Pattern PATTERN = Pattern.compile("(?<=<)([\\w., ]+)(?=>)");

    /**
     * Creates a proxy instance from
     * the given interface.
     *
     * @param clazz    interface
     * @param function function
     * @return proxy instance
     */
    public static @Nonnull Object create(@Nonnull Class<?> clazz,
                                         @Nonnull ProxyFunction function) {
        return Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                (proxy, method, args) -> function.apply(method, (args == null) ? new Object[0] : args)
        );
    }

    /**
     * Gets generic types from the interface
     * which is inherited by a generic class.
     *
     * @param clazz class with generic types
     * @return generic types
     */
    @SneakyThrows
    public static @Nonnull Class<?>[] getGenericTypes(@Nonnull Class<?> clazz) {
        String typeName = clazz
                .getInterfaces()[0]
                .getGenericInterfaces()[0]
                .getTypeName();

        Matcher matcher = PATTERN.matcher(typeName);

        String[] classes = matcher.find() ? matcher.group().split(", ") : new String[0];

        Class<?>[] types = new Class[classes.length];
        for (String _clazz : classes) {
            types[types.length - 1] = Class.forName(_clazz);
        }
        return types;
    }



    /**
     * ProxyFunction is the callback function
     * for {@link Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)}.
     */
    public interface ProxyFunction extends BiFunction<Method, Object[], Object> {

    }
}
