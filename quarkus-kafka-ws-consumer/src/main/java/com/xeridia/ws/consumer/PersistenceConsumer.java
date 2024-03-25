package com.xeridia.ws.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeridia.ws.model.Message;
import com.xeridia.ws.repository.MessageRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ApplicationScoped
public class PersistenceConsumer {

    private static final Logger log = LoggerFactory.getLogger(PersistenceConsumer.class);

    @Inject
    MessageRepository messageRepository;

    public void consume(@Observes StartupEvent startupEvent) throws URISyntaxException, DeploymentException, IOException {
        log.info("Connecting to websocket....");
        Session session = ContainerProvider.getWebSocketContainer().connectToServer(new Client(messageRepository), new URI("http://localhost:8080/messages/sergio"));
    }

    @ClientEndpoint
    public class Client {

        private MessageRepository messageRepository;
        private ObjectMapper objectMapper;
        public Client(MessageRepository messageRepository) {
            this.messageRepository = messageRepository;
            this.objectMapper = new ObjectMapper();
        };

        @OnOpen
        public void open(Session session) {
            // Send a message to indicate that we are ready,
            // as the message handler may not be registered immediately after this callback.
            session.getAsyncRemote().sendText("_ready_");
        }

        @OnMessage
        void message(String msg) throws JsonProcessingException {
            log.info("Received message throw websocket: {}", msg);
            // this.messageRepository.save(objectMapper.readValue(msg, Message.class));
        }

    }
}
