package com.xeridia.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeridia.model.Alert;
import com.xeridia.model.Message;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
@ServerEndpoint("/messages/{username}")
public class PersistenceConsumer {

    private static final Logger log = LoggerFactory.getLogger(PersistenceConsumer.class);

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Incoming("input")
    @Transactional
    public void consumeMessage(Record<Long, Message> record) throws JsonProcessingException {
        log.info("Received MESSAGE to send to websocket");
        broadcast(record.value());
    }

    @Incoming("alerts")
    public void consumeAlert(Alert alert) {
        log.info("Received ALERT to persist {}", alert.event().getPrice());
        // alertRepository.save(alert.event());
        broadcast(alert.event());
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        log.info("Session opened for user {}", username);
        sessions.put(username, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        log.info("Session closed for user {}", username);
        sessions.remove(username);
    }

    private void broadcast(Message message) {
        sessions.values().forEach(s -> {
            try {
                s.getAsyncRemote().sendObject(objectMapper.writeValueAsString(message), result ->  {
                    if (result.getException() != null) {
                        System.out.println("Unable to send message: " + result.getException());
                    }
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
