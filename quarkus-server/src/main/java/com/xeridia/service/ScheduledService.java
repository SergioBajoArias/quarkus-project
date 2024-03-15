package com.xeridia.service;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ScheduledService {
    @Scheduled(every = "30s", identity = "task-job")
    void schedule() {
        log.info("This is an scheduled task");
    }
}
