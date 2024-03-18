package com.xeridia.consumer;

import com.xeridia.model.Alert;
import com.xeridia.model.Message;
import com.xeridia.producer.InputProducer;
import com.xeridia.repository.AlertRepository;
import com.xeridia.repository.MessageRepository;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@ApplicationScoped
public class PersistenceConsumer {

    private static final Logger log = LoggerFactory.getLogger(InputProducer.class);

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
        if(alert.event().getUuid() != null) {
            log.info("Received ALERT to persist {}", alert.event().getPrice());
            alertRepository.save(alert.event());
        }
    }
}
