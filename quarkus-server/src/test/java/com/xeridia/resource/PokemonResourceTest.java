package com.xeridia.resource;

import com.xeridia.testresource.KeycloakXTestResourceLifecycleManager;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

@QuarkusTest
@QuarkusTestResource(KeycloakXTestResourceLifecycleManager.class)
@TestHTTPEndpoint(PokemonResource.class)
public class PokemonResourceTest {

    @TestHTTPResource("/api/users/pokemons")
    URL pokemonsEndpoint;

    @TestHTTPResource("/api/users/pokemons/1")
    URL pokemonsDetailEndpoint;

    @Test
    void testListEndpoint() {
        String userToken = ConfigProvider.getConfig().getValue("quarkus.test.user.token", String.class);

        given()
            .auth().oauth2(userToken)
        .with()
            .contentType(ContentType.JSON)
        .when()
            .get(pokemonsEndpoint)
        .then()
            .statusCode(200)
            .assertThat()
                .body("count", equalTo(1302))
                .body("next", equalTo("https://pokeapi.co/api/v2/pokemon?offset=20&limit=20"))
                .body("prev", nullValue())
                .body("results[0].name", equalTo("bulbasaur"));
    }

    @Test
    void testFindByIdEndpoint() {
        String userToken = ConfigProvider.getConfig().getValue("quarkus.test.user.token", String.class);

        // Non-cached request
        given()
            .auth().oauth2(userToken)
        .with()
            .contentType(ContentType.JSON)
        .when()
            .get(pokemonsDetailEndpoint)
        .then()
            .statusCode(200)
            .time(greaterThan(2000L))           // Takes longer than 2000 milliseconds
            .assertThat()
                .body("name", equalTo("bulbasaur"));

        // Cached request
        given()
            .auth().oauth2(userToken)
        .with()
            .contentType(ContentType.JSON)
        .when()
            .get(pokemonsDetailEndpoint)
        .then()
            .statusCode(200)
            .time(lessThan(2000L))              // Takes fewer than 2000 milliseconds
            .assertThat()
                .body("name", equalTo("bulbasaur"));
    }
}