import {Link} from "react-router-dom";

import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';

import manufacturersImage from "../assets/img/home/manufacturers.jpg"
import motorcycleImage from "../assets/img/home/motorcycles.jpg"


const Home = () => {
    return (
        <>
            <h2 className="page-title">Home</h2>
            <Services />
        </>
    )
}


const Services = () => {
    return (
        <Row xs={1} md={2} className="g-3">
            <Col>
                <Card bg={"dark"}>
                    <Card.Body>
                        <Card.Title>Manufacturers</Card.Title>
                        <Card.Text>
                            Create, read, update, delete Manufacturers.
                        </Card.Text>
                    </Card.Body>
                    <Link as={Link} to="/manufacturers"><Card.Img variant="bottom" src={manufacturersImage} /></Link>
                </Card>
            </Col>

            <Col>
                <Card bg={"dark"}>
                    <Card.Body>
                        <Card.Title>Motorcycles</Card.Title>
                        <Card.Text>
                            Create, read, update, delete Motorcycles.
                        </Card.Text>
                    </Card.Body>
                    <Link as={Link} to="/motorcycles"><Card.Img variant="bottom" src={motorcycleImage} /></Link>
                </Card>
            </Col>
        </Row>
    );
}

export default Home;