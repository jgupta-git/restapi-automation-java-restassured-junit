package com.automationexercise.api.steps;

import com.automationexercise.api.services.TokenAuthService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;

public class TokenAuthApiSteps {

    private final TokenAuthService tokenAuthService = new TokenAuthService();
    private String accessToken;
    private String refreshToken;

    @When("I login to DummyJSON with username {string} and password {string}")
    public void i_login_with(String username, String password) {
        Response response = tokenAuthService.login(username, password);
        CommonApiSteps.setResponse(response);
        if (response.statusCode() == 200) {
            accessToken = response.jsonPath().getString("accessToken");
            refreshToken = response.jsonPath().getString("refreshToken");
        }
    }

    @Given("I have logged in to DummyJSON with username {string} and password {string}")
    public void i_have_logged_in(String username, String password) {
        Response response = tokenAuthService.login(username, password);
        accessToken = response.jsonPath().getString("accessToken");
        refreshToken = response.jsonPath().getString("refreshToken");
        assertNotNull("Login failed — no access token returned", accessToken);
    }

    @When("I request the authenticated user profile")
    public void i_request_the_authenticated_user_profile() {
        CommonApiSteps.setResponse(tokenAuthService.getAuthUser(accessToken));
    }

    @When("I request the authenticated user profile without a token")
    public void i_request_profile_without_token() {
        CommonApiSteps.setResponse(tokenAuthService.getAuthUserWithoutToken());
    }

    @When("I request the authenticated user profile with token {string}")
    public void i_request_profile_with_invalid_token(String token) {
        CommonApiSteps.setResponse(tokenAuthService.getAuthUser(token));
    }

    @When("I refresh the auth session")
    public void i_refresh_the_auth_session() {
        CommonApiSteps.setResponse(tokenAuthService.refreshToken(refreshToken));
    }

    @Then("the HTTP status code should be {int}")
    public void the_http_status_code_should_be(int expectedStatus) {
        CommonApiSteps.getResponse().then()
                .statusCode(expectedStatus);
    }

    @Then("the response should contain a non-empty {string} field")
    public void the_response_should_contain_non_empty_field(String field) {
        CommonApiSteps.getResponse().then()
                .body(field, is(notNullValue()))
                .body(field, not(emptyString()));
    }

    @Then("the response should contain user details:")
    public void the_response_should_contain_user_details(Map<String, String> expected) {
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            CommonApiSteps.getResponse().then()
                    .body(entry.getKey(), equalTo(entry.getValue()));
        }
    }

    @Then("the response should contain field {string} with value {string}")
    public void the_response_should_contain_field_with_value(String field, String value) {
        CommonApiSteps.getResponse().then()
                .body(field, equalTo(value));
    }
}
