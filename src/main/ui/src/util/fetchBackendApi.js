import {getJwtToken} from "../security/authService";

const baseUrl = process.env.REACT_APP_BACKEND_URL;

/**
 * Sends an HTTP request to the backend API using the provided parameters.
 *
 * @param {string} urlSuffix - The path to append to the backend base URL (e.g., "/service/order/get/all").
 * @param {string} method - The HTTP method to use (e.g., "GET", "POST", "PUT", "DELETE").
 * @param {Object|null} [body=null] - The optional request payload, for methods like POST or PUT.
 * @returns {Promise<any>} - A Promise that resolves to the parsed JSON response if successful.
 * @throws {Error} - Throws an error if the response is not ok (non-2xx HTTP status).
 */
export const fetchBackendApi = async (urlSuffix, method, body = null) => {
    const jwtToken = getJwtToken();

    const headers = {
        'Content-Type': 'application/json'
    };

    if (jwtToken) {
        headers['Authorization'] = `Bearer ${jwtToken}`;
    }

    const options = {
        method,
        headers
    };

    if (body) {
        options.body = JSON.stringify(body);
    }

    const response = await fetch(`${baseUrl}${urlSuffix}`, options);

    if (!response.ok) {
        const errorText = await response.text();
        const error = new Error(errorText);
        error.status = response.status;
        throw error;
    }

    // Handle 204 No Content or empty body safely
    const contentLength = response.headers.get("content-length");
    if (response.status === 204 || contentLength === "0") {
        return null;
    }

    const text = await response.text();
    return text ? JSON.parse(text) : null;
}
