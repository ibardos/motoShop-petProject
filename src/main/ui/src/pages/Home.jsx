import {Link} from "react-router-dom";

import Card from 'react-bootstrap/Card';
import Button from "react-bootstrap/Button";


const Home = () => {
    return (
        <>
            <h1 className="page-title">Introduction</h1>
            <Introduction/>
        </>
    );
}


const LinkToProjectRepo = () => (
    <a href="https://github.com/ibardos/motoShop-petProject" target="_blank" rel="noreferrer" >here,</a>
)

const Introduction = () => {
    return (
        <Card bg={"dark"} style={{width: '70%', margin: 'auto'}}>
            <Card.Body style={{padding: "16px 50px 16px 50px"}}>
                <h3 style={{padding: "20px 0px 20px 0px"}}>Welcome to motoShop ERP</h3>
                <p>
                    This is a solo experimental project about an ERP software for a motorcycle retail shop, designed to
                    be a modern single-page web application. It is aimed to serve individual learning purposes with
                    multi-layered web-applications.
                </p>
                <p>
                    The project is under continuous development, with a fully functional Java back-end server, to which
                    a ReactJS front-end user-interface is connected. The back-end and the front-end are communicating
                    via REST APIs. For session management, JWT tokens are used to make the process stateless. During
                    persistent data storage, a PostgreSQL relational database is used.
                </p>
                <p>
                    If you'd like to see this project's GitHub repo, just click <LinkToProjectRepo/> or you can use
                    the link down in the footer to see my GitHub profile.
                </p>
                <p style={{padding: "20px 0px 20px 0px"}}>
                    To start using the application, just click on the button below "Got to Services".
                </p>
                <div style={{display: "flex", justifyContent: "center"}}>
                    <Button as={Link} to="service" type="button" variant="secondary" style={{paddingInline: "30px"}}>Go to Services</Button>
                </div>
            </Card.Body>
        </Card>
    );
}

export default Home;
