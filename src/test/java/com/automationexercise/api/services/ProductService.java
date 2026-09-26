package com.automationexercise.api.services;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductService {

    public Response getAllProducts() {
        return given()
                .when()
                .get("/productsList");
    }

    public Response postToProductsList() {
        return given()
                .when()
                .post("/productsList");
    }

    public Response searchProduct(String searchTerm) {
        return given()
                .formParam("search_product", searchTerm)
                .when()
                .post("/searchProduct");
    }

    public Response searchProductWithoutParam() {
        return given()
                .when()
                .post("/searchProduct");
    }
}
