package com.hakan.injection.utils;

import com.hakan.injection.scanner.annotations.Scanner;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import javax.annotation.Nonnull;

/**
 * ReflectionUtils is a utility class
 * for Reflections library.
 */
public class ReflectionUtils {

    private static final Scanners[] SCANNERS = new Scanners[]{
            Scanners.ConstructorsAnnotated,
            Scanners.TypesAnnotated,
            Scanners.FieldsAnnotated,
            Scanners.MethodsAnnotated,
    };

    /**
     * Creates a new Reflections instance.
     *
     * @param plugin plugin
     * @return reflections
     */
    public static @Nonnull Reflections createFrom(@Nonnull Plugin plugin) {
        Scanner scanner = plugin.getClass().getAnnotation(Scanner.class);

        if (scanner == null)
            throw new RuntimeException("scan annotation not found in the main class!");
        if (scanner.value().isEmpty())
            throw new RuntimeException("scan annotation value cannot be empty!");

        return new Reflections(scanner.value(), SCANNERS);
    }
}
