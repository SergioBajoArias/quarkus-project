package com.xeridia.producer;

import com.xeridia.model.Message;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class InputProducer {
    private static final Logger log = LoggerFactory.getLogger(InputProducer.class);

    private static final int NUM_THREADS = 10;
    private static final int WAIT_TIME_IN_MS = 5;

    @Inject
    @Broadcast
    @Channel("input")
    Emitter<Record<UUID, Message>> emitter;

    @Scheduled(every = "1h", delayed = "20s")
    public void startSendingMessages() {
        log.info("Sending a new batch of messages");
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        for(int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(this::sendMovieToKafka);
        }
    }


    public void sendMovieToKafka() {
        while(true) {
            Random random = new Random();
            UUID uuid = UUID.randomUUID();
            Message message = new Message(uuid, "product " + uuid, random.nextInt(150));
            emitter.send(Record.of(uuid, message));
            try {
                Thread.sleep(WAIT_TIME_IN_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
