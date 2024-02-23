package com.xeridia.testresource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.RolesRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.util.JsonSerialization;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class KeycloakXTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {
    private static String KEYCLOAK_SERVER_URL = "http://localhost:8081";
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