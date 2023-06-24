package com.hakan.test.config;

import com.hakan.injection.config.annotations.ConfigFile;
import com.hakan.injection.config.annotations.ConfigValue;
import com.hakan.injection.config.configuration.BaseConfiguration;

@ConfigFile(
        resource = "test.yml",
        path = "plugins/TestInjection/test.yml"
)
public interface TestConfig extends BaseConfiguration {

    @ConfigValue("test.message")
    String getMessage();

    @ConfigValue("test.amount")
    int getAmount();

    @ConfigValue("test.enabled")
    boolean isEnabled();
}
