package com.hakan.test.repository;

import com.hakan.spinjection.database.annotations.Query;
import com.hakan.spinjection.database.annotations.QueryParam;
import com.hakan.spinjection.database.annotations.Repository;
import com.hakan.spinjection.database.repositories.JpaRepository;
import com.hakan.test.model.User;

//if DbCredential has an instance, you don't have to
//specify the credentials in the @Repository annotation.
@Repository(
	username = "root",
	password = "admin",
	driver = "com.mysql.cj.jdbc.Driver",
	url = "jdbc:mysql://localhost:3306",

	id = Integer.class,
	entity = User.class,

	queries = {
		"create database hakan;",
	}
)
public interface TestRepository extends JpaRepository<Integer, User> {

	@Query("select u from User u where u.name = :name and u.credential.email = :email")
	User findByNameAndEmail(@QueryParam("name") String name,
							@QueryParam("email") String email);
}
