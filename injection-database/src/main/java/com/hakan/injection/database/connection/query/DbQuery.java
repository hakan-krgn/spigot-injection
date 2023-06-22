package com.hakan.injection.database.connection.query;

import com.hakan.injection.database.annotations.QueryParam;
import com.hakan.injection.utils.ReflectionUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Parameter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * QueryString is a utility class
 * for replacing and editing queries.
 */
public class DbQuery {

    private static final Pattern PATTERN = Pattern.compile("(?<=:)([a-zA-Z0-9.])+");

    /**
     * Creates query from arguments,
     * parameters and base query text.
     * <p>
     * Replaces base query text with arguments
     * and parameters.
     *
     * @param parameters parameters
     * @param values     arguments values
     * @param queryText  query text
     * @return DbQuery instance
     */
    public static @Nonnull DbQuery create(@Nonnull Parameter[] parameters,
                                          @Nonnull Object[] values,
                                          @Nonnull String queryText) {
        String[] keys = new String[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            QueryParam queryParam = parameters[i].getAnnotation(QueryParam.class);

            if (queryParam == null)
                throw new RuntimeException("parameter must be annotated with @QueryParam!");
            if (queryParam.value().isEmpty())
                throw new RuntimeException("parameter value cannot be empty!");

            keys[i] = queryParam.value();
        }

        return create(keys, values, queryText);
    }

    /**
     * Creates query from arguments,
     * parameters and base query text.
     * <p>
     * Replaces base query text with arguments
     * and parameters.
     *
     * @param keys      parameters keys
     * @param values    arguments values
     * @param queryText query text
     * @return DbQuery instance
     */
    public static @Nonnull DbQuery create(@Nonnull String[] keys,
                                          @Nonnull Object[] values,
                                          @Nonnull String queryText) {
        DbQuery query = new DbQuery(queryText);

        for (int i = 0; i < keys.length; i++) {
            String paramKey = keys[i];
            Object paramValue = values[i];

            query.replace(paramKey, paramValue);
        }

        return query;
    }



    private String query;

    /**
     * Constructor of {@link DbQuery}.
     *
     * @param query query
     */
    public DbQuery(@Nonnull String query) {
        this.query = query;
    }

    /**
     * Gets the query.
     *
     * @return query
     */
    public @Nonnull String getQuery() {
        return this.query;
    }

    /**
     * Replaces the key with the value
     * from the entity instance.
     *
     * @param paramKey   parameter key
     * @param paramValue parameter value
     */
    public void replace(@Nonnull String paramKey,
                        @Nonnull Object paramValue) {
        Matcher matcher = PATTERN.matcher(this.query);

        while (matcher.find()) {
            String key = matcher.group();
            String[] keys = key.split("\\.");

            if (keys.length == 0 || !keys[0].equals(paramKey))
                continue;

            Object instance = paramValue;
            for (int i = 1; i < keys.length; i++)
                instance = ReflectionUtils.getValue(instance, keys[i]);
            this.query = this.query.replace(":" + key, instance.toString());
        }
    }
}
