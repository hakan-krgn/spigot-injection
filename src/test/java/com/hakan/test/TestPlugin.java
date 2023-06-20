package com.hakan.test;

import com.hakan.injection.SpigotBootstrap;
import com.hakan.injection.scanner.annotations.Scanner;
import org.bukkit.plugin.java.JavaPlugin;

@Scanner("test")
public class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        SpigotBootstrap.run(this);
    }
}
