package com.ibardos.motoShop.controller;

import com.ibardos.motoShop.util.JsonReader;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MotorcycleModelControllerTest {
    private static HttpClient client;

    @BeforeAll
    static void initBeforeAll() {
        client = HttpClient.newBuilder().build();
    }

    @Test
    @Order(1)
    void add_newValidMotorcycleModel_statusCode201WithProperJson() throws Exception {
        // Arrange
        JsonReader reader = new JsonReader("src/test/resources/jsonsForUnitTests/motorcycleModel/responses/Add.json");

        int expectedResponseStatus = 201;
        String expectedResponseBody = reader.read();

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/add"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/motorcycleModel/requests/AddValid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        assertEquals(expectedResponseBody, resultResponseBody);
    }

    @Test
    @Order(2)
    void add_newInvalidMotorcycleModel_statusCode400() throws Exception {
        // Arrange
        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/add"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/motorcycleModel/requests/AddInvalid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(3)
    void get_motorcycleModelWithId1_statusCode200WithProperJson() throws Exception {
        // Arrange
        JsonReader reader = new JsonReader("src/test/resources/jsonsForUnitTests/motorcycleModel/responses/Get.json");

        int expectedResponseStatus = 200;
        String expectedResponseBody = reader.read();

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/get/1"))
                .GET()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        assertEquals(expectedResponseBody, resultResponseBody);
    }

    @Test
    @Order(4)
    void get_motorcycleModelWithId55_statusCode404() throws Exception {
        // Arrange
        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/get/55"))
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
    void getAll_listOfMotorcycleModels_statusCode200WithProperJson() throws Exception {
        // Arrange
        JsonReader reader = new JsonReader("src/test/resources/jsonsForUnitTests/motorcycleModel/responses/GetAll.json");

        int expectedResponseStatus = 200;
        String expectedResponseBody = reader.read();

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/get/all"))
                .GET()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        assertEquals(expectedResponseBody, resultResponseBody);
    }

    @Test
    @Order(6)
    void getAll_listOfMotorcycleModelsFromWrongUrl_statusCode400() throws Exception {
        // Arrange
        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/get/invalid"))
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
    void update_motorcycleModelWithId7_statusCode204() throws Exception {
        // Arrange
        int expectedResponseStatus = 204;
        String expectedResponseBody = "";

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/update"))
                .headers("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/motorcycleModel/requests/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        assertEquals(expectedResponseBody, resultResponseBody);
    }

    @Test
    @Order(8)
    void update_motorcycleModelWithId777_statusCode404() throws Exception {
        // Arrange
        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/update"))
                .headers("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/motorcycleModel/requests/UpdateInvalid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(9)
    void delete_motorcycleModelWithId6_statusCode204() throws Exception {
        // Arrange
        int expectedResponseStatus = 204;
        String expectedResponseBody = "";

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/delete/6"))
                .headers("Content-Type", "application/json")
                .DELETE()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        assertEquals(expectedResponseBody, resultResponseBody);
    }

    @Test
    @Order(10)
    void delete_motorcycleModelWithInvalidId99_statusCode404() throws Exception {
        // Arrange
        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/delete/99"))
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
    void delete_motorcycleModelWithId1HasForeignKeyRestriction_statusCode500() throws Exception {
        // Arrange
        int expectedResponseStatus = 500;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/delete/7"))
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
    void get_motorcycleModelTypes_statusCode200WithProperJson() throws Exception {
        // Arrange
        JsonReader reader = new JsonReader("src/test/resources/jsonsForUnitTests/motorcycleModel/responses/GetMotorcycleModelTypes.json");

        int expectedResponseStatus = 200;
        String expectedResponseBody = reader.read();

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/get/types"))
                .GET()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();
        String resultResponseBody = response.body();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
        assertEquals(expectedResponseBody, resultResponseBody);
    }
}