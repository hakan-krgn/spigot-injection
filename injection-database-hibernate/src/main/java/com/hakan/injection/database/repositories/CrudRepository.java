package com.hakan.injection.database.repositories;

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
     * @return id of entity
     */
    ID save(@Nonnull ENTITY entity);

    /**
     * Deletes an entity.
     *
     * @param entity entity
     */
    void delete(@Nonnull ENTITY entity);

    /**
     * Deletes an entity by its id.
     *
     * @param id id
     */
    void deleteById(@Nonnull ID id);
}
