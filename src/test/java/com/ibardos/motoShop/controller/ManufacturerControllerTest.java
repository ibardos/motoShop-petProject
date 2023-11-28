package com.ibardos.motoShop.controller;

import com.ibardos.motoShop.util.DatabaseManager;

import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.file.Path;
import java.nio.file.Files;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManufacturerControllerTest {
    private static HttpClient client;

    @BeforeAll
    static void initBeforeAll() {
        client = HttpClient.newBuilder().build();

        DatabaseManager.initializeDatabase();
    }

    @AfterAll
    static void cleanUpAfterAll() {
        DatabaseManager.initializeDatabase();
    }

    @Test
    @Order(1)
    void add_newValidManufacturer_statusCode201WithProperJson() throws Exception {
        // Arrange
        int expectedResponseStatus = 201;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/responses/Add.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/add"))
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
        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/add"))
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
        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/responses/Get.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/get/6"))
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
        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/get/55"))
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
        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/responses/GetAll.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/get/all"))
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
        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/get/invalid"))
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
        int expectedResponseStatus = 204;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/update"))
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
        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/update"))
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
        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/update"))
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
        int expectedResponseStatus = 204;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/delete/5"))
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
        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/delete/33"))
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
        int expectedResponseStatus = 500;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/delete/1"))
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
