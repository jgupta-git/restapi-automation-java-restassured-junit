package com.automationexercise.api.steps;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;

public class CommonApiSteps {

    private static final ThreadLocal<Response> currentResponse = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> responseLogged = ThreadLocal.withInitial(() -> true);

    public static void setResponse(Response response) {
        currentResponse.set(response);
        responseLogged.set(false);
    }

    public static Response getResponse() {
        return currentResponse.get();
    }

    public static boolean needsLogging() {
        return !responseLogged.get() && currentResponse.get() != null;
    }

    public static void markLogged() {
        responseLogged.set(true);
    }

    @Then("the response code should be {int}")
    public void the_response_code_should_be(int expectedCode) {
        getResponse().then()
                .body("responseCode", equalTo(expectedCode));
    }

    @Then("the response message should be {string}")
    public void the_response_message_should_be(String expectedMessage) {
        getResponse().then()
                .body("message", equalTo(expectedMessage));
    }

    @Then("the response should contain a non-empty {string} list")
    public void the_response_should_contain_a_non_empty_list(String listName) {
        getResponse().then()
                .body(listName, is(notNullValue()))
                .body(listName + ".size()", greaterThan(0));
    }

    @Then("the response should contain a {string} list")
    public void the_response_should_contain_a_list(String listName) {
        getResponse().then()
                .body(listName, is(notNullValue()));
    }
}
