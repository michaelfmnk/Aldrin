package com.michaelfmnk.aldrin.controllers.user;

import com.michaelfmnk.aldrin.BaseTest;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


public class GetUserPostsTest extends BaseTest {

    @Test
    public void shouldGetAllUserPosts() {
        given()
                .headers(headers)
                .when()
                .get("aldrin-api/users/1/posts")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("total", equalTo(3))
                .body("data", hasSize(3))
                // check first and second
                .body("data[0].id", equalTo(2))
                .body("data[0].title", equalTo("POST-TITLE-2"))
                .body("data[0].date", equalTo("2014-07-10T17:20:14.518"))
                .body("data[0].author_id", equalTo(1))
                .body("data[0].comments", hasSize(0))
                .body("data[0].likes", equalTo(0))

                .body("data[1].id", equalTo(1))
                .body("data[1].title", equalTo("POST-TITLE-1"))
                .body("data[1].date", equalTo("2010-07-16T17:20:14.518"))
                .body("data[1].author_id", equalTo(1))
                .body("data[1].comments", hasSize(3))
                .body("data[1].comments[0].content", equalTo("my fancy comment-2"))
                .body("data[1].comments[1].content", equalTo("my fancy comment-1"))
                .body("data[1].comments[2].content", equalTo("my fancy comment-3"))
                .body("data[1].likes", equalTo(2));

    }

    @Test
    public void shouldGetAllUserPostsPagination() {
        given()
                .headers(headers)
                .param("asc", true)
                .param("limit", 2)
                .when()
                .get("aldrin-api/users/1/posts")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("total", equalTo(3))
                .body("data", hasSize(2))

                .body("data[0].id", equalTo(4))
                .body("data[0].title", equalTo("POST-TITLE-4"))
                .body("data[0].date", equalTo("2000-07-19T17:20:14.518"))
                .body("data[0].author_id", equalTo(1))
                .body("data[0].comments", hasSize(0))
                .body("data[0].likes", equalTo(0))

                .body("data[1].id", equalTo(1))
                .body("data[1].title", equalTo("POST-TITLE-1"))
                .body("data[1].date", equalTo("2010-07-16T17:20:14.518"))
                .body("data[1].author_id", equalTo(1))
                .body("data[1].comments", hasSize(3))
                .body("data[1].comments[0].content", equalTo("my fancy comment-2"))
                .body("data[1].comments[1].content", equalTo("my fancy comment-1"))
                .body("data[1].comments[2].content", equalTo("my fancy comment-3"))
                .body("data[1].likes", equalTo(2));

    }
}
