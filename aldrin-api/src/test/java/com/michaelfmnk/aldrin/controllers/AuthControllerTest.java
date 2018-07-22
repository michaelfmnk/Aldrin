package com.michaelfmnk.aldrin.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.michaelfmnk.aldrin.BaseTest;
import com.michaelfmnk.aldrin.dtos.AuthRequest;
import com.michaelfmnk.aldrin.dtos.TokenContainer;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest extends BaseTest {

    @Test
    public void loadsDataset() {
        assertTrue(true);
    }

    @Test
    public void shouldLogin() throws JsonProcessingException {
        AuthRequest authRequest = AuthRequest.builder()
                .login("michaelfmnk@gmail.com")
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
    public void shouldNotLoginWithBadCredentials() throws JsonProcessingException {
        AuthRequest authRequest = AuthRequest.builder()
                .login("michaelfmnk@gmail.com")
                .password("badPassword")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/aldrin-api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("title", equalTo("UNAUTHORIZED"))
                .body("status", equalTo(401))
                .body("detail", equalTo("Bad credentials"))
                .body("time_stamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.security.authentication.BadCredentialsException"));
    }

    @Test
    public void shouldNotLoginNotEnabledUser() throws JsonProcessingException {
        AuthRequest authRequest = AuthRequest.builder()
                .login("steven@gmail.com")
                .password("test")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/aldrin-api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
                .body("title", equalTo("METHOD_NOT_ALLOWED"))
                .body("status", equalTo(405))
                .body("detail", equalTo("User is disabled"))
                .body("time_stamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.security.authentication.DisabledException"));
    }

    @Test
    public void shouldRefreshToken() throws JsonProcessingException {
        AuthRequest authRequest = AuthRequest.builder()
                .login("michaelfmnk@gmail.com")
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

    @Test
    public void shouldSingup() throws Throwable {
        AuthRequest authRequest = AuthRequest.builder()
                .login("meteormf99@gmail.com")
                .password("newPassword")
                .build();


        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/aldrin-api/auth/sign-up")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        verify(mailjetClient, times(1)).post(any(MailjetRequest.class));

        assertThat(new Request(dataSource,
                "SELECT enabled, last_password_reset_date FROM users WHERE login='meteormf99@gmail.com'"))
                .hasNumberOfRows(1)
                .row(0)
                .value("last_password_reset_date").isNotNull()
                .value("enabled").isFalse();

        assertThat(new Request(dataSource,
                "SELECT * FROM verification_codes WHERE user_id=1000"))
                .hasNumberOfRows(1)
                .row(0)
                .value("verification_code").isNotNull();
    }

    @Test
    public void shouldFailSingupIfNotPossibleToSendEmail() throws Throwable {
        when(mailjetClient.post(any(MailjetRequest.class))).thenThrow(new MailjetException("error"));

        AuthRequest authRequest = AuthRequest.builder()
                .login("meteormf99@gmail.com")
                .password("newPassword")
                .build();


        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/aldrin-api/auth/sign-up")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("title", equalTo("INTERNAL_SERVER_ERROR"))
                .body("status", equalTo(500))
                .body("detail", equalTo("Oops. Something went wrong while sending verification email. Try again later"))
                .body("time_stamp", notNullValue())
                .body("dev_message", equalTo("java.lang.RuntimeException"));
    }

    @Test
    public void shouldSingupOnUnprocessableEntity() throws Throwable {
        AuthRequest authRequest = AuthRequest.builder()
                .login("meteormf99gmail.com")
                .password("newPassword")
                .build();

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/aldrin-api/auth/sign-up")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("title", equalTo("UNPROCESSABLE_ENTITY"))
                .body("status", equalTo(422))
                .body("detail", equalTo("must be a well-formed email address"))
                .body("time_stamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.web.bind.MethodArgumentNotValidException"));
        ;

        authRequest = AuthRequest.builder()
                .login("meteormf99@gmail.com")
                .password("n")
                .build();

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/aldrin-api/auth/sign-up")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("title", equalTo("UNPROCESSABLE_ENTITY"))
                .body("status", equalTo(422))
                .body("detail", equalTo("Invalid password"))
                .body("time_stamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.web.bind.MethodArgumentNotValidException"));
    }


}
