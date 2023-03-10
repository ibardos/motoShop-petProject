import {Outlet, Link} from "react-router-dom";

// Navbar related imports
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import {Image} from "react-bootstrap";

import logo from "../assets/img/shared/motoShopLogoForNavbar.png";
import "../index.css";


const Layout = () => {
    return (
        <>
            <Header />
            <div className={"content"}>
                <Outlet/>
            </div>
            <Footer />
        </>
    )
};


const Header = () => {
    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Container>
                <Navbar.Brand><Image src={logo} alt="motoShop logo"/></Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav" className="justify-content-end">
                    <Nav>
                        <Nav.Link as={Link} to="/">Home</Nav.Link>
                        <Nav.Link as={Link} to="/manufacturers">Manufacturers</Nav.Link>
                        <Nav.Link as={Link} to="/motorcycles">Motorcycles</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}


const Footer = () => {
    return (
        <>
            <footer>
                <span>Created by ibardos => <a href="https://github.com/ibardos">GitHub profile.</a></span>
            </footer>
        </>
    )
}

export default Layout;