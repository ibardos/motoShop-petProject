import {useContext, useEffect, useState} from "react";

import {useNavigate} from "react-router-dom";

import {FormControl} from "react-bootstrap";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";

import StripedTable from "../../components/shared/table/StripedTable";
import MotorcycleStockAddModal from "../../components/motorcycleStock/MotorcycleStockAddModal";
import MotorcycleStockUpdateModal from "../../components/motorcycleStock/MotorcycleStockUpdateModal";
import MotorcycleStockDeleteModal from "../../components/motorcycleStock/MotorcycleStockDeleteModal";

import {fetchData} from "../../util/fetchData";
import {removeJwtToken} from "../../security/authService";
import {AuthenticationContext} from "../../security/authenticationProvider";


const MotorcycleStock = () => {
    const navigate = useNavigate();

    const {userPermissions} = useContext(AuthenticationContext);

    // States for fetched data
    const [isLoaded, setIsLoaded] = useState(false);
    const [error, setError] = useState(null);
    const [motorcycleStocks, setMotorcycleStocks] = useState([]);
    const [filteredData, setFilteredData] = useState([]);

    // States for Modals
    const [addModalShow, setAddModalShow] = useState(false);
    const [updateModalShow, setUpdateModalShow] = useState(false);
    const [deleteModalShow, setDeleteModalShow] = useState(false);
    const [recordId, setRecordId] = useState("");

    // State for table re-render
    const [formSubmit, setFormSubmit] = useState(false);


    // Table live search feature
    function handleSearch(event) {
        setFilteredData(motorcycleStocks.filter((item) =>
            item.id.toString().includes(event.target.value) ||
            item.motorcycleModel.manufacturer.name.toLowerCase().includes(event.target.value.toLowerCase()) ||
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
        fetchData("/service/motorcycle/stock/get/all")
            .then(
                (result) => {
                    // If HTTP response has 403 status code here, that means the JWT token has been maliciously altered
                    // so the user will be prompted to log in again, and retrieve a valid JWT token from the back-end server
                    if (result.status === 403) {
                        removeJwtToken();
                        navigate('/authentication/login');
                    }

                    setMotorcycleStocks(result);
                    setFilteredData(result);
                    setIsLoaded(true);
                },
                (error) => {
                    setError(error);
                    setIsLoaded(true);
                }
            );
    }, [formSubmit, navigate])


    // Create a search bar for the "Table live search feature"
    const conditionalSearchBar = (
        <Col>
            <FormControl id="search-box" type="text" placeholder="Type here to filter..."
                         onChange={event => handleSearch(event)}/>
        </Col>
    )

    // Create a conditionally rendered "Add new item" button
    const conditionalAddButton = (userPermissions.includes('Create')) ? (
        <Col xs={2}>
            <Button variant="secondary" onClick={() => setAddModalShow(true)}>Add new item</Button>
        </Col>
    ) : null;

    // Compose the search bar and "Add new item" button into one conditionally rendered element
    const conditionalSearchBarAndAddButton = (!error) ? (
        <Container id="search-and-add-bar">
            <Row>
                {conditionalSearchBar}
                {conditionalAddButton}
            </Row>
        </Container>
    ) : null;


    return (
        <>
            <h2 className="page-title">Motorcycle models in stock</h2>

            {conditionalSearchBarAndAddButton}

            <StripedTable originalData={motorcycleStocks}
                          filteredData={filteredData}
                          setRecordId={setRecordId}
                          setUpdateModalShow={setUpdateModalShow}
                          setDeleteModalShow={setDeleteModalShow}
                          error={error} isLoaded={isLoaded}/>


            <MotorcycleStockAddModal setFormSubmit={setFormSubmit}
                                     show={addModalShow}
                                     setAddModalShow={setAddModalShow}
                                     onHide={() => setAddModalShow(false)}/>

            <MotorcycleStockUpdateModal motorcycleStocks={motorcycleStocks}
                                        recordId={recordId}
                                        setFormSubmit={setFormSubmit}
                                        show={updateModalShow}
                                        setUpdateModalShow={setUpdateModalShow}
                                        onHide={() => setUpdateModalShow(false)}/>

            <MotorcycleStockDeleteModal motorcycleStocks={motorcycleStocks}
                                        recordId={recordId}
                                        setFormSubmit={setFormSubmit}
                                        show={deleteModalShow}
                                        setDeleteModalShow={setDeleteModalShow}
                                        onHide={() => setDeleteModalShow(false)}/>
        </>
    )
}

export default MotorcycleStock;
