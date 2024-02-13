package com.ibardos.motoShop.endToEndTest.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.file.Path;

/**
 * Utility class for end-to-end Java tests against API endpoints. The class consists of several outsourced static utility methods.
 */
public class EndToEndTestUtil {
    /**
     * Retrieves a JWT token from the back-end server related to a test ApplicationUser with the Role passed as parameter.
     * @param baseUrl a URI where the login request should be sent to.
     * @param client an HTTP client can be used to send HTTP related requests.
     * @param role String representation of the desired Role embedded in the JWT token.
     * @return String representation of a valid and signed JWT token retrieved from the back-end server.
     * @throws IOException will be thrown if file reading fails.
     * @throws InterruptedException will be thrown if HTTP request sending fails.
     * @throws JSONException will be thrown if a JSON object related task fails.
     */
    public static String retrieveJwtToken(String baseUrl, HttpClient client, String role) throws IOException, InterruptedException, JSONException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "authentication/login"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/jsonForEndToEndTest/authentication/login/request/Login" + role + "Role.json")))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonResponse = new JSONObject(response.body());

        return jsonResponse.getString("jwtToken");
    }
}
