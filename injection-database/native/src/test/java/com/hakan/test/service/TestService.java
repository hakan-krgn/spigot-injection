package com.hakan.test.service;

import com.hakan.injection.annotations.Autowired;
import com.hakan.injection.annotations.PostConstruct;
import com.hakan.injection.annotations.Service;
import com.hakan.test.model.User;
import com.hakan.test.repository.TestRepository;

@Service
public class TestService {

    private final TestRepository repository;

    @Autowired
    public TestService(TestRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        this.save(new User(1, "Hakan", "Kargin", 23, "hakan@gmail.com", "123456"));
        this.save(new User(2, "Ali", "Ters", 23, "ali@gmail.com", "123456"));

        this.delete(2);

        User user = this.findById(1);
        System.out.println(user.getName());
    }


    public User findById(int id) {
        return User.byResult(this.repository.findById(id));
    }

    public User findByNameAndEmail(String name, String email) {
        return User.byResult(this.repository.findByNameAndEmail(name, email));
    }

    public void save(User user) {
        this.repository.save(user);
    }

    public void delete(int id) {
        this.repository.deleteById(id);
    }
}
