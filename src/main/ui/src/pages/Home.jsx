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
        <Card bg={"dark"} style={{width: "75%", margin: "auto"}}>
            <Card.Body style={{padding: "16px 50px"}}>
                <h3 style={{padding: "20px 0"}}>motoShop ERP</h3>

                <p>
                    Solo learning project of a modern ERP web application for a motorcycle retail shop.
                    Demonstrates full-stack development with clear separation of concerns.
                </p>

                <div style={{marginTop: "2rem", marginLeft: "20px", textAlign: "left"}}>
                    <strong>Main technologies used:</strong>
                    <ul>
                        <li><strong>Backend:</strong> Java, Spring Boot, JPA, PostgreSQL</li>
                        <li><strong>Frontend:</strong> JavaScript, React, Bootstrap</li>
                        <li><strong>Communication:</strong> REST APIs using DTOs</li>
                        <li><strong>Security:</strong> JWT-based stateless authentication</li>
                        <li><strong>Testing:</strong> Postman, JUnit 5, H2 Database</li>
                        <li><strong>Dev tools:</strong> IntelliJ IDEA, Git, GitHub Actions (CI)</li>
                    </ul>
                </div>

                <p style={{marginTop: "2rem"}}>
                    Source code available on GitHub <LinkToProjectRepo/> or via my GitHub profile in the footer.
                </p>

                <p style={{marginTop: "2rem"}}>
                    Click the button below to get started.
                </p>

                <div style={{display: "flex", justifyContent: "center"}}>
                    <Button as={Link} to="service" variant="secondary" style={{paddingInline: "30px"}}>
                        Go to Services
                    </Button>
                </div>
            </Card.Body>
        </Card>
    );
}

export default Home;
