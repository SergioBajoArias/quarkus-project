package com.xeridia.producer;

import com.xeridia.model.Message;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class InputProducer {
    private static final Logger log = LoggerFactory.getLogger(InputProducer.class);

    private static final int NUM_THREADS = 1;
    private static final int NUMBER_OF_MESSAGES = 1000000;

    @Inject
    @Channel("exchange-input")
    @OnOverflow(value = OnOverflow.Strategy.BUFFER, bufferSize = 1000000)
    Emitter<Message> emitter;

     @Scheduled(every = "1h", delayed = "10s")
    public void startSendingMessages() {
        log.info("Sending a new batch of messages at {}", Instant.now());
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        for(int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(this::sendProductToKafka);
        }
         //this.prices();
    }


    public void sendProductToKafka() {
        for(int i = 0; i < NUMBER_OF_MESSAGES; i++) {
            Random random = new Random();
            Message message = new Message(null, "product " + i, random.nextInt(150));
            emitter.send(message);
        }
    }


    /*@Outgoing("input")
    public Multi<Message> prices() {
        Random random = new Random();
        AtomicInteger count = new AtomicInteger();
        return Multi.createFrom().ticks().every(Duration.ofNanos(1000))
                .map(l -> new Message(UUID.randomUUID(), "product " + count, random.nextInt(150)))
                .onOverflow().drop();
    }*/
}
