package com.hakan.test.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hakan.injection.annotations.Service;
import org.bukkit.entity.Player;

@Service
public class TestService {

    @Inject
    public TestService() {
        System.out.println("TestService");
    }

    public void sendMessage(Player player, String message) {
        System.out.println(message);
        player.sendMessage(message);
    }
}
