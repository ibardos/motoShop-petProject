import {useContext} from "react";

import {Link, Outlet} from "react-router-dom";

import "../index.css";

// Navbar related imports
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import {Image, NavDropdown} from "react-bootstrap";

import logo from "../assets/img/shared/motoShopLogoForNavbar.png";

import {AuthenticationContext} from "../security/authenticationProvider";
import {removeJwtToken} from "../security/authService";


const Layout = () => {
    return (
        <>
            <Header/>
                <div className={"content"}>
                    <Outlet/>
                </div>
            <Footer/>
        </>
    )
}


const Header = () => {
    const {userIsAuthenticated, username, userRole} = useContext(AuthenticationContext);

    const myAccountUri = `/user/${username}`;


    const ConditionalNavbarDropdownButtonsIfAdminUser = () => {
        if (userRole === 'Admin') {
            return (
                <>
                    <NavDropdown.Item as={Link} to="/user">Users</NavDropdown.Item>
                    <NavDropdown.Divider/>
                </>
            )
        }
    }

    const ConditionalNavbarButtonsIfSalesOrAdminUser = () => {
        if (userRole === 'Sales' || userRole === 'Admin') {
            return (
                <>
                    <Nav.Link as={Link} to="/customer">Customers</Nav.Link>
                    <Nav.Link as={Link} to="/bank">Banking</Nav.Link>
                    <Nav.Link disabled>II</Nav.Link>
                </>
            )
        }
    }

    const ConditionalNavbarIfAuthenticated = () => {
        if (userIsAuthenticated) {
            return (
                <>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                    <Navbar.Collapse id="responsive-navbar-nav" className="justify-content-end">
                        <Nav>
                            <Nav.Link as={Link} to="/service/manufacturer">Manufacturers</Nav.Link>
                            <NavDropdown title="Motorcycle services" id="basic-nav-dropdown">
                                <NavDropdown.Item as={Link} to="/service/motorcycle/model">Motorcycle models</NavDropdown.Item>
                                <NavDropdown.Item as={Link} to="/service/motorcycle/stock">Motorcycles in stock</NavDropdown.Item>
                            </NavDropdown>
                            <Nav.Link disabled>II</Nav.Link>
                            <ConditionalNavbarButtonsIfSalesOrAdminUser/>
                            <NavDropdown title="Account" id="basic-nav-dropdown">
                                <NavDropdown.Item as={Link} to={myAccountUri}>My account</NavDropdown.Item>
                                <ConditionalNavbarDropdownButtonsIfAdminUser/>
                                <NavDropdown.Item as={Link} to="/authentication/login" onClick={removeJwtToken}>Logout</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    </Navbar.Collapse>
                </>
            )
        }
    }


    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Container className="justify-content-center">
                <Navbar.Brand>
                    <Link to="/"><Image src={logo} alt="motoShop logo"/></Link>
                </Navbar.Brand>
                <ConditionalNavbarIfAuthenticated/>
            </Container>
        </Navbar>
    )
}


const Footer = () => {
    return (
        <>
            <footer>
                <span>Created by ibardos => <a href="https://github.com/ibardos" target="_blank" rel="noreferrer">GitHub profile</a></span>
            </footer>
        </>
    )
}

export default Layout;
