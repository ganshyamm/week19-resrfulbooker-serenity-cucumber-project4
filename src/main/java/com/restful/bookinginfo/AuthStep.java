package com.restful.bookinginfo;

import com.gargoylesoftware.htmlunit.javascript.host.fetch.Headers;
import com.restful.constants.EndPoints;
import com.restful.constants.Params;
import com.restful.model.AuthPojo;

import com.restful.utils.TestUtils;

import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class AuthStep {

    @Step("Get auth token with username: {0} and password: {1}")
    public String getAuthToken(String username, String password) {
        AuthPojo authPojo = AuthPojo.getAuthBody(username, password);
        return SerenityRest.given().log().ifValidationFails()

                .header(Params.CONTENT_TYPE, "application/json")
                .body(TestUtils.jsonToString(authPojo))
                .when()
                .post(EndPoints.AUTH)
                .then().log().ifValidationFails()
                .statusCode(200).extract().path("token");
    }

}
