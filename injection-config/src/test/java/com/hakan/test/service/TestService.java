package com.hakan.test.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hakan.injection.annotations.Service;
import com.hakan.test.config.TestConfig;
import org.bukkit.entity.Player;

import java.util.stream.IntStream;

@Singleton
@Service
public class TestService {

    private final TestConfig config;

    @Inject
    public TestService(TestConfig config) {
        this.config = config;
    }

    public void sendMessage(Player player) {
        IntStream.range(0, this.config.getAmount())
                .filter(i -> this.config.isEnabled())
                .mapToObj(i -> this.config.getMessage())
                .forEach(player::sendMessage);
    }
}
