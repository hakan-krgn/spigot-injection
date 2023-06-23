package com.hakan.injection.database.connection.query;

import com.hakan.injection.database.annotations.QueryParam;
import org.hibernate.query.Query;

import javax.annotation.Nonnull;
import java.lang.reflect.Parameter;

/**
 * QueryString is a utility class
 * for replacing and editing queries.
 */
public class DbQuery {

    /**
     * Creates the query from arguments,
     * parameters and base query text.
     * <p>
     * Replaces base query text with arguments
     * and parameters.
     *
     * @param parameters parameters
     * @param values     arguments values
     * @param query      query
     * @return DbQuery instance
     */
    public static @Nonnull DbQuery create(@Nonnull Parameter[] parameters,
                                          @Nonnull Object[] values,
                                          @Nonnull Query<?> query) {
        DbQuery dbQuery = new DbQuery(query);

        for (int i = 0; i < parameters.length; i++) {
            QueryParam queryParam = parameters[i].getAnnotation(QueryParam.class);

            if (queryParam == null)
                throw new RuntimeException("parameter must be annotated with @QueryParam!");
            if (queryParam.value().isEmpty())
                throw new RuntimeException("parameter value cannot be empty!");

            dbQuery.replace(queryParam.value(), values[i]);
        }

        return dbQuery;
    }



    private final Query<?> query;

    /**
     * Constructor of {@link DbQuery}.
     *
     * @param query query
     */
    public DbQuery(@Nonnull Query<?> query) {
        this.query = query;
    }

    /**
     * Gets the query.
     *
     * @return query
     */
    public @Nonnull Query<?> getQuery() {
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
        this.query.setParameter(paramKey, paramValue);
    }
}
