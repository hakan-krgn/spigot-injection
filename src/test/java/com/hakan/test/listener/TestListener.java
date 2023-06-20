package com.hakan.test.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hakan.injection.listener.annotations.EventListener;
import com.hakan.injection.scanner.annotations.Component;
import com.hakan.test.service.TestService;
import org.bukkit.event.player.PlayerJoinEvent;

@Singleton
@Component
public class TestListener {

    private final TestService service;

    @Inject
    public TestListener(TestService service) {
        this.service = service;
    }

    @EventListener
    public void playerJoin(PlayerJoinEvent event) {
        this.service.sendMessage(event.getPlayer(), "welcome to the server!");
    }
}
