package com.hakan.test.service;

import com.google.inject.Inject;
import com.hakan.injection.annotations.Service;
import com.hakan.test.config.TestConfig;
import org.bukkit.entity.Player;

import java.util.stream.IntStream;

@Service
public class TestService {

    private final TestConfig config;

    @Inject
    public TestService(TestConfig config) {
        this.config = config;
    }

    public void saveConfig() {
        this.config.save();
    }

    public void reloadConfig() {
        this.config.reload();
    }

    public void sendMessage(Player player) {
        IntStream.range(0, this.config.getAmount())
                .filter(i -> this.config.isEnabled())
                .mapToObj(i -> this.config.getMessage())
                .forEach(player::sendMessage);
    }
}
