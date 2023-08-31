package com.hakan.test.service;

import com.hakan.basicdi.annotations.Autowired;
import com.hakan.basicdi.annotations.PostConstruct;
import com.hakan.basicdi.annotations.Service;
import com.hakan.test.model.User;
import com.hakan.test.repository.TestRepository;

import java.util.List;

@Service
public class TestService {

    private final TestRepository repository;

    @Autowired
    public TestService(TestRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        this.repository.save(new User("Hakan", "Kargin", 20, "hakan@gmail.com", "123456"));
        this.repository.save(new User("Mert", "Tas", 20, "mert@gmail.com", "123456"));
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
