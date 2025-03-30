package com.hakan.user.config;

import com.hakan.spinjection.config.annotations.ConfigFile;
import com.hakan.spinjection.config.annotations.ConfigValue;
import com.hakan.spinjection.config.configuration.FileConfiguration;
import com.hakan.spinjection.config.container.ContainerType;

@ConfigFile(
        type = ContainerType.YAML,
        resource = "config.yml",
        path = "plugins/UserPlugin/config.yml"
)
public interface UserConfig extends FileConfiguration {

    @ConfigValue("settings.welcome-message")
    String getWelcomeMessage();

    @ConfigValue("settings.repeat-count")
    Integer getRepeatCount();
}
