package com.restful.Testsuite;

import com.restful.bookinginfo.BookingStep;
import com.restful.testbase.TestBase;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;

@UseTestDataFrom("src/test/java/resources/testdata/studentinfo.csv")
@RunWith(SerenityParameterizedRunner.class)

public class BookingDataDrivenTest extends TestBase {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private String checkIn;
    private String checkout;
    private String additionalneeds;
    private String username;
    private String password;

    @Steps
    BookingStep bookingSteps;

    @Title("Data driven test for adding multiple users")
    @Test
    public void createMultipleUsers(){
        bookingSteps.createBooking(firstname,lastname,totalprice,depositpaid,checkIn,checkout,additionalneeds).statusCode(200);
    }
}
