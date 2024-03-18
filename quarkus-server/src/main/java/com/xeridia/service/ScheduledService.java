package com.xeridia.service;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ScheduledService {
    private static final Logger log = LoggerFactory.getLogger(ScheduledService.class);

    @Scheduled(every = "30s", identity = "task-job")
    void schedule() {
        log.info("This is an scheduled task");
    }
}
