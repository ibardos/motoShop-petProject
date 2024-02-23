import {Link} from "react-router-dom";

import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';

import manufacturersImage from "../assets/img/home/manufacturers.jpg"
import motorcycleImage from "../assets/img/home/motorcycles.jpg"


const Service = () => {
    return (
        <>
            <h2 className="page-title">Services</h2>
            <Services/>
        </>
    );
}


const Services = () => {
    return (
        <Row xs={1} md={2} className="g-3">
            <Col>
                <Card bg={"dark"}>
                    <Card.Body>
                        <Card.Title>Manufacturer</Card.Title>
                        <Card.Text>
                            Create, read, update, delete Manufacturers.
                        </Card.Text>
                    </Card.Body>
                    <Link to="manufacturer"><Card.Img variant="bottom" src={manufacturersImage} /></Link>
                </Card>
            </Col>

            <Col>
                <Card bg={"dark"}>
                    <Card.Body>
                        <Card.Title>Motorcycle</Card.Title>
                        <Card.Text>
                            Create, read, update, delete Motorcycles.
                        </Card.Text>
                    </Card.Body>
                    <Link to="motorcycle"><Card.Img variant="bottom" src={motorcycleImage} /></Link>
                </Card>
            </Col>
        </Row>
    );
}

export default Service;
