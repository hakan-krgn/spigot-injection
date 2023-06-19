package com.hakan.test.config;

import com.google.inject.Inject;
import com.hakan.injection.scanner.annotations.Component;

@Component
public class TestConfig {

    private final String message;

    @Inject
    public TestConfig() {
        this.message = "test message";
    }

    public String getMessage() {
        return this.message;
    }
}
