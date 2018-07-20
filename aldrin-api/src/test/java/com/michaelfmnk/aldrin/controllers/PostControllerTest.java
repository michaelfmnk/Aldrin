package com.michaelfmnk.aldrin.controllers;

import com.michaelfmnk.aldrin.BaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;

public class PostControllerTest extends BaseTest {

    @Test
    public void shouldLoadDataset() {
        assertTrue(true);
    }

    @Test
    public void shouldGetPostById() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/aldrin-api/posts/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(1))
                .body("title", equalTo("POST-TITLE-1"))
                .body("date", equalTo("2010-07-16T17:20:14.518"))
                .body("author_id", equalTo(1))
                .body("comments", hasSize(3))
                .body("comments[0].id", equalTo(2))
                .body("comments[0].content", equalTo("my fancy comment-2"))
                .body("comments[1].id", equalTo(1))
                .body("comments[1].content", equalTo("my fancy comment-1"))
                .body("comments[2].id", equalTo(3))
                .body("comments[2].content", equalTo("my fancy comment-3"))
                .body("likes", equalTo(2));
    }

    @Test
    public void shouldFailGetPostByIdOnUnauthorized() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/aldrin-api/posts/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }



}
