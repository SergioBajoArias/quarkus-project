package com.xeridia.producer;

import com.xeridia.model.Message;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class InputProducer {
    private static final Logger log = LoggerFactory.getLogger(InputProducer.class);

    private static final int NUM_THREADS = 1000;
    private static final int NUMBER_OF_MESSAGES = 1000;

    @Inject
    @Channel("input")
    @OnOverflow(value = OnOverflow.Strategy.BUFFER, bufferSize = 1000000)
    Emitter<Record<UUID, Message>> emitter;

    @Scheduled(every = "1h", delayed = "10s")
    public void startSendingMessages() {
        log.info("Sending a new batch of messages at {}", Instant.now());
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        for(int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(this::sendProductToKafka);
        }
    }


    public void sendProductToKafka() {
        for(int i = 0; i < NUMBER_OF_MESSAGES; i++) {
            Random random = new Random();
            UUID uuid = UUID.randomUUID();
            Message message = new Message(uuid, "product " + uuid, random.nextInt(150));
            emitter.send(Record.of(uuid, message));
        }
    }
}
