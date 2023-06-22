package com.hakan.test.repository;

import com.hakan.injection.database.annotations.Query;
import com.hakan.injection.database.annotations.QueryParam;
import com.hakan.injection.database.annotations.Repository;
import com.hakan.test.model.User;

//if DbCredential has an instance, you don't have to
//specify the credentials in the @Repository annotation.
@Repository(
        username = "root",
        password = "admin",
        driver = "com.mysql.cj.jdbc.Driver",
        url = "jdbc:mysql://localhost:3306",

        queries = {
                "CREATE DATABASE IF NOT EXISTS hakan;",

                "USE hakan;",

                "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL, name VARCHAR(255) NOT NULL, surname VARCHAR(255) NOT NULL, age INT NOT NULL, PRIMARY KEY (id));",
                "CREATE TABLE IF NOT EXISTS user_credentials (id INT NOT NULL, email VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, PRIMARY KEY (id));"
        }
)
public interface TestRepository {

    @Query(
            "INSERT INTO users (id, name, surname, age) VALUES (':u.id', ':u.name', ':u.surname', ':u.age');" +
            "INSERT INTO user_credentials (id, email, password) VALUES (':u.credential.id', ':u.credential.email', ':u.credential.password');"
    )
    void save(@QueryParam("u") User user);

    @Query(
            "DELETE FROM users WHERE id = :id;" +
            "DELETE FROM user_credentials WHERE id = :id;"
    )
    void deleteById(@QueryParam("id") int id);



    @Query("SELECT * FROM users WHERE id = :id;")
    User findById(@QueryParam("id") int id);

    @Query("SELECT * FROM users INNER JOIN user_credentials " +
           "ON users.id = user_credentials.id " +
           "WHERE users.name = ':name' AND user_credentials.email = ':email';")
    User findByNameAndEmail(@QueryParam("name") String name, @QueryParam("email") String email);
}
