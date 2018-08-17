package com.michaelfmnk.aldrindocs.controllers.permanent;

import com.michaelfmnk.aldrindocs.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.is;

public class DownloadFileTest extends BaseTest {

    private File pdf;

    private static final String PDF_ID = "00000000-0000-0000-0000-000000000001";

    @Before
    public void before() throws IOException {
        pdf = new File(permanentStorage, PDF_ID);

        Files.copy(new File(testDir, "empty-pdf.pdf").toPath(), pdf.toPath());

        assertTrue(pdf.exists());
    }

    @Test
    public void shouldDownloadFile() throws IOException {
        byte[] bytesFile = Files.readAllBytes(pdf.toPath());

        given()
                .when()
                .get("/aldrin-docs-api/permanent/00000000-0000-0000-0000-000000000001")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(is(new String(bytesFile)));

    }
}
