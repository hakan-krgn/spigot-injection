package com.hakan.test;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.annotations.Scanner;
import org.bukkit.plugin.java.JavaPlugin;

@Scanner("com.hakan.test")
public class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        SpigotBootstrap.run(this);
    }
}
