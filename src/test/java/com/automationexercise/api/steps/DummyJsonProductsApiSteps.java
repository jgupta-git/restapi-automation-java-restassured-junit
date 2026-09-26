package com.automationexercise.api.steps;

import com.automationexercise.api.services.DummyJsonProductService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class DummyJsonProductsApiSteps {

    private final DummyJsonProductService productService = new DummyJsonProductService();

    private static final ResponseSpecification DUMMYJSON_RESPONSE_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType("application/json")
            .expectBody("products", notNullValue())
            .expectBody("total", greaterThan(0))
            .build();

    // --- Query Parameters: Pagination & Filtering ---

    @When("user fetches products with limit {int} and skip {int}")
    public void user_fetches_products_with_limit_and_skip(int limit, int skip) {
        CommonApiSteps.setResponse(productService.getProductsWithPagination(limit, skip));
    }

    @When("user fetches products selecting fields {string}")
    public void user_fetches_products_selecting_fields(String fields) {
        CommonApiSteps.setResponse(productService.getProductsWithSelect(fields));
    }

    @When("user fetches products sorted by {string} in {string} order")
    public void user_fetches_products_sorted_by(String sortBy, String order) {
        CommonApiSteps.setResponse(productService.getProductsSorted(sortBy, order));
    }

    @When("user fetches products in category {string}")
    public void user_fetches_products_in_category(String category) {
        CommonApiSteps.setResponse(productService.getProductsByCategory(category));
    }

    @When("user searches DummyJSON products with query {string}")
    public void user_searches_dummyjson_products(String query) {
        CommonApiSteps.setResponse(productService.searchProducts(query));
    }

    @When("user fetches product with id {int}")
    public void user_fetches_product_with_id(int id) {
        CommonApiSteps.setResponse(productService.getProductById(id));
    }

    @When("user fetches all DummyJSON products")
    public void user_fetches_all_dummyjson_products() {
        CommonApiSteps.setResponse(productService.getAllProducts());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Then("the products should be sorted by {string} in {string} order")
    public void the_products_should_be_sorted_by(String field, String order) {
        List<Comparable> values = CommonApiSteps.getResponse().jsonPath().getList("products." + field);
        for (int i = 1; i < values.size(); i++) {
            int cmp = values.get(i).compareTo(values.get(i - 1));
            if ("asc".equals(order)) {
                assertTrue("Products not sorted ascending by " + field + " at index " + i,
                        cmp >= 0);
            } else {
                assertTrue("Products not sorted descending by " + field + " at index " + i,
                        cmp <= 0);
            }
        }
    }

    // --- Hamcrest Matchers & Chained Assertions ---

    @Then("the response should match the following assertions:")
    public void the_response_should_match_assertions(List<Map<String, String>> table) {
        var validatable = CommonApiSteps.getResponse().then();
        for (Map<String, String> row : table) {
            String field = row.get("field");
            String matcher = row.get("matcher");
            String value = row.get("value");
            switch (matcher) {
                case "notNull":
                    validatable.body(field, notNullValue());
                    break;
                case "notEmpty":
                    validatable.body(field, not(emptyOrNullString()));
                    break;
                case "notEmptyList":
                    validatable.body(field, is(notNullValue()));
                    validatable.body(field + ".size()", greaterThan(0));
                    break;
                case "greaterThan":
                    float gtActual = CommonApiSteps.getResponse().jsonPath().getFloat(field);
                    assertTrue(field + " expected > " + value + " but was " + gtActual,
                            gtActual > Float.parseFloat(value));
                    break;
                case "between":
                    String[] bounds = value.split(",");
                    float btActual = CommonApiSteps.getResponse().jsonPath().getFloat(field);
                    float btMin = Float.parseFloat(bounds[0].trim());
                    float btMax = Float.parseFloat(bounds[1].trim());
                    assertTrue(field + " expected between " + btMin + " and " + btMax + " but was " + btActual,
                            btActual >= btMin && btActual <= btMax);
                    break;
                case "equalTo":
                    validatable.body(field, equalTo(value));
                    break;
                case "equalToInt":
                    validatable.body(field, equalTo(Integer.parseInt(value)));
                    break;
                case "listSize":
                    validatable.body(field + ".size()", equalTo(Integer.parseInt(value)));
                    break;
                case "everyItemNotNull":
                    validatable.body(field + "." + value, everyItem(notNullValue()));
                    break;
                case "everyItemNull":
                    List<Object> values = CommonApiSteps.getResponse().jsonPath().getList(field + "." + value);
                    assertTrue("Field '" + value + "' should not be present in " + field,
                            values == null || values.stream().allMatch(v -> v == null));
                    break;
                case "everyItemEquals":
                    validatable.body(field, everyItem(equalTo(value)));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown matcher: " + matcher);
            }
        }
    }

    // --- JSON Schema Validation ---

    @Then("the response should match the {string} JSON schema")
    public void the_response_should_match_json_schema(String schemaName) {
        CommonApiSteps.getResponse().then()
                .body(matchesJsonSchemaInClasspath("schemas/" + schemaName + ".json"));
    }

    // --- Response Spec & Response Time ---

    @Then("the response should conform to the standard DummyJSON response spec")
    public void the_response_should_conform_to_spec() {
        CommonApiSteps.getResponse().then()
                .spec(DUMMYJSON_RESPONSE_SPEC);
    }

    @Then("the response time should be less than {long} milliseconds")
    public void the_response_time_should_be_less_than(long maxTime) {
        CommonApiSteps.getResponse().then()
                .time(lessThan(maxTime));
    }

}
