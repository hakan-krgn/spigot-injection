package com.hakan.test.service;

import com.google.inject.Inject;
import com.hakan.injection.scanner.annotations.Service;
import com.hakan.test.config.TestConfig;
import org.bukkit.entity.Player;

@Service
public class TestService {

    private final TestConfig config;

    @Inject
    public TestService(TestConfig config) {
        this.config = config;
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(message);
        player.sendMessage(this.config.getMessage());
    }
}
