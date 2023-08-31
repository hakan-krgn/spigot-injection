package com.hakan.test.scheduler;

import com.hakan.basicdi.annotations.Autowired;
import com.hakan.basicdi.annotations.Component;
import com.hakan.spinjection.annotations.Async;
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

	@Async
	@Scheduler(
		delay = 1,
		period = 5,
		timeUnit = TimeUnit.MINUTES
	)
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			this.service.sendMessage(player, "test");
		}
	}
}
