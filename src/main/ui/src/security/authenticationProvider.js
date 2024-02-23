import React, {createContext, useEffect, useState} from 'react';

import {useNavigate} from 'react-router-dom';

import {isUserAuthenticated, getUsername, getUserRole, getUserPermissions} from "./authService";


export const AuthenticationContext = createContext(null);

export const AuthenticationProvider = ({children}) => {
    const navigate = useNavigate();

    const [authenticated, setAuthenticated] = useState(false);
    const [username, setUsername] = useState("");
    const [role, setRole] = useState("");
    const [permissions, setPermissions] = useState([]);


    useEffect(() => {
        setAuthenticated(isUserAuthenticated());

        if (!authenticated) {
            navigate('/authentication/login');
        }

        setUsername(getUsername());
        setRole(getUserRole());
        setPermissions(getUserPermissions());

    }, [authenticated, navigate]);


    return (
        <AuthenticationContext.Provider value={{
            userIsAuthenticated: authenticated,
            setUserIsAuthenticated: setAuthenticated,
            username: username,
            userRole: role,
            userPermissions: permissions
        }}>
            {children}
        </AuthenticationContext.Provider>
    );
};
