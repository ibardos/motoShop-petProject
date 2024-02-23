// Functions for authentication and authorization related processes

import {
    isJwtTokenCanBeDecoded,
    getUsernameFromJwtToken,
    getUserRoleFromJwtToken,
    isJwtTokenPresent,
    getUserPermissionsFromJwtToken,
    isJwtTokenExpired,
    saveJwtTokenToLocalStorage,
    getJwtTokenFromLocalStorage, removeJwtTokenFromLocalStorage
} from './jwtService';


// Functions for AuthenticationContext

/**
 * Checks if the user is authenticated based on the presence, decodability, and expiration of the JWT token.
 * @returns {boolean}
 */
export function isUserAuthenticated() {
    return isJwtTokenPresent() && isJwtTokenCanBeDecoded() && !isJwtTokenExpired();
}

/**
 * Retrieves the username if the user is authenticated; otherwise, returns null.
 * @returns {string | null}
 */
export function getUsername() {
    return isUserAuthenticated() ? getUsernameFromJwtToken() : null;
}

/**
 * Retrieves the user role if the user is authenticated; otherwise, returns null.
 * @returns {string | null}
 */
export function getUserRole() {
    return isUserAuthenticated() ? getUserRoleFromJwtToken() : null;
}

/**
 * Retrieves user permissions if the user is authenticated; otherwise, returns null.
 * @returns {Array<string> | null}
 */
export function getUserPermissions() {
    return isUserAuthenticated() ? getUserPermissionsFromJwtToken() : null;
}


// Functions for direct import

/**
 * Retrieves the JWT token if the user is authenticated; otherwise, returns null.
 * @returns {string | null}
 */
export function getJwtToken() {
    return isUserAuthenticated() ? getJwtTokenFromLocalStorage() : null;
}

/**
 * Saves the provided JWT token to the local storage.
 * @param {string} jwtToken - The JWT token to be saved.
 */
export function saveJwtToken(jwtToken) {
    saveJwtTokenToLocalStorage(jwtToken);
}

/**
 * Removes the JWT token from the local storage.
 */
export function removeJwtToken() {
    removeJwtTokenFromLocalStorage();
}
