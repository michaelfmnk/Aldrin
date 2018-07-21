package com.michaelfmnk.aldrin.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.michaelfmnk.aldrin.BaseTest;
import com.michaelfmnk.aldrin.dtos.AuthRequest;
import com.michaelfmnk.aldrin.dtos.TokenContainer;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

public class AuthControllerTest extends BaseTest {

    @Test
    public void loadsDataset() {
        assertTrue(true);
    }

    @Test
    public void shouldLogin() throws JsonProcessingException {
        AuthRequest authRequest = AuthRequest.builder()
                .username("MichaelFMNK")
                .password("test")
                .build();
        TokenContainer container = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/aldrin-api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue())
                .extract().response().as(TokenContainer.class);


        Headers headers = new Headers(new Header(authProperties.getHeaderName(), container.getToken()));
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .post("/aldrin-api/posts/2/likes")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void shouldRefreshToken() throws JsonProcessingException {
        AuthRequest authRequest = AuthRequest.builder()
                .username("MichaelFMNK")
                .password("test")
                .build();
        TokenContainer container = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/aldrin-api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue())
                .extract().response().as(TokenContainer.class);


        Headers headers = new Headers(new Header(authProperties.getHeaderName(), container.getToken()));
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .post("/aldrin-api/posts/2/likes")
                .then()
                .statusCode(HttpStatus.SC_CREATED);


        container = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/aldrin-api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue())
                .extract().response().as(TokenContainer.class);


        headers = new Headers(new Header(authProperties.getHeaderName(), container.getToken()));
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .post("/aldrin-api/posts/2/likes")
                .then()
                .statusCode(HttpStatus.SC_CREATED);

    }


}
