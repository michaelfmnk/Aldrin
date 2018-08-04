package com.michaelfmnk.aldrin.controllers.comment;

import com.michaelfmnk.aldrin.BaseTest;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DeleteCommentTest extends BaseTest {

    @Test
    public void shouldDeleteComment() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/aldrin-api/comments/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        assertThat(new Request(dataSource, "SELECT content FROM comments WHERE comment_id=1"))
                .hasNumberOfRows(0);
    }

    @Test
    public void shouldNotDeleteNotOwnComment() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/aldrin-api/comments/3")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("title", equalTo("FORBIDDEN"))
                .body("status", equalTo(403))
                .body("detail", equalTo("Access is denied"))
                .body("time_stamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.security.access.AccessDeniedException"));

        assertThat(new Request(dataSource, "SELECT content FROM comments WHERE comment_id=3"))
                .hasNumberOfRows(1);
    }

    @Test
    public void shouldDeleteAnyCommentByAdmin() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(adminHeaders)
                .when()
                .delete("/aldrin-api/comments/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(adminHeaders)
                .when()
                .delete("/aldrin-api/comments/3")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        assertThat(new Request(dataSource, "SELECT content FROM comments WHERE comment_id=1"))
                .hasNumberOfRows(0);
    }
}
