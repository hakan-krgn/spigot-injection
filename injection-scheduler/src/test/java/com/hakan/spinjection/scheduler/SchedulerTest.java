package com.hakan.spinjection.scheduler;

import com.hakan.basicdi.annotations.Autowired;
import com.hakan.basicdi.annotations.Component;
import com.hakan.basicdi.annotations.Service;
import com.hakan.spinjection.SpigotBootstrap;
import com.hakan.spinjection.annotations.Scanner;
import com.hakan.spinjection.scheduler.annotations.Scheduler;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Test;

@Scanner("com.hakan.spinjection.scheduler")
class SchedulerTest extends JavaPlugin {

    @Test
    void setUp() {
        SpigotBootstrap.run(this);
    }


    @Service
    class ExampleService {

        private final String name;
        private final String version;

        @Autowired
        public ExampleService() {
            this.name = "example service";
            this.version = "1.0.0";
        }
    }

    @Component
    class ExampleScheduler {

        @Autowired
        private ExampleService service;

        @Scheduler
        public void run() {
            System.out.println(this.service.name);
            System.out.println(this.service.version);
        }
    }
}
