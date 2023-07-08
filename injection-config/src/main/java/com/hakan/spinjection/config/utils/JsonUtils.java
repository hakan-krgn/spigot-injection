package com.hakan.spinjection.config.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.hakan.spinjection.utils.ReflectionUtils;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Json utilities for handling json objects
 * and saving them to files.
 */
public class JsonUtils {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Gets JsonObject from file.
     *
     * @param filePath path to file
     * @return JsonObject from file
     */
    @SneakyThrows
    public static @Nonnull JsonObject loadFromFile(@Nonnull String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return GSON.fromJson(reader, JsonObject.class);
        }
    }

    /**
     * Gets JsonObject from file.
     *
     * @param object   JsonObject to get
     * @param filePath path to file
     */
    @SneakyThrows
    public static void saveToFile(@Nonnull JsonObject object,
                                  @Nonnull String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            GSON.toJson(object, fileWriter);
        }
    }

    /**
     * Gets the element from the parent
     * json object by given key.
     *
     * @param parent parent json object
     * @param key    key to get
     * @return element
     */
    public static @Nullable Object getValue(@Nonnull JsonObject parent,
                                            @Nonnull String key) {
        String[] keys = key.split("\\.");
        JsonObject jsonObject = parent;
        for (int i = 0; i < keys.length - 1; i++)
            jsonObject = jsonObject.getAsJsonObject(keys[i]);

        JsonElement element = jsonObject.get(keys[keys.length - 1]);

        if (element == null || element instanceof JsonNull)
            return null;
        if (element instanceof JsonObject || element instanceof JsonArray)
            return element;

        return ReflectionUtils.getValue(element, "value");
    }

    /**
     * Sets element to parent json.
     *
     * @param parent parent json object
     * @param key    key to set
     * @param value  value to set
     */
    public static void setValue(@Nonnull JsonObject parent,
                                @Nonnull String key,
                                @Nonnull Object value) {
        String[] keys = key.split("\\.");
        JsonObject jsonObject = parent;
        for (int i = 0; i < keys.length - 1; i++)
            jsonObject = jsonObject.getAsJsonObject(keys[i]);

        jsonObject.add(keys[keys.length - 1], GSON.toJsonTree(value));
    }
}
