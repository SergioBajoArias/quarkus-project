package com.xeridia.resource;

import com.xeridia.model.Employee;
import com.xeridia.repository.EmployeeRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class EmployeeResourceTest {

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

    @Test
    void testListAllEndpoint() {
        List<Employee> employees = (List<Employee>) RestAssured.get("/employees").as(List.class);

        System.out.println();
    }

}