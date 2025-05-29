import {useEffect, useState} from "react";
import {removeJwtToken} from "../../security/authService";
import StripedTable from "../shared/table/StripedTable";
import Modal from "react-bootstrap/Modal";
import {useNavigate} from "react-router-dom";
import {fetchData} from "../../util/fetchData";
import OrderDeleteModal from "./OrderDeleteModal";
import OrderUpdateModal from "./OrderUpdateModal";
import Card from "react-bootstrap/Card";


const OrdersModal = (props) => {
    const modalBody = <Orders recordId={props.recordId}/>;

    return (
        <Modal
            show={props.show}
            onHide={() => props.setOrdersModalShow(false)}
            dialogClassName="modal-85"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <div style={{paddingInline: "40px"}}>
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter" className="w-100 text-center">
                        <h2>Orders list</h2>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {modalBody}
                </Modal.Body>
            </div>
        </Modal>
    );
}

const Orders = (props) => {
    const navigate = useNavigate();

    // States for fetched data
    const [isLoaded, setIsLoaded] = useState(false);
    const [error, setError] = useState(null);
    const [orders, setOrders] = useState([]);
    const [customer, setCustomer] = useState({});

    // States for Modals
    const [updateModalShow, setUpdateModalShow] = useState(false);
    const [deleteModalShow, setDeleteModalShow] = useState(false);
    const [recordId, setRecordId] = useState("");

    // State for table re-render
    const [formSubmit, setFormSubmit] = useState(false);


    // Fetch data: Orders of a Customer
    useEffect(() => {
        fetchData(`/service/order/get/byCustomerId/${props.recordId}`)
            .then(
                (result) => {
                    // If HTTP response has 403 status code here, that means the JWT token has been maliciously altered
                    // so the user will be prompted to log in again, and retrieve a valid JWT token from the back-end server
                    if (result.status === 403) {
                        removeJwtToken();
                        navigate('/authentication/login');
                    }

                    setOrders(result);
                    setIsLoaded(true);
                },
                (error) => {
                    setError(error);
                    setIsLoaded(true);
                }
            );
    }, [props.recordId, formSubmit, navigate])

    // Fetch data: data of a Customer
    useEffect(() => {
        fetchData(`/service/customer/get/${props.recordId}`)
            .then((result) => {
                if (result.status === 403) {
                    removeJwtToken();
                    navigate('/authentication/login');
                }

                setCustomer(result);
            })
            .catch((error) => {
                console.error("Error fetching customer:", error);
            });
    }, [props.recordId, navigate]);

    return (
        <>
            <div className={"left-aligned"}>
                <h5 style={{textDecoration: "underline"}}>Customer data:</h5>
                <Card className="bg-dark">
                    <Card.Body>
                        <h6>Id: {customer.id}</h6>
                        <h6>Full name: {customer.fullName}</h6>
                        <h6>Phone number: {customer.phoneNumber}</h6>
                    </Card.Body>
                </Card>
            </div>

            <div style={{textAlign: "center"}}>
                <StripedTable originalData={orders}
                              filteredData={orders}
                              setRecordId={setRecordId}
                              setUpdateModalShow={setUpdateModalShow}
                              setDeleteModalShow={setDeleteModalShow}
                              error={error}
                              isLoaded={isLoaded}
                              isSellButtonVisible={false}/>
            </div>

            <OrderUpdateModal recordId={recordId}
                              setFormSubmit={setFormSubmit}
                              show={updateModalShow}
                              setUpdateModalShow={setUpdateModalShow}
                              onHide={() => setUpdateModalShow(false)}/>

            <OrderDeleteModal orders={orders}
                              recordId={recordId}
                              setFormSubmit={setFormSubmit}
                              show={deleteModalShow}
                              setDeleteModalShow={setDeleteModalShow}
                              onHide={() => setDeleteModalShow(false)}/>
        </>
    );
};

export default OrdersModal;
