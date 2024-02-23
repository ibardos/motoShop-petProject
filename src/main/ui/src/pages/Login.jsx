import {useContext, useEffect, useState} from "react";

import {useNavigate} from "react-router-dom";

import {Form} from "react-bootstrap";
import Card from 'react-bootstrap/Card';
import Button from "react-bootstrap/Button";

import {saveJwtToken} from "../security/authService";
import {AuthenticationContext} from "../security/authenticationProvider";


const Login = () => {
    return (
        <>
            <h2 className="page-title">Login to motoShop</h2>
            <LoginForm/>
        </>
    )
}


const LoginForm = () => {
    const navigate = useNavigate();

    const {userIsAuthenticated, setUserIsAuthenticated} = useContext(AuthenticationContext);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [badCredentialsAlert, setBadCredentialsAlert] = useState("");


    // During page refresh, this useEffect() navigates the authenticated user to "/" route, instead of login
    useEffect(() => {
        if (userIsAuthenticated) {
            navigate('/');
        }
    }, [navigate, userIsAuthenticated]);


    async function handleSubmit(event) {
        event.preventDefault();

        const url = "/authentication/login";

        const requestBody = {
            "username": username,
            "password": password
        }

        const options = {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(requestBody),
        }

        try {
            const response = await fetch(url, options);

            if (response.ok) {
                const responseBody = await response.json();

                const jwtToken = responseBody.jwtToken;

                saveJwtToken(jwtToken);

                // Explicitly set the userIsAuthenticated in AuthenticationContext, otherwise the lag in context refresh
                // will falsely trigger safety mechanism of forced navigation back to login page
                setUserIsAuthenticated(true);

                if (userIsAuthenticated) {
                    navigate('/');
                }
            } else {
                setBadCredentialsAlert("Bad credentials, please try again!");
            }
        } catch (authenticationError) {
            console.error('Authentication failed: ', authenticationError);
        }
    }


    return (
        <Card bg={"dark"} style={{width: '300px', margin: 'auto'}}>
            <Card.Body>
                <Form>
                    <Form.Group style={{}} className="mb-3" controlId="formTextareaUsername">
                        <Form.Label>username</Form.Label>
                        <Form.Control type="text" name="username" placeholder="username"
                                      onChange={(event) => setUsername(event.target.value)}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formTextareaPassword">
                        <Form.Label>password</Form.Label>
                        <Form.Control type="password" name="password" placeholder="password"
                                      onChange={(event) => setPassword(event.target.value)}/>
                    </Form.Group>

                    <p style={{fontWeight: "bold", textAlign: "center", color: "red"}}>{badCredentialsAlert}</p>

                    <div style={{display: "flex", justifyContent: "center"}}>
                        <Button type="button" variant="secondary" style={{paddingInline: "30px"}} onClick={handleSubmit}>Login</Button>
                    </div>
                </Form>
            </Card.Body>
        </Card>
    );
}

export default Login;
