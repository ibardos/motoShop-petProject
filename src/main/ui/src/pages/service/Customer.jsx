import {FormControl} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import {AuthenticationContext} from "../../security/authenticationProvider";
import {fetchBackendApi} from "../../util/fetchBackendApi";
import {removeJwtToken} from "../../security/authService";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import StripedTable from "../../components/shared/table/StripedTable";
import CustomerAddModal from "../../components/customer/CustomerAddModal";
import CustomerUpdateModal from "../../components/customer/CustomerUpdateModal";
import CustomerDeleteModal from "../../components/customer/CustomerDeleteModal";
import CustomerDeleteErrorModal from "../../components/customer/CustomerDeleteErrorModal";
import OrdersModal from "../../components/order/OrdersModal";


const Customer = () => {
    const navigate = useNavigate();

    const {userPermissions} = useContext(AuthenticationContext);

    // States for fetched data
    const [isLoaded, setIsLoaded] = useState(false);
    const [error, setError] = useState(null);
    const [customers, setCustomers] = useState([]);
    const [filteredData, setFilteredData] = useState([]);

    // States for Modals
    const [addModalShow, setAddModalShow] = useState(false);
    const [updateModalShow, setUpdateModalShow] = useState(false);
    const [deleteModalShow, setDeleteModalShow] = useState(false);
    const [deleteErrorModalShow, setDeleteErrorModalShow] = useState(false);
    const [ordersModalShow, setOrdersModalShow] = useState(false);
    const [recordId, setRecordId] = useState("");

    // State for table re-render
    const [formSubmit, setFormSubmit] = useState(false);


    // Table live search feature
    function handleSearch(event) {
        setFilteredData(customers.filter((item) =>
            item.id.toString().includes(event.target.value) ||
            item.fullName.toLowerCase().includes(event.target.value.toLowerCase()) ||
            item.email.toLowerCase().includes(event.target.value.toLowerCase()) ||
            item.phoneNumber.toString().includes(event.target.value) ||
            item.fullAddress.toString().includes(event.target.value) ||
            item.dateOfRegistration.toLowerCase().includes(event.target.value.toLowerCase())
        ));
    }


    // Fetch data for all the components of Customers page
    useEffect(() => {
        const fetchCustomers = async () => {
            try {
                const customers = await fetchBackendApi("/service/customer/get/all", "GET");
                setCustomers(customers);
                setFilteredData(customers);
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

        fetchCustomers();
    }, [formSubmit, navigate, ordersModalShow])


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
            <h2 className="page-title">Customers</h2>

            {conditionalSearchBarAndAddButton}

            <StripedTable originalData={customers}
                          filteredData={filteredData}
                          setRecordId={setRecordId}
                          setUpdateModalShow={setUpdateModalShow}
                          setDeleteModalShow={setDeleteModalShow}
                          setOrdersModalShow={setOrdersModalShow}
                          error={error}
                          isLoaded={isLoaded}
                          isSellButtonVisible={false}/>


            <CustomerAddModal setFormSubmit={setFormSubmit}
                              show={addModalShow}
                              setAddModalShow={setAddModalShow}
                              onHide={() => setAddModalShow(false)}/>

            <CustomerUpdateModal recordId={recordId}
                                 setFormSubmit={setFormSubmit}
                                 show={updateModalShow}
                                 setUpdateModalShow={setUpdateModalShow}
                                 onHide={() => setUpdateModalShow(false)}/>

            <CustomerDeleteModal customers={customers}
                                 recordId={recordId}
                                 setErrorModalShow={setDeleteErrorModalShow}
                                 setFormSubmit={setFormSubmit}
                                 show={deleteModalShow}
                                 setDeleteModalShow={setDeleteModalShow}
                                 onHide={() => setDeleteModalShow(false)}/>

            <CustomerDeleteErrorModal customers={customers}
                                      recordId={recordId}
                                      show={deleteErrorModalShow}
                                      setDeleteErrorModalShow={setDeleteErrorModalShow}
                                      onHide={() => setDeleteErrorModalShow(false)}/>

            <OrdersModal recordId={recordId}
                         setFormSubmit={setFormSubmit}
                         show={ordersModalShow}
                         setOrdersModalShow={setOrdersModalShow}
                         onHide={() => setOrdersModalShow(false)}/>
        </>
    )
}

export default Customer;
