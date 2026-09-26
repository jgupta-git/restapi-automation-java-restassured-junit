package com.automationexercise.api.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@SuppressWarnings("deprecation")
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/token_auth_api.feature",
        glue = {"com.automationexercise.api.steps", "com.automationexercise.api.hooks"},
        plugin = {"pretty", "summary", "json:target/cucumber-reports/token_auth_api.json",
                 //"html:target/cucumber-reports/token_auth_api-report.html"
        }
)
public class TokenAuthApiRunner {
}
