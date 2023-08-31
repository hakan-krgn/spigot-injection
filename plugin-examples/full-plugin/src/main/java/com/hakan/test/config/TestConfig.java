package com.hakan.test.config;

import com.hakan.spinjection.config.annotations.ConfigFile;
import com.hakan.spinjection.config.annotations.ConfigTimer;
import com.hakan.spinjection.config.annotations.ConfigValue;
import com.hakan.spinjection.config.configuration.BaseConfiguration;
import com.hakan.spinjection.config.container.ContainerType;

import java.util.concurrent.TimeUnit;

@ConfigFile(
        type = ContainerType.YAML,
        resource = "config.yml",
        path = "plugins/TestPlugin/config.yml",

        reloadTimer = @ConfigTimer(
                enabled = true,
                delay = 10,
                period = 10,
                async = true,
                timeUnit = TimeUnit.SECONDS
        ),

        saveTimer = @ConfigTimer(
                enabled = true,
                delay = 10,
                period = 10,
                async = true,
                timeUnit = TimeUnit.SECONDS
        )
)
public interface TestConfig extends BaseConfiguration {

    @ConfigValue("settings.message")
    String getMessage();

    @ConfigValue("settings.repeat")
    Integer getRepeat();

    @ConfigValue("settings.to-console")
    Boolean isToConsole();


    @ConfigValue("database.username")
    String getDatabaseUsername();

    @ConfigValue("database.password")
    String getDatabasePassword();

    @ConfigValue("database.url")
    String getDatabaseUrl();

    @ConfigValue("database.driver")
    String getDatabaseDriver();
}
