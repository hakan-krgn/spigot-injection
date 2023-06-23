package com.hakan.test.model;

import com.hakan.test.model.credential.UserCredential;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class User {

    @Id
    private int id;

    private String name;

    private String surname;

    private int age;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserCredential credential;

    public User() {

    }

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
