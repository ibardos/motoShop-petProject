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
 * Test class, containing End-to-End tests against API endpoints in OrderController, authenticated with Sales role.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerApiSalesRoleTests {
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
    void add_newValidOrder_statusCode201WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/add";

        int expectedResponseStatus = 201;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonForEndToEndTest/service/order/response/Add.json")));

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/order/request/AddValid.json")))
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
    void add_newInvalidOrder_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/add";

        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/order/request/AddInvalidJson.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(3)
    void add_newOrderWithInvalidMotorcycleStock_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/add";

        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/order/request/AddInvalidMotorcycleStock.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(4)
    void add_newOrderWithInvalidCustomer_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/add";

        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/order/request/AddInvalidCustomer.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(5)
    void add_newOrderWithInsufficientStock_statusCode409() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/add";

        int expectedResponseStatus = 409;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/order/request/AddInsufficientStock.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(6)
    void get_orderWithValidId_statusCode200WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/get/1";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonForEndToEndTest/service/order/response/Get.json")));

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
    @Order(7)
    void get_orderWithInvalidId_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/get/111";

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
    @Order(8)
    void getAll_listOfOrders_statusCode200WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/get/all";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonForEndToEndTest/service/order/response/GetAll.json")));

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
    @Order(9)
    void getAll_listOfOrdersFromInvalidUrl_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/get/allInvalid";

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
    @Order(10)
    void update_orderWithValidId_statusCode204() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/update";

        int expectedResponseStatus = 204;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/order/request/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(11)
    void update_orderWithInvalidId_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/update";

        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/order/request/UpdateInvalidId.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(12)
    void update_orderWithInvalidJson_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/update";

        int expectedResponseStatus = 400;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/order/request/UpdateInvalidJson.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(13)
    void update_orderWithInvalidMotorcycleStock_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/update";

        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/order/request/UpdateInvalidMotorcycleStock.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(14)
    void update_orderWithInvalidCustomer_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/update";

        int expectedResponseStatus = 404;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/order/request/UpdateInvalidCustomer.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(15)
    void update_orderWithInsufficientStock_statusCode409() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/update";

        int expectedResponseStatus = 409;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/order/request/UpdateInsufficientStock.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(16)
    void delete_orderWithValidId_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/delete/1";

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
    @Order(17)
    void delete_orderWithInvalidId_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/order/delete/100";

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