package com.hakan.test.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hakan.injection.annotations.Service;
import org.bukkit.entity.Player;

@Singleton
@Service
public class TestService {

    @Inject
    public TestService() {

    }

    public void sendMessage(String player, String message) {
        System.out.println("Player: " + player + " Message: " + message);
    }
}
