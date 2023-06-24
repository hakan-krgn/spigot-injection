package com.hakan.test.config;

import com.hakan.injection.config.annotations.ConfigFile;
import com.hakan.injection.config.annotations.ConfigValue;
import com.hakan.injection.config.annotations.ReloadTimer;
import com.hakan.injection.config.annotations.SaveTimer;
import com.hakan.injection.config.configuration.BaseConfiguration;

import java.util.concurrent.TimeUnit;

@ConfigFile(
        resource = "test.yml",
        path = "plugins/TestInjection/test.yml",

        reloadTimer = @ReloadTimer(
                enabled = true,
                async = true,
                delay = 5,
                period = 5,
                timeUnit = TimeUnit.SECONDS
        ),
        saveTimer = @SaveTimer(
                enabled = true,
                async = true,
                delay = 5,
                period = 5,
                timeUnit = TimeUnit.SECONDS
        )
)
public interface TestConfig extends BaseConfiguration {

    @ConfigValue("test.message")
    String getMessage();

    @ConfigValue("test.amount")
    Integer getAmount();

    @ConfigValue("test.enabled")
    Boolean isEnabled();
}
