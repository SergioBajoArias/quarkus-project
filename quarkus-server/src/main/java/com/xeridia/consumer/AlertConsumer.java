package com.xeridia.consumer;

import com.xeridia.model.Alert;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@Slf4j
public class AlertConsumer {
    @Incoming("alerts")
    public void consume(Alert alert) {
        log.info("Received alert from message {}!!!", alert.event());
    }
}
