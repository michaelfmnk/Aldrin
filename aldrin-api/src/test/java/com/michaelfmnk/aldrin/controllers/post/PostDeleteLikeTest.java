package com.michaelfmnk.aldrin.controllers.post;

import com.michaelfmnk.aldrin.BaseTest;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;

public class PostDeleteLikeTest extends BaseTest {

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

}
