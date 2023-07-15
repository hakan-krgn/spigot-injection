package com.hakan.spinjection.utils;

import com.hakan.basicdi.reflection.Reflection;
import com.hakan.spinjection.annotations.Async;
import com.hakan.spinjection.annotations.Scanner;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ReflectionUtils is a utility class
 * for Reflections library.
 */
@SuppressWarnings({"unchecked"})
public class ReflectionUtils {

    /**
     * Creates a new Reflections instance.
     *
     * @param plugin plugin
     * @return reflections
     */
    public static @Nonnull Reflection createFrom(@Nonnull Plugin plugin) {
        return createFrom(plugin.getClass());
    }

    /**
     * Creates a new Reflections instance.
     *
     * @param instance instance
     * @return reflections
     */
    public static @Nonnull Reflection createFrom(@Nonnull Object instance) {
        return createFrom(instance.getClass());
    }

    /**
     * Creates a new Reflections instance.
     *
     * @param clazz clazz
     * @return reflections
     */
    public static @Nonnull Reflection createFrom(@Nonnull Class<?> clazz) {
        return createFrom(clazz.getAnnotation(Scanner.class));
    }

    /**
     * Creates a new Reflections instance.
     *
     * @param scanner scanner
     * @return reflections
     */
    public static @Nonnull Reflection createFrom(@Nonnull Scanner scanner) {
        return createFrom(scanner.value());
    }

    /**
     * Creates a new Reflections instance.
     *
     * @param path path
     * @return reflections
     */
    public static @Nonnull Reflection createFrom(@Nonnull String path) {
        if (path.isEmpty())
            throw new RuntimeException("scan path cannot be empty!");

        return new Reflection(path);
    }

    /**
     * Gets field value from the instance.
     *
     * @param instance instance
     * @param field    field
     * @param <T>      type
     * @return value
     */
    @SneakyThrows
    public static @Nonnull <T> T getValue(@Nonnull Object instance,
                                          @Nonnull String field) {
        Field declaredField = instance.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);

        return (T) declaredField.get(instance);
    }

    /**
     * Invokes method from the instance.
     *
     * @param instance instance
     * @param args     arguments
     */
    @SneakyThrows
    public static void invokeMethod(@Nonnull Object instance,
                                    @Nonnull Method method,
                                    @Nonnull Object... args) {
        method.setAccessible(true);
        method.invoke(instance, args);
    }

    /**
     * Runs method from the instance
     * asynchronously if the method is
     * specified with Async annotation.
     *
     * @param plugin   plugin
     * @param instance instance
     * @param method   method
     * @param args     arguments
     */
    @SneakyThrows
    public static void runMethod(@Nonnull Plugin plugin,
                                 @Nonnull Object instance,
                                 @Nonnull Method method,
                                 @Nonnull Object... args) {
        if (method.getParameterCount() != args.length)
            throw new RuntimeException("method parameter count and args length not equal!");

        if (!method.isAnnotationPresent(Async.class))
            invokeMethod(instance, method, args);
        else Bukkit.getScheduler().runTaskAsynchronously(plugin,
                () -> invokeMethod(instance, method, args));
    }
}
