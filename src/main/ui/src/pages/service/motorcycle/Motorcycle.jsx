import {Link} from "react-router-dom";

import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import Row from "react-bootstrap/Row";

import motorcycleModelImage from "../../../assets/img/motorcycles/motorcycleModel.jpg";
import motorcycleStockImage from "../../../assets/img/motorcycles/motorcycleStock.jpg";


const Motorcycle = () => {
    return (
        <>
            <h2 className="page-title">Motorcycle services</h2>
            <MotorcycleServices/>
        </>
    )
}


const MotorcycleServices = () => {
    return (
        <Row xs={1} md={2} className="g-3">
            <Col>
                <Card bg={"dark"}>
                    <Card.Body>
                        <Card.Title>Motorcycle model</Card.Title>
                        <Card.Text>
                            Create, read, update, delete Motorcycle models.
                        </Card.Text>
                    </Card.Body>
                    <Link as={Link} to="/service/motorcycle/model"><Card.Img variant="bottom" src={motorcycleModelImage} /></Link>
                </Card>
            </Col>

            <Col>
                <Card bg={"dark"}>
                    <Card.Body>
                        <Card.Title>Motorcycle stock</Card.Title>
                        <Card.Text>
                            Create, read, update, delete Motorcycle items in stock.
                        </Card.Text>
                    </Card.Body>
                    <Link as={Link} to="/service/motorcycle/stock"><Card.Img variant="bottom" src={motorcycleStockImage} /></Link>
                </Card>
            </Col>
        </Row>
    );
};

export default Motorcycle;
