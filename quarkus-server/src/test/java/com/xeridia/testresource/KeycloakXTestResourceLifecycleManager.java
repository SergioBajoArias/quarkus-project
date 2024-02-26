package com.xeridia.testresource;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.restassured.RestAssured;
import org.keycloak.representations.AccessTokenResponse;

import java.util.Map;

public class KeycloakXTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {

    private static String KEYCLOAK_SERVER_URL = "http://localhost:8082";
    private static final String KEYCLOAK_REALM = "quarkus";
    private static final String KEYCLOAK_SERVICE_CLIENT = "backend-service";

    @SuppressWarnings("resource")
    @Override
    public Map<String, String> start() {
        return Map.of(
                "quarkus.test.admin.token", getAccessToken("admin", "admin"),
                "quarkus.test.user.token", getAccessToken("alice", "alice")
        );
    }

    @Override
    public void stop() {

    }

    public static String getAccessToken(String userName, String password) {
        return RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("client_id", KEYCLOAK_SERVICE_CLIENT)
                .formParam("client_secret", "secret")
                .formParam("username", userName)
                .formParam("password", password)
                .formParam("grant_type", "password")
                .when()
                .post(KEYCLOAK_SERVER_URL + "/realms/" + KEYCLOAK_REALM + "/protocol/openid-connect/token")
                .as(AccessTokenResponse.class).getToken();
    }
}