package com.hakan.test.service;

import com.hakan.basicdi.annotations.Autowired;
import com.hakan.basicdi.annotations.Service;
import org.bukkit.entity.Player;

@Service
public class TestService {

	@Autowired
	public TestService() {

	}

	public void sendMessage(Player player, String message) {
		player.sendMessage(message);
		System.out.println(message);
	}
}
