package com.automationexercise.api.hooks;

import com.automationexercise.api.config.ApiConfig;
import com.automationexercise.api.steps.CommonApiSteps;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class Hooks {

    private static boolean initialized = false;

    @Before
    public void setUp() {
        if (!initialized) {
            RestAssured.baseURI = ApiConfig.baseUrl();
            RestAssured.defaultParser = Parser.JSON;
            RestAssured.registerParser("text/html", Parser.JSON);
            RestAssured.registerParser("text/html; charset=utf-8", Parser.JSON);
            RestAssured.filters(
                    new RequestLoggingFilter(),
                    new ResponseLoggingFilter(),
                    (req, res, ctx) -> {
                        Response response = ctx.next(req, res);
                        String contentType = response.contentType();
                        if (contentType != null && contentType.contains("text/html")) {
                            ((RestAssuredResponseImpl) response).setContentType("application/json");
                        }
                        return response;
                    }
            );
            initialized = true;
        }
    }

    @AfterStep
    public void logResponseToReport(Scenario scenario) {
        if (CommonApiSteps.needsLogging()) {
            Response response = CommonApiSteps.getResponse();
            String output = "HTTP " + response.statusCode() + "\n" + response.asPrettyString();
            scenario.attach(output.getBytes(), "text/plain", "API Response");
            CommonApiSteps.markLogged();
        }
    }
}
