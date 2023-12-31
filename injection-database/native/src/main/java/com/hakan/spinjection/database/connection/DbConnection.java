package com.hakan.spinjection.database.connection;

import com.hakan.spinjection.database.annotations.Repository;
import com.hakan.spinjection.database.connection.credential.DbCredential;
import com.hakan.spinjection.database.connection.query.DbQuery;
import com.hakan.spinjection.database.connection.result.DbResult;
import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * DbConnection is the connection class to
 * connect to the database and execute queries.
 */
public class DbConnection {

    private final Statement statement;
    private final Connection connection;
    private final DbCredential credential;

    /**
     * Constructor of {@link DbConnection}.
     *
     * @param repository repository
     */
    public DbConnection(@Nonnull Repository repository) {
        this(DbCredential.of(repository));
    }

    /**
     * Constructor of {@link DbConnection}.
     *
     * @param url      url
     * @param driver   driver
     * @param username username
     * @param password password
     */
    public DbConnection(@Nonnull String url,
                        @Nonnull String driver,
                        @Nonnull String username,
                        @Nonnull String password) {
        this(DbCredential.of(url, driver, username, password));
    }

    /**
     * Constructor of {@link DbConnection}.
     *
     * @param credential credential
     */
    @SneakyThrows
    public DbConnection(@Nonnull DbCredential credential) {
        this.credential = credential;
        this.connection = this.connect();
        this.statement = this.connection.createStatement();
    }

    /**
     * Gets the statement.
     *
     * @return statement
     */
    public @Nonnull Statement getStatement() {
        return statement;
    }

    /**
     * Gets the connection.
     *
     * @return connection
     */
    public @Nonnull Connection getConnection() {
        return this.connection;
    }

    /**
     * Gets the credential of
     * the database connection
     *
     * @return credential
     */
    public @Nonnull DbCredential getCredential() {
        return this.credential;
    }

    /**
     * Checks if the connection is connected.
     *
     * @return connected
     */
    public boolean isConnected() {
        return this.connection != null;
    }



    /**
     * Executes the query and
     * returns the result.
     *
     * @param dbQuery dbQuery
     * @return result set
     */
    @SneakyThrows
    public synchronized @Nonnull DbResult executeQuery(@Nonnull DbQuery dbQuery) {
        return this.executeQuery(dbQuery.getQuery());
    }

    /**
     * Executes the query and
     * returns the result.
     *
     * @param query query text
     * @return result set
     */
    @SneakyThrows
    public synchronized @Nonnull DbResult executeQuery(@Nonnull String query) {
        return DbResult.of(this.statement.executeQuery(query));
    }

    /**
     * Executes the query.
     *
     * @param dbQuery dbQuery
     */
    @SneakyThrows
    public synchronized boolean executeUpdate(@Nonnull DbQuery dbQuery) {
        return this.executeUpdate(dbQuery.getQuery());
    }

    /**
     * Executes the query.
     *
     * @param query query text
     */
    @SneakyThrows
    public synchronized boolean executeUpdate(@Nonnull String query) {
        for (String queryLine : query.split(";"))
            this.statement.executeUpdate(queryLine);
        return true;
    }



    /**
     * Connects to the database.
     *
     * @return connection
     */
    @SneakyThrows
    public @Nonnull Connection connect() {
        Class.forName(this.credential.driver());

        Connection connection = DriverManager.getConnection(
                this.credential.url(),
                this.credential.username(),
                this.credential.password()
        );
        connection.setAutoCommit(true);

        return connection;
    }
}
