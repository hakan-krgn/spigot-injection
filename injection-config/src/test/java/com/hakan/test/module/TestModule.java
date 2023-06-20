package com.hakan.test.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hakan.test.config.TestConfig;

@Singleton
public class TestModule extends AbstractModule {

    @Singleton
    @Provides
    public TestConfig testConfig() {
        return new TestConfig("this is the test message");
    }
}
