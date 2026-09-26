package com.automationexercise.api.steps;

import com.automationexercise.api.models.UserAccount;
import com.automationexercise.api.services.UserAccountService;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class UserAccountApiSteps {

    private final UserAccountService userAccountService = new UserAccountService();
    private String activeEmail;
    private String activePassword;
    private boolean accountDeleted = false;

    private UserAccount buildFromTable(Map<String, String> data) {
        UserAccount user = new UserAccount();
        user.setName(data.get("name"));
        user.setEmail(data.get("email"));
        user.setPassword(data.get("password"));
        user.setTitle(data.get("title"));
        user.setBirth_date(data.get("birth_date"));
        user.setBirth_month(data.get("birth_month"));
        user.setBirth_year(data.get("birth_year"));
        user.setFirstname(data.get("firstname"));
        user.setLastname(data.get("lastname"));
        user.setCompany(data.get("company"));
        user.setAddress1(data.get("address1"));
        user.setAddress2(data.get("address2"));
        user.setCountry(data.get("country"));
        user.setZipcode(data.get("zipcode"));
        user.setState(data.get("state"));
        user.setCity(data.get("city"));
        user.setMobile_number(data.get("mobile_number"));
        return user;
    }

    @Given("any existing account with email {string} and password {string} is cleaned up")
    public void any_existing_account_is_cleaned_up(String email, String password) {
        userAccountService.deleteAccount(email, password);
    }

    @Given("a user account has been created with:")
    public void a_user_account_has_been_created_with(Map<String, String> data) {
        UserAccount user = buildFromTable(data);
        activeEmail = user.getEmail();
        activePassword = user.getPassword();
        userAccountService.deleteAccount(activeEmail, activePassword);
        userAccountService.createAccount(user);
    }

    @Given("the user account with email {string} and password {string} has been deleted")
    public void the_user_account_has_been_deleted(String email, String password) {
        userAccountService.deleteAccount(email, password);
        accountDeleted = true;
    }

    @When("I create a user account with the following details:")
    public void i_create_a_user_account_with_the_following_details(Map<String, String> data) {
        UserAccount user = buildFromTable(data);
        activeEmail = user.getEmail();
        activePassword = user.getPassword();
        CommonApiSteps.setResponse(userAccountService.createAccount(user));
    }

    @When("I get the user account by email {string}")
    public void i_get_the_user_account_by_email(String email) {
        CommonApiSteps.setResponse(userAccountService.getUserByEmail(email));
    }

    @When("I update the user account {string} with password {string} and:")
    public void i_update_the_user_account_with(String email, String password, Map<String, String> data) {
        UserAccount user = buildFromTable(data);
        user.setEmail(email);
        user.setPassword(password);
        CommonApiSteps.setResponse(userAccountService.updateAccount(user));
    }

    @When("I delete the user account with email {string} and password {string}")
    public void i_delete_the_user_account(String email, String password) {
        CommonApiSteps.setResponse(userAccountService.deleteAccount(email, password));
        accountDeleted = true;
    }

    @Then("the user detail should match:")
    public void the_user_detail_should_match(Map<String, String> expected) {
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            String jsonField = entry.getKey();
            if (jsonField.equals("firstname")) jsonField = "first_name";
            else if (jsonField.equals("lastname")) jsonField = "last_name";
            CommonApiSteps.getResponse().then()
                    .body("user." + jsonField, equalTo(entry.getValue()));
        }
    }

    @After("@account")
    public void cleanupAccount() {
        if (activeEmail != null && !accountDeleted) {
            userAccountService.deleteAccount(activeEmail, activePassword);
        }
    }
}
