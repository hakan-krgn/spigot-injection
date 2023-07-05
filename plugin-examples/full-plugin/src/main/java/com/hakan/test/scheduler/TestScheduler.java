package com.hakan.test.scheduler;

import com.hakan.injection.annotations.Autowired;
import com.hakan.injection.annotations.Component;
import com.hakan.spinjection.scheduler.annotations.Scheduler;
import com.hakan.test.cache.TestCache;
import com.hakan.test.repository.TestRepository;
import com.hakan.test.service.TestService;

import java.util.concurrent.TimeUnit;

@Component
public class TestScheduler {

    private final TestService service;

    @Autowired
    public TestScheduler(TestService service) {
        this.service = service;
    }

    @Scheduler(
            async = true,
            delay = 0,
            period = 3,
            timeUnit = TimeUnit.SECONDS
    )
    public void saveAll() {
        TestCache testCache = this.service.getCache();
        TestRepository testRepository = this.service.getRepository();

        testCache.getEntities().values().forEach(testRepository::save);
    }
}
