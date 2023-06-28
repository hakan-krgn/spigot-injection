package com.hakan.test.model.credential;

import com.hakan.test.model.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users_credentials")
public class UserCredential {

    @Id
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    public UserCredential() {

    }

    public UserCredential(User user,
                          String email,
                          String password) {
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public User getUser() {
        return this.user;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
