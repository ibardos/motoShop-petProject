import {useContext, useEffect, useState} from "react";

import {useNavigate} from "react-router-dom";

import {FormControl} from "react-bootstrap";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";

import StripedTable from "../../../components/shared/table/StripedTable";
import MotorcycleStockAddModal from "../../../components/motorcycleStock/MotorcycleStockAddModal";
import MotorcycleStockUpdateModal from "../../../components/motorcycleStock/MotorcycleStockUpdateModal";
import MotorcycleStockDeleteModal from "../../../components/motorcycleStock/MotorcycleStockDeleteModal";

import {fetchBackendApi} from "../../../util/fetchBackendApi";
import {removeJwtToken} from "../../../security/authService";
import {AuthenticationContext} from "../../../security/authenticationProvider";
import OrderAddModal from "../../../components/order/OrderAddModal";
import MotorcycleStockDeleteErrorModal from "../../../components/motorcycleStock/MotorcycleStockDeleteErrorModal";


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
    const [deleteErrorModalShow, setDeleteErrorModalShow] = useState(false);
    const [orderAddModalShow,  setOrderAddModalShow] = useState(false);
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


    // Fetch data for all the components of Motorcycle stock page
    useEffect(() => {
        const fetchMotorcycleStocks = async () => {
            try {
                const motorcycleStocks = await fetchBackendApi("/service/motorcycle/stock/get/all", "GET");
                setMotorcycleStocks(motorcycleStocks);
                setFilteredData(motorcycleStocks);
            } catch (error) {
                if (error.status === 403) {
                    removeJwtToken();
                    navigate('/authentication/login');
                } else {
                    console.error("Unexpected error:", error.message);
                    setError(error);
                }
            } finally {
                setIsLoaded(true);
            }
        };

        fetchMotorcycleStocks();
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
                          setOrderAddModalShow={setOrderAddModalShow}
                          error={error}
                          isLoaded={isLoaded}
                          isSellButtonVisible={true}/>


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
                                        setDeleteErrorModalShow={setDeleteErrorModalShow}
                                        setFormSubmit={setFormSubmit}
                                        show={deleteModalShow}
                                        setDeleteModalShow={setDeleteModalShow}
                                        onHide={() => setDeleteModalShow(false)}/>

            <OrderAddModal motorcycleStocks={motorcycleStocks}
                           recordId={recordId}
                           setFormSubmit={setFormSubmit}
                           show={orderAddModalShow}
                           setOrderAddModalShow={setOrderAddModalShow}
                           onHide={() => setOrderAddModalShow(false)}/>
        </>
    )
}

export default MotorcycleStock;
