package com.hakan.test.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hakan.injection.annotations.Service;
import com.hakan.test.model.User;
import com.hakan.test.repository.TestRepository;

import java.util.List;

@Singleton
@Service
public class TestService {

    private final TestRepository repository;

    @Inject
    public TestService(TestRepository repository) {
        this.repository = repository;
    }

    public User findById(int id) {
        return this.repository.findById(id);
    }

    public User findByNameAndEmail(String name, String email) {
        return this.repository.findByNameAndEmail(name, email);
    }

    public List<User> findAll() {
        return this.repository.findAll();
    }

    public void save(User user) {
        this.repository.save(user);
    }

    public void delete(User user) {
        this.repository.delete(user);
    }

    public void deleteById(int id) {
        this.repository.deleteById(id);
    }
}
