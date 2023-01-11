package com.restful.cucumber.steps;

import com.restful.bookinginfo.AuthStep;
import com.restful.bookinginfo.BookingStep;
import com.restful.utils.TestUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;

import static org.hamcrest.CoreMatchers.equalTo;

public class RestBookerStepdefs {
    static ValidatableResponse response;
    static String firstName = "PrimUser" + TestUtils.getRandomValue();
    static String lastName = "PrimeUser" + TestUtils.getRandomValue();
    static int totalPrice = 500;
    static boolean depositPaid = true;
    static String checkIn = "2022-01-07";
    static String checkOut = "2022-01-20";
    static String additionalNeeds = "Breakfast";
    static String username = "admin";
    static String password = "password123";
    static int bookingId;
    static String token;

    @Steps
    BookingStep bookingSteps;
    @Steps
    AuthStep authSteps;

    @When("^I send a POST request using a valid payload to booking application$")
    public void iSendAPOSTRequestUsingAValidPayloadToBookingApplication() {
        response=bookingSteps.createBooking(firstName,lastName,totalPrice,depositPaid,checkIn,checkOut,additionalNeeds);
        bookingId = response.extract().path("bookingid");
    }

    @Then("^I get a valid response code (\\d+)$")
    public void iGetAValidResponseCode(int arg0) {
        response.statusCode(200).log().ifValidationFails();
    }

    @When("^I send GET request to booking application$")
    public void iSendGETRequestToBookingApplication() {
        response = bookingSteps.getBookingWithBookingId(bookingId);
        response.body("firstname", equalTo(firstName), "lastname", equalTo(lastName),
                "totalprice", equalTo(totalPrice), "depositpaid", equalTo(depositPaid),
                "bookingdates.checkin", equalTo(checkIn), "bookingdates.checkout", equalTo(checkOut),
                "additionalneeds", equalTo(additionalNeeds));
    }

    @When("^I send PUT request to booking application$")
    public void iSendPUTRequestToBookingApplication() {
        firstName = firstName + "_updated";
        lastName = lastName + "_updated";
        additionalNeeds = "Bed and Breakfast";
        token = authSteps.getAuthToken(username, password);
        response = bookingSteps.updateBooking(bookingId, firstName, lastName, totalPrice,
                depositPaid, checkIn, checkOut, additionalNeeds, token);

    }

    @When("^I send DELETE request to booking application$")
    public void iSendDELETERequestToBookingApplication() {
        response = bookingSteps.deleteBookingWithBookingId(bookingId, token);
    }

    @Then("^I get a valid response code for delete (\\d+)$")
    public void iGetAValidResponseCodeForDelete(int arg0) {
        bookingSteps.getBookingWithBookingId(bookingId).log().all().statusCode(404);
    }
}
