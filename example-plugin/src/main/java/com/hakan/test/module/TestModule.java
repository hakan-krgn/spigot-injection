package com.hakan.test.module;

import com.hakan.injection.annotations.Provide;
import com.hakan.spinjection.database.connection.credential.DbCredential;
import com.hakan.spinjection.module.PluginModule;
import com.hakan.test.config.TestConfig;

public class TestModule extends PluginModule {

    @Provide
    public DbCredential dbCredential(TestConfig config) {
        return new DbCredential(
                config.getDatabaseUrl(),
                config.getDatabaseDriver(),
                config.getDatabaseUsername(),
                config.getDatabasePassword()
        );
    }
}
