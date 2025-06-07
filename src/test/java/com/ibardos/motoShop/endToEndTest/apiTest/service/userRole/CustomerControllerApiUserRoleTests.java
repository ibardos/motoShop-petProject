package com.ibardos.motoShop.endToEndTest.apiTest.service.userRole;

import com.ibardos.motoShop.endToEndTest.util.EndToEndTestUtil;

import jakarta.annotation.PostConstruct;

import org.junit.jupiter.api.*;
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
import java.time.LocalDate;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class, containing End-to-End tests against API endpoints in CustomerController, authenticated with User role.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerApiUserRoleTests {
    @LocalServerPort
    private int port;

    private String baseUrl;
    private static HttpClient client;
    private String jwtToken;

    @BeforeAll
    static void setupAll() {
        client = HttpClient.newBuilder().build();
    }

    @BeforeEach
    void setupEach() throws Exception {
        baseUrl = "http://localhost:" + port + "/";
        jwtToken = EndToEndTestUtil.retrieveJwtToken(baseUrl, client, "User");
    }


    @Test
    @Order(1)
    void add_newValidCustomer_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/add";

        int expectedResponseStatus = 403;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/customer/request/AddValid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(2)
    void add_newInvalidCustomer_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/add";

        int expectedResponseStatus = 403;

        // Read JSON, update dateOfRegistration to today
        String jsonPath = "src/test/resources/jsonForEndToEndTest/service/customer/request/AddInvalid.json";
        String originalJson = new String(Files.readAllBytes(Path.of(jsonPath)));
        JSONObject jsonObj = new JSONObject(originalJson);
        jsonObj.put("dateOfRegistration", LocalDate.now().toString());
        String modifiedJson = jsonObj.toString();

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofString(modifiedJson))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(3)
    void get_customerWithValidId_statusCode200WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/get/1";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonForEndToEndTest/service/customer/response/Get.json")));

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
    void get_customerWithInvalidId_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/get/11";

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
    void get_customerUpdateDtoWithValidId_statusCode200WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/get/updateDto/1";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonForEndToEndTest/service/customer/response/GetUpdateDto.json")));

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
    void get_customerUpdateDtoWithInvalidId_statusCode404() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/get/updateDto/11";

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
    @Order(7)
    void getAll_listOfCustomers_statusCode200WithProperJson() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/get/all";

        int expectedResponseStatus = 200;
        String expectedResponseBody = new String(Files.readAllBytes(Path.of("src/test/resources/jsonForEndToEndTest/service/customer/response/GetAll.json")));

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
    @Order(8)
    void getAll_listOfCustomersFromInvalidUrl_statusCode400() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/get/allInvalid";

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
    @Order(9)
    void update_customerWithValidId_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/update";

        int expectedResponseStatus = 403;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/customer/request/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(10)
    void update_customerWithInvalidId_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/update";

        int expectedResponseStatus = 403;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/customer/request/UpdateInvalidId.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(11)
    void update_customerWithInvalidJson_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/update";

        int expectedResponseStatus = 403;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/customer/request/UpdateInvalidJson.json")))
                .build();

        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int resultResponseStatus = response.statusCode();

        // Assert
        assertEquals(expectedResponseStatus, resultResponseStatus);
    }

    @Test
    @Order(12)
    void delete_customerWithValidId_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/delete/2";

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
    void delete_customerWithInvalidId_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/delete/22";

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
    @Order(14)
    void delete_customerWithIdHasForeignKeyRestriction_statusCode403() throws Exception {
        // Arrange
        String url = baseUrl + "service/customer/delete/1";

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
