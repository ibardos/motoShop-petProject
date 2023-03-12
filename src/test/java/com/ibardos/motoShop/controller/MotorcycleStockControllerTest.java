package com.ibardos.motoShop.controller;

import com.ibardos.motoShop.data.DatabaseManager;
import com.ibardos.motoShop.util.JsonReader;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MotorcycleStockControllerTest {
    private static HttpClient client;

    @BeforeAll
    static void initBeforeAll() throws SQLException {
        client = HttpClient.newBuilder().build();

        DatabaseManager.initialiseDatabase();
    }

    @AfterAll
    static void cleanUpAfterAll() throws SQLException {
        DatabaseManager.initialiseDatabase();
    }

    @Test
    @Order(1)
    void add_newValidMotorcycleStock_statusCode201WithProperJson() throws Exception {
        // Arrange
        JsonReader reader = new JsonReader("src/test/resources/jsonsForUnitTests/motorcycleStock/responses/Add.json");

        int expectedResponseStatus = 201;
        String expectedResponseBody = reader.read();

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/stock/add"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/motorcycleStock/requests/AddValid.json")))
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
    void add_newInvalidMotorcycleStock_statusCode400() throws Exception {
        // Arrange
        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/stock/add"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/motorcycleStock/requests/AddInvalid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(3)
    void get_motorcycleStockWithId8_statusCode200WithProperJson() throws Exception {
        // Arrange
        JsonReader reader = new JsonReader("src/test/resources/jsonsForUnitTests/motorcycleStock/responses/Get.json");

        int expectedResponseStatus = 200;
        String expectedResponseBody = reader.read();

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/stock/get/8"))
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
    void get_motorcycleStockWithId55_statusCode404() throws Exception {
        // Arrange
        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/stock/get/55"))
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
    void getAll_listOfMotorcycleStocks_statusCode200WithProperJson() throws Exception {
        // Arrange
        JsonReader reader = new JsonReader("src/test/resources/jsonsForUnitTests/motorcycleStock/responses/GetAll.json");

        int expectedResponseStatus = 200;
        String expectedResponseBody = reader.read();

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/stock/get/all"))
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
    void getAll_listOfMotorcycleStocksFromWrongUrl_statusCode400() throws Exception {
        // Arrange
        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/stock/get/invalid"))
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
    void update_motorcycleStockWithId8_statusCode204() throws Exception {
        // Arrange
        int expectedResponseStatus = 204;
        String expectedResponseBody = "";

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/stock/update"))
                .headers("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/motorcycleStock/requests/UpdateValid.json")))
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
    void update_motorcycleStockWithId8ButWithInvalidJsonObject_statusCode400() throws Exception {
        // Arrange
        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/model/update"))
                .headers("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForUnitTests/motorcycleStock/requests/UpdateInvalid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(9)
    void delete_motorcycleStockWithId9_statusCode204() throws Exception {
        // Arrange
        int expectedResponseStatus = 204;
        String expectedResponseBody = "";

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/stock/delete/9"))
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
    void delete_motorcycleStockWithInvalidId99_statusCode404() throws Exception {
        // Arrange
        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/motorcycle/stock/delete/99"))
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
