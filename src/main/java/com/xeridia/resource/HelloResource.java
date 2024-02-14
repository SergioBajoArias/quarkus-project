package com.xeridia.resource;

import com.xeridia.service.HelloService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

    HelloService helloService;

    public HelloResource(HelloService helloService) {
        this.helloService = helloService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/polite/{name}")
    public String hello(@PathParam("name") String name) {
        return helloService.politeHello(name);
    }
}
