package com.xeridia.consumer;

import com.xeridia.model.Alert;
import com.xeridia.model.Message;
import com.xeridia.repository.AlertRepository;
import com.xeridia.repository.MessageRepository;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class PersistenceConsumer {

    private static final Logger log = LoggerFactory.getLogger(PersistenceConsumer.class);

    @Inject
    MessageRepository messageRepository;

    @Inject
    AlertRepository alertRepository;

    @Incoming("input")
    public void consumeMessage(Record<UUID, Message> record) {
        log.info("Received MESSAGE to persist");
        messageRepository.save(record.value());
    }

    @Incoming("alerts")
    public void consumeAlert(Alert alert) {
            log.info("Received ALERT to persist {}", alert.event().getPrice());
            alertRepository.save(alert.event());
    }

    // @Scheduled(every = "5s")
    public void startSendingMessages() {
        long expectedMessages = 1000000;
        if(messageRepository.count().equals(expectedMessages)) {
            log.info("Received all messages at {}", Instant.now());
        }
    }
}
