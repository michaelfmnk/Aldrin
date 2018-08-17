package com.michaelfmnk.aldrin.controllers.post;

import com.michaelfmnk.aldrin.BaseTest;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DeletePostTest extends BaseTest {

    @Test
    public void shouldDeletePost() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/aldrin-api/posts/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/aldrin-api/posts/2")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        assertThat(new Request(dataSource, "SELECT * FROM posts WHERE post_id=1 OR post_id=2"))
                .hasNumberOfRows(0);
    }

    @Test
    public void shouldNotDeleteNotOwnPost() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/aldrin-api/posts/3")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("title", equalTo("FORBIDDEN"))
                .body("status", equalTo(403))
                .body("detail", equalTo("Access is denied"))
                .body("timestamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.security.access.AccessDeniedException"));

        assertThat(new Request(dataSource, "SELECT * FROM posts WHERE post_id=3"))
                .hasNumberOfRows(1);
    }

    @Test
    public void shouldDeleteAnyCommentByAdmin() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(adminHeaders)
                .when()
                .delete("/aldrin-api/posts/2")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(adminHeaders)
                .when()
                .delete("/aldrin-api/posts/3")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        assertThat(new Request(dataSource, "SELECT * FROM posts WHERE post_id=2 OR post_id=3"))
                .hasNumberOfRows(0);
    }

}
