package com.hakan.user.config;

import com.hakan.spinjection.config.annotations.ConfigFile;
import com.hakan.spinjection.config.annotations.ConfigTimer;
import com.hakan.spinjection.config.annotations.ConfigValue;
import com.hakan.spinjection.config.configuration.BaseConfiguration;
import com.hakan.spinjection.config.container.ContainerType;

import java.util.concurrent.TimeUnit;

@ConfigFile(
        type = ContainerType.YAML,
        resource = "config.yml",
        path = "plugins/UserPlugin/config.yml",

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
public interface UserConfig extends BaseConfiguration {

    @ConfigValue("settings.welcome-message")
    String getWelcomeMessage();

    @ConfigValue("settings.repeat-count")
    Integer getRepeatCount();
}
