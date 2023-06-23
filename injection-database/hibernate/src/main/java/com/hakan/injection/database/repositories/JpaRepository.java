package com.hakan.injection.database.repositories;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface JpaRepository<ID, ENTITY> extends CrudRepository<ID, ENTITY> {

    @Nullable
    ENTITY findById(@Nonnull ID id);

    @Nonnull
    List<ENTITY> findAll();
}
