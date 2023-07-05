package com.hakan.test.cache;

import com.hakan.basicdi.annotations.Autowired;
import com.hakan.basicdi.annotations.Component;
import com.hakan.test.model.TestEntity;

import java.util.HashMap;
import java.util.Map;

@Component
public class TestCache {

    private final Map<Long, TestEntity> entities;

    @Autowired
    public TestCache() {
        this.entities = new HashMap<>();
    }

    public Map<Long, TestEntity> getEntities() {
        return this.entities;
    }

    public void add(TestEntity entity) {
        this.entities.put(entity.getId(), entity);
    }

    public TestEntity get(Long id) {
        return this.entities.get(id);
    }

    public void remove(Long id) {
        this.entities.remove(id);
    }

    public void clear() {
        this.entities.clear();
    }
}
