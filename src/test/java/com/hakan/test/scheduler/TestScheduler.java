package com.hakan.test.scheduler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hakan.injection.scanner.annotations.Component;
import com.hakan.injection.scheduler.annotations.Scheduler;
import com.hakan.test.service.TestService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@Singleton
@Component
public class TestScheduler {

    private final TestService service;

    @Inject
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
