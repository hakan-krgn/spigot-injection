package com.hakan.test.repository;

import com.hakan.injection.database.annotations.Query;
import com.hakan.injection.database.annotations.QueryParam;

public interface TestRepository {

    @Query("INSERT INTO Test (id, name) VALUES (:id, :name)")
    void save(@QueryParam("id") int id, @QueryParam("name") String name);

    @Query("DELETE FROM Test WHERE id = :id")
    void delete(@QueryParam("id") int id);
}
