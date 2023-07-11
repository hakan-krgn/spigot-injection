package com.hakan.spinjection.config.utils;

import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * File utilities for creating
 * files and include resources
 * to the files.
 */
public class FileUtils {

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
     * @return created file
     */
    @SneakyThrows
    public static @Nonnull File createFile(@Nonnull String path,
                                           @Nonnull String resource) {
        return createFile(path, resource, FileUtils.class);
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

        File created = createFile(path);
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
}
