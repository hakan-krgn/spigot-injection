package com.hakan.spinjection.database.repositories;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * JpaRepository is the interface
 * for database processes which
 * includes {@link CrudRepository}
 * and some extra methods.
 *
 * @param <ID>     ID of entity
 * @param <ENTITY> entity
 */
public interface JpaRepository<ID, ENTITY> extends CrudRepository<ID, ENTITY> {

    /**
     * Finds an entity by id.
     *
     * @param id id
     * @return entity
     */
    @Nullable
    ENTITY findById(@Nonnull ID id);

    /**
     * Finds all entities.
     *
     * @return entities
     */
    @Nonnull
    List<ENTITY> findAll();
}
