package com.ibardos.motoShop.endToendTests.apiTests;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.file.Path;
import java.nio.file.Files;

/**
 * Test class, containing End-to-End tests against API endpoints in ManufacturerController.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManufacturerControllerApiTests {
    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    private static HttpClient client;

    @BeforeAll
    static void initBeforeAll() { client = HttpClient.newBuilder().build(); }

    @Test
    @Order(1)
    void add_newValidManufacturer_statusCode201WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/add";

        int expectedResponseStatus = 201;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/responses/Add.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/requests/AddValid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        JSONAssert.assertEquals(expectedResponseBody, resultResponseBody, false);
    }

    @Test
    @Order(2)
    void add_newInvalidManufacturer_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/add";

        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/requests/AddInvalid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(3)
    void get_manufacturerWithValidId_statusCode200WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/get/6";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/responses/Get.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        JSONAssert.assertEquals(expectedResponseBody, resultResponseBody, false);
    }

    @Test
    @Order(4)
    void get_manufacturerWithInvalidId_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/get/55";

        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(5)
    void getAll_listOfManufacturers_statusCode200WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/get/all";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/responses/GetAll.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        JSONAssert.assertEquals(expectedResponseBody, resultResponseBody, false);
    }

    @Test
    @Order(6)
    void getAll_listOfManufacturersFromInvalidUrl_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/get/allInvalid";

        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(7)
    void update_manufacturerWithValidId_statusCode204() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/update";

        int expectedResponseStatus = 204;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/requests/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(8)
    void update_manufacturerWithInvalidId_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/update";

        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/requests/UpdateInvalidId.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(9)
    void update_manufacturerWithInvalidJson_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/update";

        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/requests/UpdateInvalidJson.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(10)
    void delete_manufacturerWithValidId_statusCode204() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/delete/5";

        int expectedResponseStatus = 204;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json")
                .DELETE()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(11)
    void delete_manufacturerWithInvalidId_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/delete/33";

        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json")
                .DELETE()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(12)
    void delete_manufacturerWithIdHasForeignKeyRestriction_statusCode500() throws Exception {
        // Arrange
        String url = baseUrl + port + "/manufacturer/delete/1";

        int expectedResponseStatus = 500;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json")
                .DELETE()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }
}
