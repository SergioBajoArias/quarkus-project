package com.xeridia.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class HelloResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    void testPoliteHelloEndpoint() {
        given()
                .when().get("/hello/polite/Sergio")
                .then()
                .statusCode(200)
                .body(is("Good morning Sergio"));
    }
}