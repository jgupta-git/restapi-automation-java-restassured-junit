package com.automationexercise.api.services;

import com.automationexercise.api.config.ApiConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DummyJsonProductService {

    private static final String BASE_URL = ApiConfig.dummyJsonBaseUrl();

    private RequestSpecification baseRequest() {
        return given()
                .baseUri(BASE_URL)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());
    }

    public Response getAllProducts() {
        return baseRequest()
                .when()
                .get("/products");
    }

    public Response getProductById(int id) {
        return baseRequest()
                .when()
                .get("/products/" + id);
    }

    public Response getProductsWithPagination(int limit, int skip) {
        return baseRequest()
                .queryParam("limit", limit)
                .queryParam("skip", skip)
                .when()
                .get("/products");
    }

    public Response getProductsWithSelect(String fields) {
        return baseRequest()
                .queryParam("select", fields)
                .when()
                .get("/products");
    }

    public Response getProductsSorted(String sortBy, String order) {
        return baseRequest()
                .queryParam("sortBy", sortBy)
                .queryParam("order", order)
                .when()
                .get("/products");
    }

    public Response getProductsByCategory(String category) {
        return baseRequest()
                .when()
                .get("/products/category/" + category);
    }

    public Response searchProducts(String query) {
        return baseRequest()
                .queryParam("q", query)
                .when()
                .get("/products/search");
    }
}
