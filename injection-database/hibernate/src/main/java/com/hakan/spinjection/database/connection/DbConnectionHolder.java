package com.hakan.spinjection.database.connection;

import com.hakan.spinjection.database.executor.DatabaseExecutor;
import lombok.Data;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
public class DbConnectionHolder {

    private Set<DatabaseExecutor> executors = new HashSet<>();

    public void add(DatabaseExecutor executor) {
        executors.add(executor);
    }

    /**
     * For each Repository interface exists {@link DbConnection}, which
     * responsible for opening Hibernate Sessions and communication with database.
     * <p>
     * Find {@link DbConnection} by RepositoryClass
     * @return Optional of {@link DbConnection}. Can be null, if repositoryClass isn't in Spigot Injection context
     */
    public Optional<DbConnection> findByRepositoryClass(Class<?> repositoryClass) {
        Optional<DatabaseExecutor> executor = executors.stream()
                .filter(ex -> ex.getDeclaringClass().getName().equalsIgnoreCase(repositoryClass.getName()))
                .findFirst();
        return executor.map(DatabaseExecutor::getConnection);
    }
}
