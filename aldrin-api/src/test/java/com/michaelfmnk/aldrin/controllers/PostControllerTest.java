package com.michaelfmnk.aldrin.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.michaelfmnk.aldrin.BaseTest;
import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.dtos.PostDto;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.assertj.db.type.Table;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
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

    @Test
    public void shouldBeAbleToEditPost() throws JsonProcessingException {
        PostDto updatedPost = PostDto.builder()
                .title("new title")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(updatedPost))
                .when()
                .put("/aldrin-api/posts/1")
                .then()
                .statusCode(HttpStatus.SC_OK);

        assertThat(new Table(dataSource, "posts"))
                .row(0)
                .hasOnlyNotNullValues()
                .value("title").isEqualTo("new title");
    }


    @Test
    public void shouldNotBeAbleToEditNotOwnPost() throws JsonProcessingException {
        PostDto updatedPost = PostDto.builder()
                .title("new title")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(updatedPost))
                .when()
                .put("/aldrin-api/posts/3")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void shouldLikeDislike() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .post("/aldrin-api/posts/2/likes")
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        assertThat(new Request(dataSource, "SELECT * FROM likes WHERE user_id=1 AND post_id=2"))
                .hasNumberOfRows(1);

        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .post("/aldrin-api/posts/2/likes")
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        assertThat(new Request(dataSource, "SELECT * FROM likes WHERE user_id=1 AND post_id=2"))
                .hasNumberOfRows(1);

        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/aldrin-api/posts/2/likes")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        assertThat(new Request(dataSource, "SELECT * FROM likes WHERE user_id=1 AND post_id=2"))
                .hasNumberOfRows(0);
    }


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
