package com.ibardos.motoShop.endToEndTest.apiTest.erpCore.salesRole;

import com.ibardos.motoShop.endToEndTest.util.EndToEndTestUtil;

import jakarta.annotation.PostConstruct;

import org.json.JSONException;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class, containing End-to-End tests against API endpoints in MotorcycleModelController, authenticated with Sales role.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MotorcycleModelControllerApiSalesRoleTests {
    @LocalServerPort
    private int port;
    private final String baseUrl = "http://localhost:";
    private HttpClient client;
    private String jwtToken;


    @PostConstruct
    public void initBeforeAll() throws Exception {
        client = HttpClient.newBuilder().build();

        jwtToken = EndToEndTestUtil.retrieveJwtToken(baseUrl, port, client, "Sales");
    }


    @Test
    @Order(1)
    void add_newValidMotorcycleModel_statusCode201WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/add";

        int expectedResponseStatus = 201;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonsForEndToEndTests/erpCore/motorcycleModel/response/Add.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForEndToEndTests/erpCore/motorcycleModel/request/AddValid.json")))
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
    void add_newInvalidMotorcycleModel_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/add";

        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForEndToEndTests/erpCore/motorcycleModel/request/AddInvalid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(3)
    void get_motorcycleModelWithValidId_statusCode200WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/get/1";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonsForEndToEndTests/erpCore/motorcycleModel/response/Get.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
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
    void get_motorcycleModelWithInvalidId_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/get/55";

        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
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
        String url = baseUrl + port + "/motorcycle/model/get/all";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonsForEndToEndTests/erpCore/motorcycleModel/response/GetAll.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
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
    void getAll_listOfMotorcycleModelsFromInvalidUrl_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/get/allInvalid";

        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
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
    void update_motorcycleModelWithValidId_statusCode204() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/update";

        int expectedResponseStatus = 204;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForEndToEndTests/erpCore/motorcycleModel/request/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(8)
    void update_motorcycleModelWithInvalidId_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/update";

        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForEndToEndTests/erpCore/motorcycleModel/request/UpdateInvalidId.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(9)
    void update_motorcycleModelWithInvalidJson_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/update";

        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonsForEndToEndTests/erpCore/motorcycleModel/request/UpdateInvalidJson.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(10)
    void delete_motorcycleModelWithValidId_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/delete/6";

        int expectedResponseStatus = 403;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
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
    void delete_motorcycleModelWithInvalidId_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/delete/99";

        int expectedResponseStatus = 403;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
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
    void delete_motorcycleModelWithIdHasForeignKeyRestriction_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/delete/7";

        int expectedResponseStatus = 403;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .DELETE()
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(13)
    void get_motorcycleModelTypes_statusCode200WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + port + "/motorcycle/model/get/types";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonsForEndToEndTests/erpCore/motorcycleModel/response/GetMotorcycleModelTypes.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
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
}