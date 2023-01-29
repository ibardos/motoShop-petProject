import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import Row from "react-bootstrap/Row";

import motorcycleModelImage from "../../assets/img/motorcycles/motorcycleModel.jpg";
import motorcycleStockImage from "../../assets/img/motorcycles/motorcycleStock.jpg";
import {Link} from "react-router-dom";

const Motorcycles = () => {
    return (
        <>
            <h2 className="pageTitle">Motorcycles</h2>
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
                        <Card.Title>Motorcycle model</Card.Title>
                        <Card.Text>
                            Create, read, update, delete Motorcycle models.
                        </Card.Text>
                    </Card.Body>
                    <Link as={Link} to="/motorcycles/model"><Card.Img variant="bottom" src={motorcycleModelImage} /></Link>
                </Card>
            </Col>

            <Col>
                <Card bg={"dark"}>
                    <Card.Body>
                        <Card.Title>Motorcycle stock</Card.Title>
                        <Card.Text>
                            Create, read, update, delete Motorcycle models in stock.
                        </Card.Text>
                    </Card.Body>
                    <Link as={Link} to="/motorcycles/stock"><Card.Img variant="bottom" src={motorcycleStockImage} /></Link>
                </Card>
            </Col>
        </Row>
    );
};

export default Motorcycles;