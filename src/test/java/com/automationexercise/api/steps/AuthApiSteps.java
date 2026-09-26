package com.automationexercise.api.steps;

import com.automationexercise.api.models.UserAccount;
import com.automationexercise.api.services.AuthService;
import com.automationexercise.api.services.UserAccountService;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class AuthApiSteps {

    private final AuthService authService = new AuthService();
    private final UserAccountService userAccountService = new UserAccountService();
    private String testEmail;
    private String testPassword;

    @Given("a test user account exists with email {string} and password {string}")
    public void a_test_user_account_exists(String email, String password) {
        testEmail = email;
        testPassword = password;

        UserAccount user = new UserAccount();
        user.setName("AuthTestUser");
        user.setEmail(email);
        user.setPassword(password);
        user.setTitle("Mr");
        user.setBirth_date("1");
        user.setBirth_month("1");
        user.setBirth_year("1990");
        user.setFirstname("Auth");
        user.setLastname("Test");
        user.setCompany("AuthCorp");
        user.setAddress1("100 Auth Street");
        user.setAddress2("");
        user.setCountry("United States");
        user.setZipcode("10001");
        user.setState("New York");
        user.setCity("New York");
        user.setMobile_number("5550001111");
        userAccountService.createAccount(user);
    }

    @When("I verify login without the email parameter using password {string}")
    public void i_verify_login_without_the_email_parameter(String password) {
        CommonApiSteps.setResponse(authService.verifyLoginWithoutEmail(password));
    }

    @When("I verify login with email {string} and password {string}")
    public void i_verify_login_with_email_and_password(String email, String password) {
        CommonApiSteps.setResponse(authService.verifyLogin(email, password));
    }

    @When("I send a DELETE request to the verify login endpoint")
    public void i_send_a_delete_request_to_the_verify_login_endpoint() {
        CommonApiSteps.setResponse(authService.deleteToVerifyLogin());
    }

    @After("@auth")
    public void cleanupTestUser() {
        if (testEmail != null) {
            userAccountService.deleteAccount(testEmail, testPassword);
        }
    }
}
