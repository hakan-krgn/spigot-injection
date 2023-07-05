package com.hakan.user.listener;

import com.hakan.basicdi.annotations.Autowired;
import com.hakan.basicdi.annotations.Component;
import com.hakan.spinjection.listener.annotations.EventListener;
import com.hakan.user.config.UserConfig;
import com.hakan.user.service.UserService;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Component
public class UserListener {

    private final UserConfig config;
    private final UserService service;

    @Autowired
    public UserListener(UserConfig config,
                        UserService service) {
        this.config = config;
        this.service = service;
    }

    @EventListener
    public void onJoin(PlayerJoinEvent event) {
        this.service.add(event.getPlayer());

        for (int i = 0; i < this.config.getRepeatCount(); i++) {
            event.getPlayer().sendMessage(this.config.getWelcomeMessage());
        }
    }

    @EventListener
    public void onQuit(PlayerQuitEvent event) {
        this.service.remove(event.getPlayer());
    }
}
