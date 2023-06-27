package com.hakan.spinjection.config.utils;

import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.util.function.BiFunction;

/**
 * Config utilities for creating
 * proxy instances.
 */
public class ConfigUtils {

    /**
     * Creates a file
     * at the given path.
     *
     * @param path path to create
     * @return created file
     */
    @SneakyThrows
    public static @Nonnull File createFile(@Nonnull String path) {
        File file = new File(path.replace("/", File.separator));
        file.getParentFile().mkdirs();
        file.createNewFile();
        return file;
    }

    /**
     * Creates a file with the given
     * path, resource and loader.
     *
     * @param path     path to create
     * @param resource resource to include
     * @param loader   class loader
     * @return created file
     */
    @SneakyThrows
    public static @Nonnull File createFile(@Nonnull String path,
                                           @Nonnull String resource,
                                           @Nonnull Class<?> loader) {
        File file = new File(path);
        if (file.exists()) return file;

        File created = ConfigUtils.createFile(path);
        if (resource.isEmpty()) return file;

        try (InputStream inputStream = loader.getResourceAsStream("/" + resource);
             OutputStream outputStream = Files.newOutputStream(created.toPath())) {
            int readBytes;
            byte[] buffer = new byte[4096];
            while ((readBytes = inputStream.read(buffer)) > 0)
                outputStream.write(buffer, 0, readBytes);
        }

        return file;
    }

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
