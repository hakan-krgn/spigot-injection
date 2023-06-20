package com.hakan.test.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hakan.injection.scanner.annotations.Configuration;

@Singleton
@Configuration
public class TestConfiguration extends AbstractModule {

    @Provides
    public TestConfig testConfig() {
        return new TestConfig("this is the test message");
    }
}
