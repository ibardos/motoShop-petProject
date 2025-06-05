import {useContext, useEffect, useState} from "react";

import {useNavigate} from "react-router-dom";

import {Form} from "react-bootstrap";
import Card from 'react-bootstrap/Card';
import Button from "react-bootstrap/Button";

import {saveJwtToken} from "../security/authService";
import {AuthenticationContext} from "../security/authenticationProvider";
import {fetchBackendApi} from "../util/fetchBackendApi";


const Login = () => {
    return (
        <>
            <h2 className="page-title">Login to motoShop</h2>
            <LoginForm/>
            <LoginHelpInfo/>
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

        const body = {
            "username": username,
            "password": password
        }

        try {
            const response = await fetchBackendApi("/authentication/login", "POST", body);
            saveJwtToken(response.jwtToken);

            // Explicitly set the userIsAuthenticated in AuthenticationContext, otherwise the lag in context refresh
            // will falsely trigger safety mechanism of forced navigation back to login page
            setUserIsAuthenticated(true);

            if (userIsAuthenticated) {
                navigate('/');
            }
        } catch (authenticationError) {
            setBadCredentialsAlert("Bad credentials, please try again!");
            console.error('Authentication failed: ', authenticationError);
        }
    }

    const handleEnterPressedToLogin = async (event) => {
        if (event.key === "Enter") {
            await handleSubmit(event);
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
                                      onChange={(event) => setPassword(event.target.value)}
                                      onKeyUp={handleEnterPressedToLogin}/>
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

const LoginHelpInfo = () => {
    return (
        <Card bg={"secondary"} style={{width: '400px', margin: 'auto', marginTop: '40px'}}>
            <Card.Body>
                <h4 style={{marginBottom: '1rem', textDecoration: 'underline'}}>Help to log in:</h4>
                <h6 className="left-aligned">Credentials (username - password):</h6>
                <ul className="left-aligned">
                    <li>User role: user - user</li>
                    <li>Sales role: sales - sales</li>
                    <li>Admin role: admin - admin</li>
                </ul>
                <h6 className="left-aligned">Permissions:</h6>
                <ul className="left-aligned">
                    <li>User role: read</li>
                    <li>Sales role: create, read, update</li>
                    <li>Admin role: create, read, update, delete</li>
                </ul>
            </Card.Body>
        </Card>
    )
}

export default Login;
