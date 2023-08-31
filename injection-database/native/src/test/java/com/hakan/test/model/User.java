package com.hakan.test.model;

import com.hakan.spinjection.database.connection.result.DbResult;
import com.hakan.test.model.credential.UserCredential;

public class User {

	public static User byResult(DbResult result) {
		result.next();

		return new User(
			result.getInt("users.id"),
			result.getString("users.name"),
			result.getString("users.surname"),
			result.getInt("users.age"),
			result.getString("user_credentials.email"),
			result.getString("user_credentials.password")
		);
	}



	private final int id;
	private final String name;
	private final String surname;
	private final int age;
	private final UserCredential credential;

	public User(int id,
				String name,
				String surname,
				int age,
				String email,
				String password) {
		this.id = id;
		this.age = age;
		this.name = name;
		this.surname = surname;
		this.credential = new UserCredential(id, email, password);
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getSurname() {
		return this.surname;
	}

	public int getAge() {
		return this.age;
	}

	public UserCredential getCredential() {
		return this.credential;
	}
}
