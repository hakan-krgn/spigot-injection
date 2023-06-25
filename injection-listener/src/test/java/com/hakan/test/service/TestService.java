package com.hakan.test.service;

import com.google.inject.Inject;
import com.hakan.injection.annotations.Service;
import org.bukkit.entity.Player;

@Service
public class TestService {

    @Inject
    public TestService() {

    }

    public void sendMessage(Player player, String message) {
        System.out.println(message);
        player.sendMessage(message);
    }
}
