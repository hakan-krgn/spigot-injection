package com.hakan.injection.config.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.hakan.injection.utils.ReflectionUtils;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

/**
 * Json utilities for handling json objects
 * and saving them to files.
 */
public final class JsonUtils {

    private static final Gson GSON = new Gson();
    private static final JsonParser PARSER = new JsonParser();

    /**
     * Gets JsonObject from file.
     *
     * @param filePath path to file
     * @return JsonObject from file
     */
    @SneakyThrows
    public static @Nonnull JsonObject loadFromFile(@Nonnull String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonReader jsonReader = new JsonReader(reader);
            Map<?, ?> objectMap = GSON.fromJson(jsonReader, Map.class);
            return PARSER.parse(GSON.toJson(objectMap)).getAsJsonObject();
        }
    }

    /**
     * Gets JsonObject from file.
     *
     * @param object     JsonObject to get
     * @param filePath   path to file
     * @param beautified beautified json
     */
    @SneakyThrows
    public static void saveToFile(@Nonnull JsonObject object,
                                  @Nonnull String filePath,
                                  boolean beautified) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            if (!beautified) fileWriter.write(object.toString());
            else fileWriter.write(JsonUtils.beautify(object.toString()));
        }
    }

    /**
     * Saves JsonObject to file.
     *
     * @param object   JsonObject to save
     * @param filePath path to file
     */
    public static void saveToFile(@Nonnull JsonObject object,
                                  @Nonnull String filePath) {
        JsonUtils.saveToFile(object, filePath, true);
    }

    /**
     * Gets the element from the parent
     * json object by given path.
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
        if (element instanceof JsonObject || element instanceof JsonArray)
            return element;
        else if (element instanceof JsonNull || element == null)
            return null;
        return ReflectionUtils.getValue(element, "value");
    }

    /**
     * Sets element to parent json.
     *
     * @param parent  parent json object
     * @param key     key to set
     * @param element element to set
     */
    public static void setValue(@Nonnull JsonObject parent,
                                @Nonnull String key,
                                @Nonnull JsonElement element) {
        String[] keys = key.split("\\.");
        JsonObject jsonObject = parent;
        for (int i = 0; i < keys.length - 1; i++)
            jsonObject = jsonObject.getAsJsonObject(keys[i]);
        jsonObject.add(keys[keys.length - 1], element);
    }

    /**
     * Beautifies the given json string.
     *
     * @param input input json string
     * @return beautified json string
     * @see <a href="https://stackoverflow.com/questions/5457524/json-beautifier-library-for-java">Json beautifier method</a>
     */
    public static @Nonnull String beautify(@Nonnull String input) {
        StringBuilder inputBuilder = new StringBuilder();
        char[] inputChar = input.toCharArray();

        int tabCount = 0;
        for (int i = 0; i < inputChar.length; i++) {
            String charI = String.valueOf(inputChar[i]);
            if (charI.equals("}") || charI.equals("]")) {
                tabCount--;
                if (!String.valueOf(inputChar[i - 1]).equals("[") && !String.valueOf(inputChar[i - 1]).equals("{"))
                    inputBuilder.append(newLine(tabCount));
            }
            inputBuilder.append(charI);

            if (charI.equals("{") || charI.equals("[")) {
                tabCount++;
                if (String.valueOf(inputChar[i + 1]).equals("]") || String.valueOf(inputChar[i + 1]).equals("}"))
                    continue;

                inputBuilder.append(newLine(tabCount));
            }

            if (charI.equals(","))
                inputBuilder.append(newLine(tabCount));
        }

        return inputBuilder.toString();
    }



    /**
     * New line with tab count.
     *
     * @param tabCount tab count
     * @return new line
     */
    private static @Nonnull String newLine(int tabCount) {
        StringBuilder builder = new StringBuilder();

        builder.append("\n");
        for (int j = 0; j < tabCount; j++)
            builder.append("  ");
        return builder.toString();
    }
}