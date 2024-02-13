package com.ibardos.motoShop.endToEndTest.apiTest.authentication;

import com.ibardos.motoShop.endToEndTest.util.EndToEndTestUtil;

import jakarta.annotation.PostConstruct;
import org.json.JSONObject;

import org.junit.jupiter.api.*;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class, containing End-to-End tests against API endpoints in AuthenticationController.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationControllerApiTests {
    @LocalServerPort
    private int port;
    private String baseUrl;
    private HttpClient client;
    private String jwtTokenAdminRole;


    @PostConstruct
    void initBeforeAll() throws Exception {
        client = HttpClient.newBuilder().build();

        baseUrl = "http://localhost:" + port + "/";

        // JWT of authenticated ApplicationUser with Admin role needed to be authorized for calling register endpoint
        jwtTokenAdminRole = EndToEndTestUtil.retrieveJwtToken(baseUrl, client, "Admin");
    }


    // Register related tests
    @Test
    @Order(1)
    void register_newValidApplicationUserWithoutJwtToken_statusCode403() throws Exception {
        // Arrange
        String registerUrl = baseUrl + "authentication/register";

        int registerExpectedResponseStatus = 403;

        // Retrieve a JWT token of a registered ApplicationUser to be used during the call of the register API
        String emptyJwtToken = "";

        HttpRequest registerRequest = HttpRequest.newBuilder(URI.create(registerUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + emptyJwtToken)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/register/request/RegisterNewValidApplicationUserWithSalesRole.json")))
                .build();

        // Act
        HttpResponse<String> registerResponse = client.send(registerRequest, HttpResponse.BodyHandlers.ofString());

        int registerResultResponseStatus = registerResponse.statusCode();

        // Assert
        // Asserting that registerResponse has: expected status code, json payload indication in header
        assertEquals(registerExpectedResponseStatus, registerResultResponseStatus);
        assertTrue(registerResponse.headers().toString().contains("application/json"));
    }

    @Test
    @Order(2)
    void register_newValidApplicationUserWithUserRole_statusCode403() throws Exception {
        // Arrange
        String registerUrl = baseUrl + "authentication/register";

        int registerExpectedResponseStatus = 403;

        // Retrieve a JWT token of a registered ApplicationUser to be used during the call of the register API
        String jwtTokenUserRole = EndToEndTestUtil.retrieveJwtToken(baseUrl, client, "User");

        HttpRequest registerRequest = HttpRequest.newBuilder(URI.create(registerUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtTokenUserRole)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/register/request/RegisterNewValidApplicationUserWithSalesRole.json")))
                .build();

        // Act
        HttpResponse<String> registerResponse = client.send(registerRequest, HttpResponse.BodyHandlers.ofString());

        int registerResultResponseStatus = registerResponse.statusCode();

        // Assert
        // Asserting that registerResponse has: expected status code, json payload indication in header
        assertEquals(registerExpectedResponseStatus, registerResultResponseStatus);
        assertTrue(registerResponse.headers().toString().contains("application/json"));
    }

    @Test
    @Order(3)
    void register_newValidApplicationUserWithSalesRole_statusCode403() throws Exception {
        // Arrange
        String registerUrl = baseUrl + "authentication/register";

        int registerExpectedResponseStatus = 403;

        // Retrieve a JWT token of a registered ApplicationUser to be used during the call of the register API
        String jwtTokenSalesRole = EndToEndTestUtil.retrieveJwtToken(baseUrl, client, "Sales");

        HttpRequest registerRequest = HttpRequest.newBuilder(URI.create(registerUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtTokenSalesRole)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/register/request/RegisterNewValidApplicationUserWithSalesRole.json")))
                .build();

        // Act
        HttpResponse<String> registerResponse = client.send(registerRequest, HttpResponse.BodyHandlers.ofString());

        int registerResultResponseStatus = registerResponse.statusCode();

        // Assert
        // Asserting that registerResponse has: expected status code, json payload indication in header, jwtToken field in body
        assertEquals(registerExpectedResponseStatus, registerResultResponseStatus);
        assertTrue(registerResponse.headers().toString().contains("application/json"));
    }

    @Test
    @Order(4)
    void register_newEmptyBodyApplicationUserWithAdminRole_statusCode400() throws Exception {
        // Arrange
        String registerUrl = baseUrl + "authentication/register";

        int registerExpectedResponseStatus = 400;

        HttpRequest registerRequest = HttpRequest.newBuilder(URI.create(registerUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtTokenAdminRole)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/register/request/RegisterNewEmptyBodyApplicationUser.json")))
                .build();

        // Act
        HttpResponse<String> registerResponse = client.send(registerRequest, HttpResponse.BodyHandlers.ofString());

        int registerResultResponseStatus = registerResponse.statusCode();

        // Assert
        // Asserting that registerResponse has: expected status code, json payload indication in header, jwtToken field in body
        assertEquals(registerExpectedResponseStatus, registerResultResponseStatus);
        assertTrue(registerResponse.headers().toString().contains("application/json"));
    }

    @Test
    @Order(5)
    void register_newInvalidApplicationUserWithAdminRole_statusCode500() throws Exception {
        // Arrange
        String registerUrl = baseUrl + "authentication/register";

        int registerExpectedResponseStatus = 500;

        HttpRequest registerRequest = HttpRequest.newBuilder(URI.create(registerUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtTokenAdminRole)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/register/request/RegisterNewInvalidApplicationUser.json")))
                .build();

        // Act
        HttpResponse<String> registerResponse = client.send(registerRequest, HttpResponse.BodyHandlers.ofString());

        int registerResultResponseStatus = registerResponse.statusCode();

        // Assert
        // Asserting that registerResponse has: expected status code, json payload indication in header, jwtToken field in body
        assertEquals(registerExpectedResponseStatus, registerResultResponseStatus);
        assertTrue(registerResponse.headers().toString().contains("application/json"));
    }

    @Test
    @Order(6)
    void register_newValidApplicationUserWithAdminRole_statusCode200WithValidJwtTokenInResponseForUserRole() throws Exception {
        // Arrange
        String registerUrl = baseUrl + "authentication/register";

        int registerExpectedResponseStatus = 200;

        HttpRequest registerRequest = HttpRequest.newBuilder(URI.create(registerUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtTokenAdminRole)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/register/request/RegisterNewValidApplicationUserWithUserRole.json")))
                .build();

        // Act
        HttpResponse<String> registerResponse = client.send(registerRequest, HttpResponse.BodyHandlers.ofString());

        int registerResultResponseStatus = registerResponse.statusCode();
        JSONObject registerJsonResponse = new JSONObject(registerResponse.body());

        // Assert
        // Asserting that registerResponse has: expected status code, json payload indication in header, jwtToken field in body
        assertEquals(registerExpectedResponseStatus, registerResultResponseStatus);
        assertTrue(registerResponse.headers().toString().contains("application/json"));
        assertTrue(registerJsonResponse.has("jwtToken"), "Response does not contain 'jwtToken'");


        // ####################  Make a test HTTP call with JWT token retrieved for new ApplicationUser  #################### //

        // Asserting that the retrieved JWT token created for the newly registered ApplicationUser is valid,
        // by utilizing it during a test HTTP call to the server

        // Arrange
        String testCallUrl = baseUrl + "service/manufacturer/update";

        int testCallExpectedResponseStatus = 403;

        // JWT token retrieved for the newly created ApplicationUser
        String jwtTokenForTestCall = registerJsonResponse.get("jwtToken").toString();

        HttpRequest testCallRequest = HttpRequest.newBuilder(URI.create(testCallUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtTokenForTestCall)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/manufacturer/request/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> testCallResponse = client.send(testCallRequest, HttpResponse.BodyHandlers.ofString());

        int testCallResponseStatus = testCallResponse.statusCode();

        // Assert
        assertEquals(testCallExpectedResponseStatus, testCallResponseStatus);
    }

    @Test
    @Order(7)
    void register_newValidApplicationUserWithAdminRole_statusCode200WithValidJwtTokenInResponseForSalesRole() throws Exception {
        // Arrange
        String registerUrl = baseUrl + "authentication/register";

        int registerExpectedResponseStatus = 200;

        HttpRequest registerRequest = HttpRequest.newBuilder(URI.create(registerUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtTokenAdminRole)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/register/request/RegisterNewValidApplicationUserWithSalesRole.json")))
                .build();

        // Act
        HttpResponse<String> registerResponse = client.send(registerRequest, HttpResponse.BodyHandlers.ofString());

        int registerResultResponseStatus = registerResponse.statusCode();
        JSONObject registerJsonResponse = new JSONObject(registerResponse.body());

        // Assert
        // Asserting that registerResponse has: expected status code, json payload indication in header, jwtToken field in body
        assertEquals(registerExpectedResponseStatus, registerResultResponseStatus);
        assertTrue(registerResponse.headers().toString().contains("application/json"));
        assertTrue(registerJsonResponse.has("jwtToken"), "Response does not contain 'jwtToken'");


        // ####################  Make a test HTTP call with JWT token retrieved for new ApplicationUser  #################### //

        // Asserting that the retrieved JWT token created for the newly registered ApplicationUser is valid,
        // by utilizing it during a test HTTP call to the server

        // Arrange
        String testCallUrl = baseUrl + "service/manufacturer/update";

        int testCallExpectedResponseStatus = 204;

        // JWT token retrieved for the newly created ApplicationUser
        String jwtTokenForTestCall = registerJsonResponse.get("jwtToken").toString();

        HttpRequest testCallRequest = HttpRequest.newBuilder(URI.create(testCallUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtTokenForTestCall)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/manufacturer/request/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> testCallResponse = client.send(testCallRequest, HttpResponse.BodyHandlers.ofString());

        int testCallResponseStatus = testCallResponse.statusCode();

        // Assert
        assertEquals(testCallExpectedResponseStatus, testCallResponseStatus);
    }

    @Test
    @Order(8)
    void register_newValidApplicationUserWithAdminRole_statusCode200WithValidJwtTokenInResponseForAdminRole() throws Exception {
        // Arrange
        String registerUrl = baseUrl + "authentication/register";

        int registerExpectedResponseStatus = 200;

        HttpRequest registerRequest = HttpRequest.newBuilder(URI.create(registerUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtTokenAdminRole)
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/register/request/RegisterNewValidApplicationUserWithAdminRole.json")))
                .build();

        // Act
        HttpResponse<String> registerResponse = client.send(registerRequest, HttpResponse.BodyHandlers.ofString());

        int registerResultResponseStatus = registerResponse.statusCode();
        JSONObject registerJsonResponse = new JSONObject(registerResponse.body());

        // Assert
        // Asserting that registerResponse has: expected status code, json payload indication in header, jwtToken field in body
        assertEquals(registerExpectedResponseStatus, registerResultResponseStatus);
        assertTrue(registerResponse.headers().toString().contains("application/json"));
        assertTrue(registerJsonResponse.has("jwtToken"), "Response does not contain 'jwtToken'");


        // ####################  Make a test HTTP call with JWT token retrieved for new ApplicationUser  #################### //

        // Asserting that the retrieved JWT token created for the newly registered ApplicationUser is valid,
        // by utilizing it during a test HTTP call to the server

        // Arrange
        String testCallUrl = baseUrl + "service/manufacturer/update";

        int testCallExpectedResponseStatus = 204;

        // JWT token retrieved for the newly created ApplicationUser
        String jwtTokenForTestCall = registerJsonResponse.get("jwtToken").toString();

        HttpRequest testCallRequest = HttpRequest.newBuilder(URI.create(testCallUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtTokenForTestCall)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/manufacturer/request/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> testCallResponse = client.send(testCallRequest, HttpResponse.BodyHandlers.ofString());

        int testCallResponseStatus = testCallResponse.statusCode();

        // Assert
        assertEquals(testCallExpectedResponseStatus, testCallResponseStatus);
    }

    // Login related tests
    @Test
    @Order(9)
    void login_emptyBodyApplicationUser_statusCode400() throws Exception {
        // Arrange
        String loginUrl = baseUrl + "authentication/login";

        int loginExpectedResponseStatus = 400;

        HttpRequest loginRequest = HttpRequest.newBuilder(URI.create(loginUrl))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/login/request/LoginEmptyBody.json")))
                .build();

        // Act
        HttpResponse<String> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.ofString());

        int loginResultResponseStatus = loginResponse.statusCode();

        // Assert
        // Asserting that response has: expected status code, json payload indication in header
        assertEquals(loginExpectedResponseStatus, loginResultResponseStatus);
        assertTrue(loginResponse.headers().toString().contains("application/json"));
    }

    @Test
    @Order(10)
    void login_invalidApplicationUser_statusCode403() throws Exception {
        // Arrange
        String loginUrl = baseUrl + "authentication/login";

        int loginExpectedResponseStatus = 403;

        HttpRequest loginRequest = HttpRequest.newBuilder(URI.create(loginUrl))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/login/request/LoginInvalidApplicationUser.json")))
                .build();

        // Act
        HttpResponse<String> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.ofString());

        int loginResultResponseStatus = loginResponse.statusCode();

        // Assert
        // Asserting that response has: expected status code, json payload indication in header
        assertEquals(loginExpectedResponseStatus, loginResultResponseStatus);
        assertTrue(loginResponse.headers().toString().contains("application/json"));
    }

    @Test
    @Order(11)
    void login_existingApplicationUserWithUserRole_statusCode200WithValidJwtTokenInResponseForUserRole() throws Exception {
        // Arrange
        String loginUrl = baseUrl + "authentication/login";

        int loginExpectedResponseStatus = 200;

        HttpRequest loginRequest = HttpRequest.newBuilder(URI.create(loginUrl))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/login/request/LoginUserRole.json")))
                .build();

        // Act
        HttpResponse<String> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.ofString());

        int loginResultResponseStatus = loginResponse.statusCode();
        JSONObject loginJsonResponse = new JSONObject(loginResponse.body());

        String jwtToken = loginJsonResponse.get("jwtToken").toString();

        // Assert
        // Asserting that response has: expected status code, json payload indication in header, jwtToken field in body
        assertEquals(loginExpectedResponseStatus, loginResultResponseStatus);
        assertTrue(loginResponse.headers().toString().contains("application/json"));
        assertTrue(loginJsonResponse.has("jwtToken"), "Response does not contain 'jwtToken'");


        // ####################  Make a test HTTP call with JWT token retrieved for authenticated ApplicationUser  #################### //

        // Asserting that the retrieved JWT token retrieved for the authenticated ApplicationUser is valid,
        // by utilizing it during a test HTTP call to the server

        // Arrange
        String testCallUrl = baseUrl + "service/manufacturer/update";

        int testCallExpectedResponseStatus = 403;

        HttpRequest testCallRequest = HttpRequest.newBuilder(URI.create(testCallUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/manufacturer/request/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> testCallResponse = client.send(testCallRequest, HttpResponse.BodyHandlers.ofString());

        int testCallResponseStatus = testCallResponse.statusCode();

        // Assert
        assertEquals(testCallExpectedResponseStatus, testCallResponseStatus);
    }

    @Test
    @Order(12)
    void login_existingApplicationUserWithSalesRole_statusCode200WithValidJwtTokenInResponseForSalesRole() throws Exception {
        // Arrange
        String loginUrl = baseUrl + "authentication/login";

        int loginExpectedResponseStatus = 200;

        HttpRequest loginRequest = HttpRequest.newBuilder(URI.create(loginUrl))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/login/request/LoginSalesRole.json")))
                .build();

        // Act
        HttpResponse<String> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.ofString());

        int loginResultResponseStatus = loginResponse.statusCode();
        JSONObject loginJsonResponse = new JSONObject(loginResponse.body());

        String jwtToken = loginJsonResponse.get("jwtToken").toString();

        // Assert
        // Asserting that response has: expected status code, json payload indication in header, jwtToken field in body
        assertEquals(loginExpectedResponseStatus, loginResultResponseStatus);
        assertTrue(loginResponse.headers().toString().contains("application/json"));
        assertTrue(loginJsonResponse.has("jwtToken"), "Response does not contain 'jwtToken'");


        // ####################  Make a test HTTP call with JWT token retrieved for authenticated ApplicationUser  #################### //

        // Asserting that the retrieved JWT token retrieved for the authenticated ApplicationUser is valid,
        // by utilizing it during a test HTTP call to the server

        // Arrange
        String testCallUrl = baseUrl + "service/manufacturer/update";

        int testCallExpectedResponseStatus = 204;

        HttpRequest testCallRequest = HttpRequest.newBuilder(URI.create(testCallUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/manufacturer/request/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> testCallResponse = client.send(testCallRequest, HttpResponse.BodyHandlers.ofString());

        int testCallResponseStatus = testCallResponse.statusCode();

        // Assert
        assertEquals(testCallExpectedResponseStatus, testCallResponseStatus);
    }

    @Test
    @Order(13)
    void login_existingApplicationUserWithAdminRole_statusCode200WithValidJwtTokenInResponseForAdminRole() throws Exception {
        // Arrange
        String loginUrl = baseUrl + "authentication/login";

        int loginExpectedResponseStatus = 200;

        HttpRequest loginRequest = HttpRequest.newBuilder(URI.create(loginUrl))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/login/request/LoginAdminRole.json")))
                .build();

        // Act
        HttpResponse<String> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.ofString());

        int loginResultResponseStatus = loginResponse.statusCode();
        JSONObject loginJsonResponse = new JSONObject(loginResponse.body());

        String jwtToken = loginJsonResponse.get("jwtToken").toString();

        // Assert
        // Asserting that response has: expected status code, json payload indication in header, jwtToken field in body
        assertEquals(loginExpectedResponseStatus, loginResultResponseStatus);
        assertTrue(loginResponse.headers().toString().contains("application/json"));
        assertTrue(loginJsonResponse.has("jwtToken"), "Response does not contain 'jwtToken'");


        // ####################  Make a test HTTP call with JWT token retrieved for authenticated ApplicationUser  #################### //

        // Asserting that the retrieved JWT token retrieved for the authenticated ApplicationUser is valid,
        // by utilizing it during a test HTTP call to the server

        // Arrange
        String testCallUrl = baseUrl + "service/manufacturer/update";

        int testCallExpectedResponseStatus = 204;

        HttpRequest testCallRequest = HttpRequest.newBuilder(URI.create(testCallUrl))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + jwtToken)
                .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/service/manufacturer/request/UpdateValid.json")))
                .build();

        // Act
        HttpResponse<String> testCallResponse = client.send(testCallRequest, HttpResponse.BodyHandlers.ofString());

        int testCallResponseStatus = testCallResponse.statusCode();

        // Assert
        assertEquals(testCallExpectedResponseStatus, testCallResponseStatus);
    }
}
