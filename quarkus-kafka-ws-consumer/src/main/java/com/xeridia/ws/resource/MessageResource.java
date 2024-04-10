package com.xeridia.ws.resource;

import com.xeridia.ws.generated.MessageReply;
import com.xeridia.ws.generated.MessageRequest;
import com.xeridia.ws.model.Message;
import com.xeridia.ws.repository.MessageRepository;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Path("/messages")
public class MessageResource {

    @GrpcClient
    com.xeridia.ws.generated.MessageService messageService;

    @Inject
    MessageRepository messageRepository;

    @GET
    @Path("/{id}/reactive")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Message> findByIdReactive(@PathParam("id") Integer id) {
        return messageRepository.findByIdReactive(id);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message findById(@PathParam("id") Integer id) {
        return messageRepository.findById(id);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Long count() {
        return messageRepository.count();
    }

    @GET
    @Path("/count/reactive")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Long> countReactive() {
        return messageRepository.countReactive();
    }

    @RunOnVirtualThread
    @Route(path = "/messages/:id/route", methods = Route.HttpMethod.GET)
    public Uni<Message> findByIdRoute(@Param Integer id) {
        return messageRepository.findByIdReactive(id);
    }

    @GET
    @Path("/grpc")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean grpc() throws InterruptedException {
        Random random = new Random();
        int numThreads = 10000;
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        for(int i = 0; i < numThreads; i++) {
            executorService.submit(() -> messageService.findById(MessageRequest.newBuilder().setId(random.nextInt(999999)).build()));
        }
        executorService.shutdown();
        boolean finished = executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println((System.currentTimeMillis() - start) + " milliseconds");
        return finished;
    }
}