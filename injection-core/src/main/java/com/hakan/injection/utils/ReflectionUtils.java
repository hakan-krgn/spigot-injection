package com.hakan.injection.utils;

import com.hakan.injection.annotations.Scanner;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;

/**
 * ReflectionUtils is a utility class
 * for Reflections library.
 */
@SuppressWarnings({"unchecked"})
public class ReflectionUtils {

    private static final Scanners[] SCANNERS = new Scanners[]{
            Scanners.SubTypes,
            Scanners.TypesAnnotated,
            Scanners.FieldsAnnotated,
            Scanners.MethodsAnnotated,
            Scanners.ConstructorsAnnotated,
    };

    /**
     * Creates a new Reflections instance.
     *
     * @param plugin plugin
     * @return reflections
     */
    public static @Nonnull Reflections createFrom(@Nonnull Plugin plugin) {
        return createFrom(plugin.getClass());
    }

    /**
     * Creates a new Reflections instance.
     *
     * @param instance instance
     * @return reflections
     */
    public static @Nonnull Reflections createFrom(@Nonnull Object instance) {
        return createFrom(instance.getClass());
    }

    /**
     * Creates a new Reflections instance.
     *
     * @param clazz clazz
     * @return reflections
     */
    public static @Nonnull Reflections createFrom(@Nonnull Class<?> clazz) {
        return createFrom(clazz.getAnnotation(Scanner.class));
    }

    /**
     * Creates a new Reflections instance.
     *
     * @param scanner scanner
     * @return reflections
     */
    public static @Nonnull Reflections createFrom(@Nonnull Scanner scanner) {
        return createFrom(scanner.value());
    }

    /**
     * Creates a new Reflections instance.
     *
     * @param path path
     * @return reflections
     */
    public static @Nonnull Reflections createFrom(@Nonnull String path) {
        if (path.isEmpty())
            throw new RuntimeException("scan path cannot be empty!");

        return new Reflections(path, SCANNERS);
    }

    /**
     * Creates a new instance of the class.
     *
     * @param clazz        class
     * @param paramClasses param classes
     * @param params       params
     * @param <T>          type
     * @return instance
     */
    public static @Nonnull <T> T newInstance(@Nonnull Class<?> clazz,
                                             @Nonnull Class<?>[] paramClasses,
                                             @Nonnull Object params) {
        try {
            Constructor<?> constructor = clazz.getConstructor(paramClasses);
            constructor.setAccessible(true);

            return (T) constructor.newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
