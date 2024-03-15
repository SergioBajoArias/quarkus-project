package com.xeridia.producer;

import com.xeridia.model.Message;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.Random;

@ApplicationScoped
@Slf4j
public class InputProducer {
    @Inject
    @Channel("input")
    Emitter<Record<Long, Message>> emitter;

    @Scheduled(every = "1s")
    public void sendMovieToKafka() {
        Random random = new Random();
        long id = System.currentTimeMillis();
        Message message = new Message("product " + id, random.nextInt(150));
        log.info("Sending message {}", message);
        emitter.send(Record.of(id, message));
    }
}
