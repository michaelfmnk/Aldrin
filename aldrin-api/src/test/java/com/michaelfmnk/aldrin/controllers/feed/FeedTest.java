package com.michaelfmnk.aldrin.controllers.feed;

import com.michaelfmnk.aldrin.BaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class FeedTest extends BaseTest {

    @Test
    public void shouldGetFeed() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/aldrin-api/feed")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(6))
                .body("total", equalTo(6))

                .body("data[0].id", equalTo(8))
                .body("data[0].title", equalTo("POST-TITLE-8"))
                .body("data[0].date", equalTo("2074-07-16T17:20:14.518"))
                .body("data[0].author_id", equalTo(2))
                .body("data[0].comments", hasSize(2))
                .body("data[0].likes", equalTo(0))
                .body("data[0].liked", equalTo(false))
                .body("data[5].id", equalTo(7))
                .body("data[5].title", equalTo("POST-TITLE-7"))
                .body("data[5].date", equalTo("1994-07-16T17:20:14.518"))
                .body("data[5].author_id", equalTo(2))
                .body("data[5].comments", hasSize(2))
                .body("data[5].likes", equalTo(2))
                .body("data[5].liked", equalTo(true));

    }

    @Test
    public void shouldGetFeedPaginationCheck() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .param("asc", true)
                .param("offset", 2)
                .param("limit", 1)
                .when()
                .get("/aldrin-api/feed")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(1))
                .body("total", equalTo(6))

                .body("data[0].id", equalTo(4))
                .body("data[0].title", equalTo("POST-TITLE-4"))
                .body("data[0].date", equalTo("2014-07-16T17:20:14.518"))
                .body("data[0].author_id", equalTo(2))
                .body("data[0].comments", hasSize(0))
                .body("data[0].likes", equalTo(0));

    }
}
