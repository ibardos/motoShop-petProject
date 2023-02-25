import {useEffect, useState} from "react";

import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {FormControl} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import StripedTable from "../../components/shared/table/StripedTable";
import MotorcycleStockAddModal from "../../components/motorcycleStock/MotorcycleStockAddModal";
import MotorcycleStockUpdateModal from "../../components/motorcycleStock/MotorcycleStockUpdateModal";

import {fetchData} from "../../util/fetchData";


const MotorcycleStock = () => {
    // States for fetched data
    const [isLoaded, setIsLoaded] = useState(false);
    const [error, setError] = useState(null);
    const [motorcycleStocks, setMotorcycleStocks] = useState([]);
    const [filteredData, setFilteredData] = useState([]);

    // States for Modals
    const [addModalShow, setAddModalShow] = useState(false);
    const [updateModalShow, setUpdateModalShow] = useState(false);
    const [recordId, setRecordId] = useState("");

    // State for table re-render
    const [formSubmit, setFormSubmit] = useState(false);


    // Table live search feature
    async function handleSearch(event) {
        setFilteredData(motorcycleStocks.filter((item) =>
            item.id.toString().includes(event.target.value) ||
            item.motorcycleModel.modelName.toLowerCase().includes(event.target.value.toLowerCase()) ||
            item.mileage.toString().includes(event.target.value) ||
            item.purchasingPrice.toString().includes(event.target.value) ||
            item.profitMargin.toString().includes(event.target.value) ||
            item.profitOnUnit.toString().includes(event.target.value) ||
            item.sellingPrice.toString().includes(event.target.value) ||
            item.inStock.toString().includes(event.target.value) ||
            item.color.toLowerCase().includes(event.target.value.toLowerCase())
        ));
    }


    // Fetching data for all the components of Motorcycle stock page
    useEffect(() => {
        fetchData("/motorcycle/stock/get/all")
            .then(
                (result) => {
                    setIsLoaded(true);
                    setMotorcycleStocks(result);
                    setFilteredData(result);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            );
    }, [formSubmit])

    // Check for errors during fetch data, and show loading status
    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    }


    return (
        <>
            <h2 className="page-title">Motorcycle stock</h2>
            <Container id="search-and-add-bar">
                <Row>
                    <Col>
                        <FormControl id="search-box" type="text" onChange={async (event) => await handleSearch(event)}
                                     placeholder="Type here to filter..."/>
                    </Col>
                    <Col xs={2}>
                        <Button variant="secondary" onClick={() => setAddModalShow(true)}>Add new item</Button>
                    </Col>
                </Row>
            </Container>

            <StripedTable originalData={motorcycleStocks} filteredData={filteredData} setRecordId={setRecordId}
                          setUpdateModalShow={setUpdateModalShow} setDeleteModalShow={setDeleteModalShow}/>


            <MotorcycleStockAddModal setFormSubmit={setFormSubmit} show={addModalShow} setAddModalShow={setAddModalShow}
                                     onHide={() => setAddModalShow(false)}/>

            <MotorcycleStockUpdateModal motorcycleStocks={motorcycleStocks} recordId={recordId}
                                        setFormSubmit={setFormSubmit}
                                        show={updateModalShow} setUpdateModalShow={setUpdateModalShow}
                                        onHide={() => setUpdateModalShow(false)}/>

        </>
    )
};

export default MotorcycleStock;