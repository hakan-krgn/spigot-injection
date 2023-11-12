package com.hakan.spinjection.database.connection;

import com.hakan.spinjection.database.executor.DatabaseExecutor;
import lombok.Data;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
public class DatabaseExecutorsHolder {

    private Set<DatabaseExecutor> executors = new HashSet<>();

    public void add(DatabaseExecutor executor) {
        executors.add(executor);
    }

    /**
     * For each Repository interface exists {@link DatabaseExecutor}, which
     * responsible for opening Hibernate Sessions and communication with database.
     * <p>
     * Find {@link DatabaseExecutor} by RepositoryClass
     * @return Optional of DatabaseExecutor. Can be null, if repositoryClass isn't in Spigot Injection context
     */
    public Optional<DatabaseExecutor> findByRepositoryClass(Class<?> repositoryClass) {
        return executors.stream()
                .filter(executor -> executor.getDeclaringClass().getName().equalsIgnoreCase(repositoryClass.getName()))
                .findFirst();
    }
}
