package com.hakan.test.model.credential;

public class UserCredential {

    private final int id;
    private final String email;
    private final String password;

    public UserCredential(int id,
                          String email,
                          String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
