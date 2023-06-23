package com.hakan.test.module;

import com.google.inject.Provides;
import com.hakan.injection.database.connection.credential.DbCredential;
import com.hakan.injection.module.PluginModule;

public class TestModule extends PluginModule {

    @Provides
    public DbCredential credential() {
        return DbCredential.of(
                "jdbc:mysql://localhost:3306/hakan?createDatabaseIfNotExist=true",
                "com.mysql.cj.jdbc.Driver",
                "root",
                "admin"
        );
    }
}
