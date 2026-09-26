package com.automationexercise.api.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BrandService {

    public Response getAllBrands() {
        return given()
                .when()
                .get("/brandsList");
    }

    public Response putToBrandsList() {
        return given()
                .when()
                .put("/brandsList");
    }
}
