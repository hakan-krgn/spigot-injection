package com.hakan.test.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hakan.injection.annotations.Service;
import com.hakan.test.repository.TestRepository;

@Singleton
@Service
public class TestService {

    private final TestRepository repository;

    @Inject
    public TestService(TestRepository repository) {
        this.repository = repository;
    }

    public void saveSomething(int id, String name) {
        this.repository.save(id, name);
    }

    public void deleteSomething(int id) {
        this.repository.delete(id);
    }
}
