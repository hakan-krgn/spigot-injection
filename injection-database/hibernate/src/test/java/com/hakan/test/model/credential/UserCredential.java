package com.hakan.test.model.credential;

import com.hakan.test.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
