package com.xeridia.resource;

import com.xeridia.model.Employee;
import com.xeridia.model.Fruit;
import com.xeridia.testresource.KeycloakXTestResourceLifecycleManager;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
@QuarkusTestResource(KeycloakXTestResourceLifecycleManager.class)
public class FruitResourceTest {

    @Test
    void testPersistEndpoint() {
        String adminToken = ConfigProvider.getConfig().getValue("quarkus.test.admin.token", String.class);

        Fruit fruitToPersist = new Fruit(null, "Apple", 41);

        given()
                .auth().oauth2(adminToken)
        .with()
            .contentType(ContentType.JSON)
            .body(fruitToPersist)
        .when()
            .post("/api/admin/fruits")
        .then()
            .statusCode(201)
            .assertThat()
                .body("id", equalTo(1))
                .body("name", equalTo(fruitToPersist.getName()))
                .body("amount", equalTo(fruitToPersist.getAmount()));
    }

    @Test
    void testListAllEndpoint() {
        String adminToken = ConfigProvider.getConfig().getValue("quarkus.test.admin.token", String.class);

        given()
                .auth().oauth2(adminToken)
                .with()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/admin/fruits")
                .then()
                .statusCode(200);
    }
}