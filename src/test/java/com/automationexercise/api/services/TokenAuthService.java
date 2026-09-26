package com.automationexercise.api.services;

import com.automationexercise.api.config.ApiConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class TokenAuthService {

    private static final String BASE_URL = ApiConfig.dummyJsonBaseUrl();

    private RequestSpecification baseRequest() {
        return given()
                .baseUri(BASE_URL)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());
    }

    public Response login(String username, String password) {
        return baseRequest()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}")
                .when()
                .post("/auth/login");
    }

    public Response getAuthUser(String accessToken) {
        return baseRequest()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/auth/me");
    }

    public Response getAuthUserWithoutToken() {
        return baseRequest()
                .when()
                .get("/auth/me");
    }

    public Response refreshToken(String refreshToken) {
        return baseRequest()
                .contentType(ContentType.JSON)
                .body("{\"refreshToken\": \"" + refreshToken + "\"}")
                .when()
                .post("/auth/refresh");
    }
}
