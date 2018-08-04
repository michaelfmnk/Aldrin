package com.michaelfmnk.aldrin.controllers.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.michaelfmnk.aldrin.BaseTest;
import com.michaelfmnk.aldrin.dtos.PostDto;
import io.restassured.http.ContentType;
import org.assertj.db.type.Table;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;

public class PutPostTest extends BaseTest {

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

}
