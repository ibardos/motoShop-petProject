import {getJwtToken} from "../security/authService";


export function fetchData(url) {
    const options = {
        method: "GET",
        headers: {'Content-Type': 'application/json', 'Authorization': `Bearer ${getJwtToken()}`}
    }

    return fetch(url, options)
        .then(res => res.json());
}
