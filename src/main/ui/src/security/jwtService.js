// Functions for JWT token handling

import {jwtDecode} from "jwt-decode";


/**
 * Saves a JWT token into local storage.
 * @param jwtToken as string.
 */
export function saveJwtTokenToLocalStorage(jwtToken) {
    localStorage.setItem('jwtToken', jwtToken);
}

/**
 * Gets the saved JWT token from local storage.
 * @returns {string}
 */
export function getJwtTokenFromLocalStorage() {
    return localStorage.getItem("jwtToken");
}

/**
 * Removes the JWT token from the local storage.
 */
export function removeJwtTokenFromLocalStorage() {
    localStorage.removeItem("jwtToken");
}

/**
 * Checks if JWT token is present in local storage.
 * @returns {boolean}
 */
export function isJwtTokenPresent() {
    return getJwtTokenFromLocalStorage() !== null;
}

/**
 * Checks if JWT token can be decoded to data retrieval.
 * @returns {boolean}
 */
export function isJwtTokenCanBeDecoded() {
    let decodedJwtToken= decodeJwtTokenFromLocalStorage();
    return decodedJwtToken !== null;
}

/**
 * Checks if JWT token has been expired, based on its expiration time compared to current local time.
 * @returns {boolean}
 */
export function isJwtTokenExpired() {
    let decodedJwtToken= decodeJwtTokenFromLocalStorage();

    const currentDate = new Date();

    if (currentDate.getTime() > decodedJwtToken.exp * 1000) {
        console.error("The JWT token has expired!");
        return true;
    }

    return false;
}

/**
 * Gets username from JWT token.
 * @returns {string}
 */
export function getUsernameFromJwtToken() {
    let decodedJwtToken= decodeJwtTokenFromLocalStorage();
    return decodedJwtToken.sub;
}

/**
 * Gets the role of a user from JWT token.
 * @returns {string}
 */
export function getUserRoleFromJwtToken() {
    const authorities = getAuthoritiesFromJwtToken();
    return authorities.find(record => record.authority.startsWith('ROLE')).authority.substring(5);
}

/**
 * Gets a list of permissions that a user has, stored in a JWT token.
 * @returns list of permission.
 */
export function getUserPermissionsFromJwtToken() {
    const authorities = getAuthoritiesFromJwtToken();

    let permissions = [];

    for (const item of authorities) {
        if (item.authority.startsWith('PERMISSION')) {
            permissions.push(item.authority.substring(11));
        }
    }

    return permissions;
}


// Helper methods
/**
 * Decodes the JWT token, stored in local storage, to prepare it for data retrieval.
 * @returns the decoded payload of the stored JWT token.
 */
function decodeJwtTokenFromLocalStorage() {
    // Pure JWT token from local storage to be tested for decoding
    const jwtToken = getJwtTokenFromLocalStorage();

    let decodedJwtToken;

    try {
        decodedJwtToken = jwtDecode(jwtToken);
    } catch (InvalidTokenError) {
        console.error(`The JWT token couldn't be decoded: "${jwtToken}". Error message: ${InvalidTokenError}`);
        return null;
    }

    return decodedJwtToken;
}

/**
 * Gets the list of authorities that the JWT token stores, which has been saved in local storage.
 * @returns list of authorities, as objects.
 */
function getAuthoritiesFromJwtToken() {
    let decodedJwtToken= decodeJwtTokenFromLocalStorage();
    return decodedJwtToken.authorities;
}
