package com.automationexercise.api.services;

import com.automationexercise.api.models.UserAccount;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserAccountService {

    public Response createAccount(UserAccount user) {
        return given()
                .formParam("name", user.getName())
                .formParam("email", user.getEmail())
                .formParam("password", user.getPassword())
                .formParam("title", user.getTitle())
                .formParam("birth_date", user.getBirth_date())
                .formParam("birth_month", user.getBirth_month())
                .formParam("birth_year", user.getBirth_year())
                .formParam("firstname", user.getFirstname())
                .formParam("lastname", user.getLastname())
                .formParam("company", user.getCompany())
                .formParam("address1", user.getAddress1())
                .formParam("address2", user.getAddress2())
                .formParam("country", user.getCountry())
                .formParam("zipcode", user.getZipcode())
                .formParam("state", user.getState())
                .formParam("city", user.getCity())
                .formParam("mobile_number", user.getMobile_number())
                .when()
                .post("/createAccount");
    }

    public Response deleteAccount(String email, String password) {
        return given()
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .delete("/deleteAccount");
    }

    public Response updateAccount(UserAccount user) {
        return given()
                .formParam("name", user.getName())
                .formParam("email", user.getEmail())
                .formParam("password", user.getPassword())
                .formParam("title", user.getTitle())
                .formParam("birth_date", user.getBirth_date())
                .formParam("birth_month", user.getBirth_month())
                .formParam("birth_year", user.getBirth_year())
                .formParam("firstname", user.getFirstname())
                .formParam("lastname", user.getLastname())
                .formParam("company", user.getCompany())
                .formParam("address1", user.getAddress1())
                .formParam("address2", user.getAddress2())
                .formParam("country", user.getCountry())
                .formParam("zipcode", user.getZipcode())
                .formParam("state", user.getState())
                .formParam("city", user.getCity())
                .formParam("mobile_number", user.getMobile_number())
                .when()
                .put("/updateAccount");
    }

    public Response getUserByEmail(String email) {
        return given()
                .queryParam("email", email)
                .when()
                .get("/getUserDetailByEmail");
    }
}
