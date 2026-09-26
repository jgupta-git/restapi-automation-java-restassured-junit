package com.automationexercise.api.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthService {

    public Response verifyLogin(String email, String password) {
        return given()
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post("/verifyLogin");
    }

    public Response verifyLoginWithoutEmail(String password) {
        return given()
                .formParam("password", password)
                .when()
                .post("/verifyLogin");
    }

    public Response deleteToVerifyLogin() {
        return given()
                .when()
                .delete("/verifyLogin");
    }
}
