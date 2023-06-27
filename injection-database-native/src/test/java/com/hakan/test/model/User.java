package com.hakan.test.model;

import com.hakan.spinjection.database.annotations.Column;
import com.hakan.spinjection.database.annotations.Relational;
import com.hakan.spinjection.database.annotations.Table;
import com.hakan.test.model.credential.UserCredential;

@Table("users")
public class User {

    @Column("id")
    private int id;

    @Column("name")
    private String name;

    @Column("surname")
    private String surname;

    @Column("age")
    private int age;

    @Relational
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
