package com.xeridia.resource;

import com.xeridia.model.Employee;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
class EmployeeResourceTest {
    @Test
    void testPersistEndpoint() {
        Employee employeeToPersist = Employee.builder()
                .name("Sergio")
                .age(41)
                .build();

        given()
          .with()
                .contentType(ContentType.JSON)
                .body(employeeToPersist)
          .when()
                .post("/employees")
          .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(1))
                .body("name", equalTo(employeeToPersist.getName()))
                .body("age", equalTo(employeeToPersist.getAge()));

    }
}