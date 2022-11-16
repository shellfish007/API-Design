package a0;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class APITest {
    static String baseUrl = "http://localhost:4567/DAI.com";

    private void addEvent1() {
        when().
                post(baseUrl + "/event?date=2022-11-01&name=General Checking");
    }

    private void addEvent2() {
        when().
                post(baseUrl + "/event?date=2022-11-01&name=Regular Checking");
    }

    @BeforeAll
    public static void startServer() {
        API.getCurrentTime();
        API.Event();
        API.clearEvents();
    }

    @AfterEach
    public void clearServerData() {
        when().
                get(baseUrl + "/events/clearAll").
                then().
                statusCode(200);
    }

    @Test public void getCurrentDate() {
        /* Example response body:

        {"date" : "2022-11-01 15:00:00"}

        */
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        when().
                get(baseUrl + "/gettime/date").
                then().
                statusCode(200).
                assertThat().
                body("date", equalTo(dtf.format(now)));
    }

    @Test public void getCurrentDateError() {
        // redundant path parameter
        when().
                get(baseUrl + "/gettime/date?zone=USA").
                then().
                statusCode(400);
    }

    @Test public void getCurrentDay() {
        /* Example response body:

        {"day" : "01"}

        */
        LocalDateTime now = LocalDateTime.now();
        when().
                get(baseUrl + "/gettime/day").
                then().
                statusCode(200).
                body("day", equalTo(now.getDayOfMonth()));
    }

    @Test public void getCurrentDayError() {
        // redundant path parameter
        when().
                get(baseUrl + "/gettime/day?zone=USA").
                then().
                statusCode(400);
    }

    @Test public void getCurrentMonth() {
        /* Example response body:

        {"month" : "11"}

        */
        LocalDateTime now = LocalDateTime.now();
        when().
                get(baseUrl + "/gettime/month").
                then().
                statusCode(200).
                body("month", equalTo(now.getMonthValue()));
    }

    @Test public void getCurrentMonthError() {
        // redundant path parameter
        when().
                get(baseUrl + "/gettime/month?zone=USA").
                then().
                statusCode(400);
    }

    @Test public void getCurrentYear() {
        /* Example response body:

        {"year" : "2022"}

        */
        LocalDateTime now = LocalDateTime.now();
        when().
                get(baseUrl + "/gettime/year").
                then().
                statusCode(200).
                body("year", equalTo(now.getYear()));
    }

    @Test public void getCurrentYearError() {
        // redundant path parameter
        when().
                get(baseUrl + "/gettime/year?zone=USA").
                then().
                statusCode(400);
    }

    @Test public void addEvent() {
        /* Example response body:

        {"id" : "1",
         "date" : "2022-11-01 15:00:00",
         "name" : "General Checking"}

        */
        when().
                post(baseUrl + "/event?date=2022-11-01&name=General Checking").
                then().
                statusCode(200).
                body("id", equalTo("0"),
                        "date", equalTo("2022-11-01"),
                        "name", equalTo("General Checking"));
    }

    @Test public void addEventError() {
        // path parameter missing
        when().
                post(baseUrl + "/event?date=2022-11-01").
                then().
                statusCode(400); // bad request
    }

    @Test public void modifyEvent() {
        /* Example response body:

        {"id" : "1",
         "date" : "2022-12-01 14:00:00",
         "name" : "General Checking"}

        */
        addEvent1();
        when().
                patch(baseUrl + "/event/0?date=2022-12-01").
                then().
                statusCode(200).
                body("date", equalTo("2022-12-01"));
    }

    @Test public void modifyEventError() {
        // event not modified
        addEvent1();
        when().
                patch(baseUrl + "/event/0?date=2022-11-01").
                then().
                statusCode(304); // not modified
    }

    @Test public void getEvent() {
        addEvent1();
        when().
                get(baseUrl + "/event/0").
                then().
                statusCode(200).
                body("date", equalTo("2022-11-01"));
    }

    @Test public void deleteEvent() {
        addEvent1();
        when().
                delete(baseUrl + "/event/0").
                then().
                statusCode(204);
    }

    @Test public void deleteEventError() {
        // deleting an event that does not exist
        addEvent1();
        when().
                delete(baseUrl + "/event/2").
                then().
                statusCode(404);
    }

    @Test public void getEvents() {
        /* Example response body:

        {
        {"id" : "1",
         "date" : "2022-11-01 15:00:00",
         "name" : "General Checking"},

        {"id" : "2",
         "date" : "2022-11-01 16:00:00",
         "name" : "Recheck"}
        }

        */
        addEvent1();
        addEvent2();
        when().
                get(baseUrl + "/event?date=2022-11-01").
                then().
                statusCode(200).
                body("id", Matchers.hasItem("0"), "id", Matchers.hasItem("1"));
    }

    @Test public void getEventsError() {
        // path parameter format incorrect
        addEvent();
        when().
                get(baseUrl + "/event").
                then().
                statusCode(400); // bad request
    }
}
