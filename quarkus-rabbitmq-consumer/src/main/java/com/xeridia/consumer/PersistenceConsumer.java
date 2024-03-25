package com.xeridia.consumer;

import com.xeridia.model.Message;
import com.xeridia.repository.MessageRepository;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class PersistenceConsumer {

    private static final Logger log = LoggerFactory.getLogger(PersistenceConsumer.class);

    @Inject
    MessageRepository messageRepository;

    private static final AtomicInteger counter = new AtomicInteger(0);

    @Incoming("input")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<Void> process(org.eclipse.microprofile.reactive.messaging.Message<JsonObject> messageJson) {
        int counterValue = counter.incrementAndGet();
        log.info("Received MESSAGE number {} to persist", counterValue);
        if(counterValue <= 1000000) {
            messageRepository.save(messageJson.getPayload().mapTo(Message.class));
        }
        if(counterValue == 1000000) {
            log.info("Received all messages at {}", Instant.now());
        }

        return messageJson.ack();
    }

    // @Scheduled(every = "5s")
    public void startSendingMessages() {
        long expectedMessages = 1000000;
        if(messageRepository.count().equals(expectedMessages)) {
            log.info("Received all messages at {}", Instant.now());
        }
    }
}
