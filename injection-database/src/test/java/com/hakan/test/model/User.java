package com.hakan.test.model;

import com.hakan.test.model.credential.UserCredential;

public class User {

    private final int id;
    private final String name;
    private final String surname;
    private final int age;
    private final UserCredential credential;

    public User(int id,
                String name,
                String surname,
                int age,
                UserCredential credential) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.credential = credential;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public UserCredential getCredential() {
        return credential;
    }
}
