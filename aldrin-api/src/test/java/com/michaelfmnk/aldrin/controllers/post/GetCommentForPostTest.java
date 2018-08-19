package com.michaelfmnk.aldrin.controllers.post;

import com.michaelfmnk.aldrin.BaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class GetCommentForPostTest extends BaseTest {

    @Test
    public void shouldGetCommentsForPost() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/aldrin-api/posts/1/comments")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(3))
                .body("data[0].id", equalTo(2))
                .body("data[0].content", equalTo("my fancy comment-2"))
                .body("data[0].date", equalTo("2060-07-26T13:20:14.518"))
                .body("data[1].id", equalTo(1))
                .body("data[1].content", equalTo("my fancy comment-1"))
                .body("data[1].date", equalTo("2050-07-26T13:20:14.518"))
                .body("data[2].id", equalTo(3))
                .body("data[2].content", equalTo("my fancy comment-3"))
                .body("data[2].date", equalTo("2010-07-26T13:20:14.518"))
                .body("total", equalTo(3));
    }

    @Test
    public void shouldGetCommentsForPostPaginationCheck() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .param("asc", true)
                .param("offset", 1)
                .param("limit", 1)
                .when()
                .get("/aldrin-api/posts/1/comments")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(1))
                .body("data[0].id", equalTo(1))
                .body("data[0].content", equalTo("my fancy comment-1"))
                .body("data[0].date", equalTo("2050-07-26T13:20:14.518"))
                .body("total", equalTo(3));
    }

}
