package com.hakan.test.listener;

import com.hakan.basicdi.annotations.Autowired;
import com.hakan.basicdi.annotations.Component;
import com.hakan.spinjection.listener.annotations.EventListener;
import com.hakan.test.service.TestService;
import org.bukkit.event.player.PlayerJoinEvent;

@Component
public class TestListener {

    private final TestService service;

    @Autowired
    public TestListener(TestService service) {
        this.service = service;
    }

    @EventListener
    public void playerJoin(PlayerJoinEvent event) {
        this.service.sendMessage(event.getPlayer(), "welcome to the server!");
    }
}
