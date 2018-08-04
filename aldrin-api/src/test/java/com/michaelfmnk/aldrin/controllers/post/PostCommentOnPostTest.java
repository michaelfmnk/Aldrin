package com.michaelfmnk.aldrin.controllers.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.michaelfmnk.aldrin.BaseTest;
import com.michaelfmnk.aldrin.dtos.CommentDto;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class PostCommentOnPostTest extends BaseTest {

    @Test
    public void shouldCommentPost() throws JsonProcessingException {
        CommentDto commentDto = CommentDto.builder()
                .content("New COMMENT")
                .build();

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(commentDto))
                .when()
                .post("/aldrin-api/posts/1/comments")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", equalTo(1000))
                .body("content", equalTo("New COMMENT"))
                .body("date", notNullValue())
                .body("replied_comment_id", nullValue());

        assertThat(new Request(dataSource,
                "SELECT content, date, post_id, user_id, replied_comment_id FROM comments WHERE comment_id=1000"))
                .hasNumberOfRows(1)
                .row(0)
                .value("content").isEqualTo("New COMMENT")
                .value("date").isNotNull()
                .value("post_id").isEqualTo(1)
                .value("user_id").isEqualTo(1)
                .value("replied_comment_id").isNull();
    }

    @Test
    public void shouldFailCommentPostOnPostNotFound() throws JsonProcessingException {
        CommentDto commentDto = CommentDto.builder()
                .content("New COMMENT")
                .build();

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(commentDto))
                .when()
                .post("/aldrin-api/posts/99/comments")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("title", equalTo("NOT_FOUND"))
                .body("status", equalTo(404))
                .body("detail", equalTo("Post not found"))
                .body("time_stamp", notNullValue())
                .body("dev_message", equalTo("javax.persistence.EntityNotFoundException"));
    }

}
