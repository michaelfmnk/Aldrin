package com.michaelfmnk.aldrin.controllers.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.michaelfmnk.aldrin.BaseTest;
import com.michaelfmnk.aldrin.dtos.CommentDto;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PutCommentTest extends BaseTest {
    @Test
    public void shouldEditComment() throws JsonProcessingException {
        CommentDto commentDto = CommentDto.builder()
                .content("edited content")
                .build();

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(commentDto))
                .when()
                .put("/aldrin-api/comments/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        assertThat(new Request(dataSource, "SELECT content FROM comments WHERE comment_id=1"))
                .hasNumberOfRows(1)
                .row(0)
                .value("content").isEqualTo("edited content");
    }

    @Test
    public void shouldNotBeAbleEditNotOwnComment() throws JsonProcessingException {
        CommentDto commentDto = CommentDto.builder()
                .content("edited content")
                .build();

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(commentDto))
                .when()
                .put("/aldrin-api/comments/3")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("title", equalTo("FORBIDDEN"))
                .body("status", equalTo(403))
                .body("detail", equalTo("Access is denied"))
                .body("timestamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.security.access.AccessDeniedException"));
    }
}
