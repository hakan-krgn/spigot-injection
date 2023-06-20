package com.hakan.test.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hakan.injection.annotations.Service;
import com.hakan.test.config.TestConfig;
import org.bukkit.entity.Player;

@Singleton
@Service
public class TestService {

    private final TestConfig config;

    @Inject
    public TestService(TestConfig config) {
        this.config = config;
    }

    public void sendMessage(Player player, String message) {
        System.out.println(message);
        System.out.println(this.config.getMessage());
        player.sendMessage(message);
    }
}
