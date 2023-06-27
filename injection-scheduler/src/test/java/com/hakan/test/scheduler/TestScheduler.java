package com.hakan.test.scheduler;

import com.hakan.injection.annotations.Autowired;
import com.hakan.injection.annotations.Component;
import com.hakan.spinjection.scheduler.annotations.Scheduler;
import com.hakan.test.service.TestService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@Component
public class TestScheduler {

    private final TestService service;

    @Autowired
    public TestScheduler(TestService service) {
        this.service = service;
    }

    @Scheduler(
            delay = 1,
            period = 5,
            async = true,
            timeUnit = TimeUnit.MINUTES
    )
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.service.sendMessage(player, "test");
        }
    }
}
