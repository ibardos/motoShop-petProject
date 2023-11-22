package com.ibardos.motoShop.controller;

import com.ibardos.motoShop.data.DatabaseManager;
import com.ibardos.motoShop.util.JsonReader;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import java.nio.file.Path;

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
        JsonReader reader = new JsonReader("src/test/resources/jsonsForUnitTests/manufacturer/responses/Add.json");

        int expectedResponseStatus = 201;
        String expectedResponseBody = reader.read();

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/add"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/requests/AddValid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        assertEquals(expectedResponseBody, resultResponseBody);
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
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(3)
    void get_manufacturerWithId6_statusCode200WithProperJson() throws Exception {
        // Arrange
        JsonReader reader = new JsonReader("src/test/resources/jsonsForUnitTests/manufacturer/responses/Get.json");

        int expectedResponseStatus = 200;
        String expectedResponseBody = reader.read();

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/get/6"))
                .GET()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        assertEquals(expectedResponseBody, resultResponseBody);
    }

    @Test
    @Order(4)
    void get_manufacturerWithId55_statusCode404() throws Exception {
        // Arrange
        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/get/55"))
                .GET()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(5)
    void getAll_listOfManufacturers_statusCode200WithProperJson() throws Exception {
        // Arrange
        JsonReader reader = new JsonReader("src/test/resources/jsonsForUnitTests/manufacturer/responses/GetAll.json");

        int expectedResponseStatus = 200;
        String expectedResponseBody = reader.read();

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/get/all"))
                .GET()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        assertEquals(expectedResponseBody, resultResponseBody);
    }

    @Test
    @Order(6)
    void getAll_listOfManufacturersFromWrongUrl_statusCode400() throws Exception {
        // Arrange
        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/get/invalid"))
                .GET()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(7)
    void update_manufacturerWithId1_statusCode204() throws Exception {
        // Arrange
        int expectedResponseStatus = 204;
        String expectedResponseBody = "";

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/update"))
                .headers("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/requests/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        assertEquals(expectedResponseBody, resultResponseBody);
    }

    @Test
    @Order(8)
    void update_manufacturerWithId111_statusCode404() throws Exception {
        // Arrange
        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/update"))
                .headers("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/manufacturer/requests/UpdateInvalid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(9)
    void delete_manufacturerWithId5_statusCode204() throws Exception {
        // Arrange
        int expectedResponseStatus = 204;
        String expectedResponseBody = "";

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/delete/5"))
                .headers("Content-Type", "application/json")
                .DELETE()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        assertEquals(expectedResponseBody, resultResponseBody);
    }

    @Test
    @Order(10)
    void delete_manufacturerWithInvalidId33_statusCode404() throws Exception {
        // Arrange
        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/delete/33"))
                .headers("Content-Type", "application/json")
                .DELETE()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(11)
    void delete_manufacturerWithId1HasForeignKeyRestriction_statusCode500() throws Exception {
        // Arrange
        int expectedResponseStatus = 500;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/manufacturer/delete/1"))
                .headers("Content-Type", "application/json")
                .DELETE()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }
}
