package com.hakan.test.config;

import com.hakan.spinjection.config.annotations.ConfigFile;
import com.hakan.spinjection.config.annotations.ConfigValue;
import com.hakan.spinjection.config.configuration.FileConfiguration;
import com.hakan.spinjection.config.container.ContainerType;

@ConfigFile(
        type = ContainerType.YAML,
        resource = "config.yml",
        path = "plugins/TestPlugin/config.yml"
)
public interface TestConfig extends FileConfiguration {

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
