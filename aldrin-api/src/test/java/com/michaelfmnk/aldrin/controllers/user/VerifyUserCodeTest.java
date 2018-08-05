package com.michaelfmnk.aldrin.controllers.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.michaelfmnk.aldrin.BaseTest;
import com.michaelfmnk.aldrin.dtos.VerificationCodeContainer;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class VerifyUserCodeTest extends BaseTest {

    @Test
    public void shouldVerifyCode() throws JsonProcessingException {
        VerificationCodeContainer codeContainer = new VerificationCodeContainer("123321");
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(codeContainer))
                .when()
                .post("aldrin-api/users/1/verify")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        assertThat(new Request(dataSource,
                "SELECT * FROM verification_codes WHERE verification_code='123321' AND user_id=1"))
                .hasNumberOfRows(0);

        assertThat(new Request(dataSource,
                "SELECT enabled FROM users WHERE user_id=1"))
                .hasNumberOfRows(1)
                .row(0)
                .value("enabled").isEqualTo(true);

    }

    @Test
    public void shouldNotVerifyCode() throws JsonProcessingException {
        VerificationCodeContainer codeContainer = new VerificationCodeContainer("123399");
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(codeContainer))
                .when()
                .post("aldrin-api/users/1/verify")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("title", equalTo("BAD_REQUEST"))
                .body("status", equalTo(400))
                .body("detail", equalTo("Verification code is not valid"))
                .body("time_stamp", notNullValue())
                .body("dev_message", equalTo("com.michaelfmnk.aldrin.exceptions.BadRequestException"));

        assertThat(new Request(dataSource,
                "SELECT * FROM verification_codes WHERE verification_code='123321' AND user_id=1"))
                .hasNumberOfRows(1);

        assertThat(new Request(dataSource,
                "SELECT enabled FROM users WHERE user_id=1"))
                .hasNumberOfRows(1)
                .row(0)
                .value("enabled").isEqualTo(false);
    }

    @Test
    public void shouldFailVerifyCodeOnUserEnabled() throws JsonProcessingException {
        VerificationCodeContainer codeContainer = new VerificationCodeContainer("123399");
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(codeContainer))
                .when()
                .post("aldrin-api/users/2/verify")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("title", equalTo("BAD_REQUEST"))
                .body("status", equalTo(400))
                .body("detail", equalTo("User is already enabled"))
                .body("time_stamp", notNullValue())
                .body("dev_message", equalTo("com.michaelfmnk.aldrin.exceptions.BadRequestException"));

        assertThat(new Request(dataSource,
                "SELECT enabled FROM users WHERE user_id=2"))
                .hasNumberOfRows(1)
                .row(0)
                .value("enabled").isEqualTo(true);
    }
}
