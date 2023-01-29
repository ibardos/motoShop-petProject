import {Outlet, Link} from "react-router-dom";
import logo from "../assets/img/shared/motoShop_logo_forNavbar.png";
import "../index.css";

// Navbar related imports
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import {Image} from "react-bootstrap";

const Layout = () => {
    return (
        <>
            <Header />
            <div className={"content"}>
                <Outlet/>
            </div>
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
}

export default Layout;