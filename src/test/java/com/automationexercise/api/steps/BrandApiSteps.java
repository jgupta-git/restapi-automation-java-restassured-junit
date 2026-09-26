package com.automationexercise.api.steps;

import com.automationexercise.api.services.BrandService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class BrandApiSteps {

    private final BrandService brandService = new BrandService();

    @When("I send a GET request to the brands list endpoint")
    public void i_send_a_get_request_to_the_brands_list_endpoint() {
        CommonApiSteps.setResponse(brandService.getAllBrands());
    }

    @When("I send a PUT request to the brands list endpoint")
    public void i_send_a_put_request_to_the_brands_list_endpoint() {
        CommonApiSteps.setResponse(brandService.putToBrandsList());
    }

    @Then("each brand should have id and brand fields")
    public void each_brand_should_have_required_fields() {
        CommonApiSteps.getResponse().then()
                .body("brands[0].id", is(notNullValue()))
                .body("brands[0].brand", is(notNullValue()));
    }
}
