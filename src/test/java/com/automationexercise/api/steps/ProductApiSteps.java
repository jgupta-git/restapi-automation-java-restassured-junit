package com.automationexercise.api.steps;

import com.automationexercise.api.services.ProductService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ProductApiSteps {

    private final ProductService productService = new ProductService();

    @When("I send a GET request to the products list endpoint")
    public void i_send_a_get_request_to_the_products_list_endpoint() {
        CommonApiSteps.setResponse(productService.getAllProducts());
    }

    @When("I send a POST request to the products list endpoint")
    public void i_send_a_post_request_to_the_products_list_endpoint() {
        CommonApiSteps.setResponse(productService.postToProductsList());
    }

    @When("I search for products with keyword {string}")
    public void i_search_for_products_with_keyword(String keyword) {
        CommonApiSteps.setResponse(productService.searchProduct(keyword));
    }

    @When("I search for products without the search parameter")
    public void i_search_for_products_without_the_search_parameter() {
        CommonApiSteps.setResponse(productService.searchProductWithoutParam());
    }

    @Then("each product should have id, name, price, and category fields")
    public void each_product_should_have_required_fields() {
        CommonApiSteps.getResponse().then()
                .body("products[0].id", is(notNullValue()))
                .body("products[0].name", is(notNullValue()))
                .body("products[0].price", is(notNullValue()))
                .body("products[0].category", is(notNullValue()));
    }
}
