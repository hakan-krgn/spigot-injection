package com.hakan.test.service;

import com.hakan.injection.annotations.Autowired;
import com.hakan.injection.annotations.PostConstruct;
import com.hakan.injection.annotations.Service;
import com.hakan.test.cache.TestCache;
import com.hakan.test.model.TestEntity;
import com.hakan.test.repository.TestRepository;

@Service
public class TestService {

    private final TestCache cache;
    private final TestRepository repository;

    @Autowired
    public TestService(TestCache cache,
                       TestRepository repository) {
        this.cache = cache;
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        this.repository.findAll().forEach(this.cache::add);
    }


    public TestCache getCache() {
        return this.cache;
    }

    public TestRepository getRepository() {
        return this.repository;
    }


    public TestEntity findById(Long id) {
        return this.cache.get(id);
    }

    public TestEntity findByName(String name) {
        return this.repository.findByName(name);
    }


    public void create(String name,
                       String surname) {
        this.create(new TestEntity(name, surname));
    }

    public void create(TestEntity entity) {
        this.cache.add(entity);
        this.repository.save(entity);
    }

    public void delete(Long id) {
        this.cache.remove(id);
        this.repository.deleteById(id);
    }
}
