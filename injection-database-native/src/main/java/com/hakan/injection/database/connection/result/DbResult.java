package com.hakan.injection.database.connection.result;

import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.function.Supplier;

/**
 * DbResult is the result set class
 * to get the results of the query.
 */
public class DbResult {

    /**
     * Creates a new {@link DbResult}.
     *
     * @param resultSet result set
     * @return {@link DbResult}
     */
    public static @Nonnull DbResult of(@Nonnull ResultSet resultSet) {
        return new DbResult(resultSet);
    }



    private final ResultSet resultSet;

    /**
     * Constructor of {@link DbResult}.
     *
     * @param resultSet result set
     */
    public DbResult(@Nonnull ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * Gets the result set.
     *
     * @return result set
     */
    public @Nonnull ResultSet getResultSet() {
        return this.resultSet;
    }

    /**
     * Moves the cursor forward one
     * row from its current position.
     *
     * @return true if the new current row is valid;
     * false if there are no more rows
     */
    @SneakyThrows
    public boolean next() {
        return this.resultSet.next();
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link String} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public String getString(@Nonnull String column) {
        return this.resultSet.getString(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as an {@link Integer} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public int getInt(@Nonnull String column) {
        return this.resultSet.getInt(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link Long} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public long getLong(@Nonnull String column) {
        return this.resultSet.getLong(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link Double} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public double getDouble(@Nonnull String column) {
        return this.resultSet.getDouble(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link Boolean} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public boolean getBoolean(@Nonnull String column) {
        return this.resultSet.getBoolean(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link Byte} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public byte getByte(@Nonnull String column) {
        return this.resultSet.getByte(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link Short} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public short getShort(@Nonnull String column) {
        return this.resultSet.getShort(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link Float} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public float getFloat(@Nonnull String column) {
        return this.resultSet.getFloat(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link BigDecimal} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public BigDecimal getBigDecimal(@Nonnull String column) {
        return this.resultSet.getBigDecimal(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as an {@link InputStream} object in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public Ref getRef(@Nonnull String column) {
        return this.resultSet.getRef(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as an {@link InputStream} object in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public Blob getBlob(@Nonnull String column) {
        return this.resultSet.getBlob(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as an {@link InputStream} object in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public Clob getClob(@Nonnull String column) {
        return this.resultSet.getClob(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as an {@link Array} object in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public Array getArray(@Nonnull String column) {
        return this.resultSet.getArray(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as an {@link InputStream} object in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public URL getURL(@Nonnull String column) {
        return this.resultSet.getURL(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link byte} array in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public byte[] getBytes(@Nonnull String column) {
        return this.resultSet.getBytes(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link Date} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public Date getDate(@Nonnull String column) {
        return this.resultSet.getDate(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link Time} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public Time getTime(@Nonnull String column) {
        return this.resultSet.getTime(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link Timestamp} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public Timestamp getTimestamp(@Nonnull String column) {
        return this.resultSet.getTimestamp(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link InputStream} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public InputStream getBinaryStream(@Nonnull String column) {
        return this.resultSet.getBinaryStream(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link InputStream} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public InputStream getAsciiStream(@Nonnull String column) {
        return this.resultSet.getAsciiStream(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as an {@link Object} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public Object getObject(@Nonnull String column) {
        return this.resultSet.getObject(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as a {@link Reader} in the Java programming language.
     *
     * @param column column
     * @return value of the designated column
     */
    @SneakyThrows
    public Reader getCharacterStream(@Nonnull String column) {
        return this.resultSet.getCharacterStream(column);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as an {@link Object} in the Java programming language.
     *
     * @param column column
     * @param type   type
     * @return value of the designated column
     */
    @SneakyThrows
    public <T> T getObject(@Nonnull String column,
                           @Nonnull Class<T> type) {
        return type.cast(this.resultSet.getObject(column));
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as an {@link Object} in the Java programming language.
     *
     * @param column column
     * @param type   type
     * @return value of the designated column
     */
    @SneakyThrows
    public <T> T getObject(@Nonnull String column,
                           @Nonnull Class<T> type,
                           @Nonnull T defaultValue) {
        Object object = this.resultSet.getObject(column);
        return object == null ? defaultValue : type.cast(object);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as an {@link Object} in the Java programming language.
     *
     * @param column column
     * @param type   type
     * @return value of the designated column
     */
    @SneakyThrows
    public <T> T getObject(@Nonnull String column,
                           @Nonnull Class<T> type,
                           @Nonnull Class<? extends T> defaultType) {
        Object object = this.resultSet.getObject(column);
        return object == null ? defaultType.newInstance() : type.cast(object);
    }

    /**
     * Gets the value of the designated column
     * in the current row of this {@link DbResult}
     * object as an {@link Object} in the Java programming language.
     *
     * @param column column
     * @param type   type
     * @return value of the designated column
     */
    @SneakyThrows
    public <T> T getObject(@Nonnull String column,
                           @Nonnull Class<T> type,
                           @Nonnull Supplier<? extends T> defaultSupplier) {
        Object object = this.resultSet.getObject(column);
        return object == null ? defaultSupplier.get() : type.cast(object);
    }
}
