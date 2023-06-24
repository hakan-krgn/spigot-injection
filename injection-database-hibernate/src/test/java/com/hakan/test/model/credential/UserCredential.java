package com.hakan.test.model.credential;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class UserCredential {

    @Id
    private int id;

    private String email;

    private String password;

    public UserCredential() {

    }

    public UserCredential(int id,
                          String email,
                          String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
