package com.hakan.user;

import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.annotations.Scanner;
import org.bukkit.plugin.java.JavaPlugin;

@Scanner("com.hakan.test")
//Extra line for testing purposes!
public class UserPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		SpigotBootstrap.run(this);
	}
}
