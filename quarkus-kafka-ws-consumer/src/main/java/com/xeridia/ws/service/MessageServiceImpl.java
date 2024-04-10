package com.xeridia.ws.service;

import com.xeridia.ws.generated.*;
import com.xeridia.ws.repository.MessageRepository;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class MessageServiceImpl implements MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Inject
    MessageRepository messageRepository;

    @Override
    // @RunOnVirtualThread
    public Uni<MessageReply> findById(MessageRequest request) {
        //log.info("Received gRPC request for id {}", request.getId());
        return messageRepository
                .findByIdReactive(request.getId())
                .map(m -> MessageReply.newBuilder().setId(m.getId()).setPrice(m.getPrice()).setProduct(m.getProduct()).build());
    }
}
