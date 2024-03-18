package com.xeridia.resource;

import com.xeridia.model.Employee;
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
public class EmployeeResourceTest {

    @Test
    void testPersistEndpoint() {
        String adminToken = ConfigProvider.getConfig().getValue("quarkus.test.admin.token", String.class);

        Employee employeeToPersist = new Employee(null, "Sergio", 41);

        given()
                .auth().oauth2(adminToken)
        .with()
            .contentType(ContentType.JSON)
            .body(employeeToPersist)
        .when()
            .post("/api/admin/employees")
        .then()
            .statusCode(200)
            .assertThat()
                .body("id", equalTo(1))
                .body("name", equalTo(employeeToPersist.getName()))
                .body("age", equalTo(employeeToPersist.getAge()));
    }

    @Test
    void testListAllEndpoint() {
        String adminToken = ConfigProvider.getConfig().getValue("quarkus.test.admin.token", String.class);

        List<Employee> employees = given()
                .auth().oauth2(adminToken)
                .with()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/admin/employees").as(List.class);

        System.out.println();
    }

}