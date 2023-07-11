package com.hakan.spinjection.database.repositories;

import javax.annotation.Nonnull;

/**
 * CrudRepository is the interface
 * for database processes which
 * includes CRUD methods.
 *
 * @param <ID>     ID of entity
 * @param <ENTITY> entity
 */
public interface CrudRepository<ID, ENTITY> {

    /**
     * Saves an entity and
     * returns the id of entity.
     *
     * @param entity entity
     * @return new entity with identifiers
     */
    @Nonnull
    ENTITY save(@Nonnull ENTITY entity);

    /**
     * Deletes an entity.
     *
     * @param entity entity
     */
    @Nonnull
    ENTITY delete(@Nonnull ENTITY entity);

    /**
     * Deletes an entity by its id.
     *
     * @param id id
     */
    @Nonnull
    ENTITY deleteById(@Nonnull ID id);
}
