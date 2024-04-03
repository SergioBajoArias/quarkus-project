package com.xeridia.ws.resource;

import com.xeridia.ws.model.Message;
import com.xeridia.ws.repository.MessageRepository;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/messages")
public class MessageResource {

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
}