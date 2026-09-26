package com.automationexercise.api.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TokenAuthService {

    private static final String BASE_URL = "https://dummyjson.com";

    public Response login(String username, String password) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}")
                .when()
                .post("/auth/login");
    }

    public Response getAuthUser(String accessToken) {
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/auth/me");
    }

    public Response getAuthUserWithoutToken() {
        return given()
                .baseUri(BASE_URL)
                .when()
                .get("/auth/me");
    }

    public Response refreshToken(String refreshToken) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body("{\"refreshToken\": \"" + refreshToken + "\"}")
                .when()
                .post("/auth/refresh");
    }
}
