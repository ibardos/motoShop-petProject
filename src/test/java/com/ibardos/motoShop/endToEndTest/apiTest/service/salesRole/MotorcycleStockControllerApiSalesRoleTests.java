package com.ibardos.motoShop.endToEndTest.apiTest.service.salesRole;

import com.ibardos.motoShop.endToEndTest.util.EndToEndTestUtil;

import jakarta.annotation.PostConstruct;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class, containing End-to-End tests against API endpoints in MotorcycleStockController, authenticated with Sales role.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MotorcycleStockControllerApiSalesRoleTests {
    @LocalServerPort
    private int port;
    private String baseUrl;
    private HttpClient client;
    private String jwtToken;


    @PostConstruct
    public void initBeforeAll() throws Exception {
        client = HttpClient.newBuilder().build();

        baseUrl = "http://localhost:" + port + "/";

        jwtToken = EndToEndTestUtil.retrieveJwtToken(baseUrl, client, "Sales");
    }


    @Test
    @Order(1)
    void add_newValidMotorcycleStock_statusCode201WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + "service/motorcycle/stock/add";

        int expectedResponseStatus = 201;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonForEndToEndTest/service/motorcycleStock/response/Add.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/motorcycleStock/request/AddValid.json")))
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
    void add_newInvalidMotorcycleStock_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + "service/motorcycle/stock/add";

        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/motorcycleStock/request/AddInvalid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(3)
    void get_motorcycleStockWithValidId_statusCode200WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + "service/motorcycle/stock/get/8";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonForEndToEndTest/service/motorcycleStock/response/Get.json")));

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
    void get_motorcycleStockWithInvalidId_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + "service/motorcycle/stock/get/55";

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
    void getAll_listOfMotorcycleStocks_statusCode200WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + "service/motorcycle/stock/get/all";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonForEndToEndTest/service/motorcycleStock/response/GetAll.json")));

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
    void getAll_listOfMotorcycleStocksFromInvalidUrl_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + "service/motorcycle/stock/get/allInvalid";

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
    void update_motorcycleStockWithValidId_statusCode204() throws Exception {
        // Arrange
        String url = baseUrl + "service/motorcycle/stock/update";

        int expectedResponseStatus = 204;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/motorcycleStock/request/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(8)
    void update_motorcycleStockWithInvalidId_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + "service/motorcycle/stock/update";

        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/motorcycleStock/request/UpdateInvalidId.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(9)
    void update_motorcycleStockWithInvalidJson_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + "service/motorcycle/stock/update";

        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/motorcycleStock/request/UpdateInvalidJson.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(10)
    void delete_motorcycleStockWithValidId_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/motorcycle/stock/delete/9";

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
    void delete_motorcycleStockWithInvalidId_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/motorcycle/stock/delete/99";

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
}
