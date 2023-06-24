package com.hakan.test.model.credential;

import com.hakan.injection.database.annotations.Column;
import com.hakan.injection.database.annotations.Table;

@Table("user_credentials")
public class UserCredential {

    @Column("id")
    private int id;

    @Column("email")
    private String email;

    @Column("password")
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
