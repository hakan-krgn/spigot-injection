package com.hakan.injection.config.utils;

import com.hakan.injection.config.annotations.ConfigFile;
import com.hakan.injection.config.container.Container;
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
public final class ConfigUtils {

    /**
     * Creates a file.
     *
     * @param path Path to create.
     * @return Created file.
     */
    @SneakyThrows
    public static @Nonnull File createFile(@Nonnull String path) {
        File file = new File(path.replace("/", File.separator));
        file.getParentFile().mkdirs();
        file.createNewFile();
        return file;
    }


    /**
     * Creates a file.
     *
     * @param container Container to create.
     * @return Created file.
     */
    public static @Nonnull File createFile(@Nonnull Container container,
                                           @Nonnull Class<?> loader) {
        return ConfigUtils.createFile(container.getPath(), container.getResource(), loader);
    }

    /**
     * Creates a file.
     *
     * @param file File to create.
     * @return Created file.
     */
    public static @Nonnull File createFile(@Nonnull ConfigFile file,
                                           @Nonnull Class<?> loader) {
        return ConfigUtils.createFile(file.path(), file.resource(), loader);
    }

    /**
     * Creates a file.
     *
     * @param path     Path to create.
     * @param resource Resource to include.
     * @param loader   Class loader.
     * @return Created file.
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
