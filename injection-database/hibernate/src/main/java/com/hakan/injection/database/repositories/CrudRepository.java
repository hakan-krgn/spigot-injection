package com.hakan.injection.database.repositories;

import javax.annotation.Nonnull;

public interface CrudRepository<ID, ENTITY> {

    ID save(@Nonnull ENTITY entity);

    void delete(@Nonnull ENTITY entity);

    void deleteById(@Nonnull ID id);
}
