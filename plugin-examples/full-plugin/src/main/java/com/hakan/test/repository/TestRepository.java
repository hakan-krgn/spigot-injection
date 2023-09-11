package com.hakan.test.repository;

import com.hakan.spinjection.database.annotations.Query;
import com.hakan.spinjection.database.annotations.QueryParam;
import com.hakan.spinjection.database.annotations.Repository;
import com.hakan.spinjection.database.repositories.JpaRepository;
import com.hakan.test.model.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<Long, TestEntity> {

    @Query("select t from TestEntity t where t.name = :name")
    TestEntity findByName(@QueryParam("name") String name);
}
